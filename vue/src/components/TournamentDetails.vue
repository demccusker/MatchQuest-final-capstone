<template>
  <div class="tournament-details">
    <div class="tournament-header">
      <h2>{{ tournament.name }}</h2>
      <p>Location: {{ (tournament.online) ? "Online" : tournamentLocation }}</p>
      <p>Start Date: {{ tournament.startDate }}</p>
      <p>End Date: {{ (tournament.endDate === null) ? "TBD" : tournament.endDate }}</p>
      <h3>Registration Status: {{ isUserRegistered ? 'Registered' : 'Not Registered' }}</h3>
    </div>

    <div class="tournament-actions">
      <button v-show="this.$store.state.user.id === this.tournament.creatorId && this.tournament.bracketId === 0"
        v-on:click="showGenerateConfirmModal = true" :disabled='this.hasEnoughPlayers'
        :style="{ cursor: (this.hasEnoughPlayers) ? `default` : `pointer` }">

        Generate Bracket
      </button>
      <button
        v-show="this.$store.state.user.id === this.tournament.creatorId && this.tournament.endDate !== null && new Date() > this.tournament.endDate"
        v-on:click="showCloseConfirmModal = true" v-if="!tournament.closed">

        Close Tournament
      </button>
      <button v-show="!isUserRegistered && this.tournament.bracketId === 0" v-on:click="showConfirmModal = true">

        Register
      </button>
    </div>

    <div class="brackets" v-if="hasBracket">
      <tournament-brackets v-bind:tournamentId="this.$route.params.tournamentId"
        v-bind:tournamentDate="this.tournament.startDate" v-bind:tournamentName="this.tournament.name">
      </tournament-brackets>
    </div>

    <div v-if="showGenerateConfirmModal" class="modal-overlay">
      <div class="modal">
        <p>Are you sure you want to generate brackets?</p>
        <button v-on:click="confirmGenerateBrackets">Yes</button>
        <button v-on:click="showGenerateConfirmModal = false">No</button>
      </div>
    </div>

    <div v-if="showCloseConfirmModal" class="modal-overlay">
      <div class="modal">
        <p>Are you sure you want to close this tournament?</p>
        <button v-on:click="confirmCloseTournament">Yes</button>
        <button v-on:click="showCloseConfirmModal = false">No</button>
      </div>
    </div>

    <div v-if="showConfirmModal" class="modal-overlay">
      <div class="modal">
        <p>Are you sure you want to register for this tournament?</p>
        <button v-on:click="confirmRegistration">Yes</button>
        <button v-on:click="showConfirmModal = false">No</button>
      </div>
    </div>

    <div v-if="showSuccessModal" class="modal-overlay">
      <div class="modal">
        <p>You have successfully registered for the tournament!</p>
        <button v-on:click="showSuccessModal = false">OK</button>
      </div>
    </div>
  </div>
</template>

<script>
import AddressService from '../services/AddressService';
import BracketService from '../services/BracketService';
import matchService from '../services/MatchService';
import TournamentService from '../services/TournamentService';
import TournamentBrackets from './TournamentBrackets.vue';

export default {
  props: {
    tournament: {
      type: Object,
      required: true
    }
  },
  components: {
    TournamentBrackets
  },
  data() {
    return {
      showConfirmModal: false,
      showSuccessModal: false,
      showCloseConfirmModal: false,
      showGenerateConfirmModal: false,
      registrationStatus: 'Not Registered',
      tournamentAddress: {},
      matches: [],
      registeredParticipants: []
    };
  },
  computed: {
    buttonText() {
      return this.isUserRegistered ? 'Leave' : 'Register';
    },
    isUserRegistered() {
      const userId = this.$store.state.user.id;
      return this.registeredParticipants.some(
        (participant) => participant.userId === userId
      );
    },
    isOrganizer() {
      return this.$store.state.user.role === 'organizer';
    },
    hasEnoughPlayers() {
      return !(this.registeredParticipants.length > 1);
    },
    hasBracket() {
      return this.tournament.bracketId != 0 && this.tournament.bracketId != null;
    },
    tournamentLocation() {
      const address = this.tournamentAddress;
      return address.streetNumber + " " + address.city + ", " + address.province + " " + address.country;
    }
  },
  methods: {
    handleClick() {
      if (this.isUserRegistered) {
        // Handle leave tournament logic here
      } else {
        this.showConfirmModal = true;
      }
    },
    confirmRegistration() {
      this.showConfirmModal = false;
      const tournamentId = this.$store.state.tournamentId;
      const authToken = this.$store.state.token;
      TournamentService.joinTournament(tournamentId, authToken)
        .then(response => {
          if (response.status === 200) {
            this.showSuccessModal = true;
            this.registrationStatus = 'Registered';
            this.fetchTournamentRegistrations(tournamentId);
          }
        });
    },
    assignMatches(bracketTree, matches) {
      if (bracketTree.length != matches.length) {
        console.log("brackeTree and matches mismatch!");
        return;
      }

      matches = matches.slice().reverse();

      for(let i = 0; i < bracketTree.length && i < matches.length; i++) {
        let bracket = bracketTree[i];

        bracket.matchId = matches[i].matchId;
        bracketTree[i] = bracket;
      }

      console.log("BracketTree before update: ", bracketTree);
      console.log("Token: ", this.$store.state.token);
      BracketService.updateBrackets(bracketTree, this.$store.state.token).then(response => {
        if (response.status == 200) {
          this.tournament.bracketId = response.data.root;
        }
      }).catch(error => {
        console.log(error);
      })
    },
    createBrackets(matches) {
      BracketService.createBracketTree(this.tournament.tournamentId).then(response => {
        if (response.status == 201) {
          this.assignMatches(response.data, matches);
        }
      }).catch(error => {
        console.log()
      });
    },
    confirmGenerateBrackets() {
      this.showGenerateConfirmModal = false;
      
      const numberOfMatches = Math.floor(Math.log2(this.registeredParticipants.length) + 1);
      const matches = [];

      // Go through registeredParticipants and assign to matches //
      for (let participantIndex = 0, matchCount = 0; participantIndex < this.registeredParticipants.length && matchCount < numberOfMatches; matchCount++) {
        const player1 = this.registeredParticipants[participantIndex];
        const player2 = this.registeredParticipants[participantIndex + 1];

        let match = {
          matchId: 0,
          gameId: this.tournament.gameId,
          isScrim: this.tournament.isScrim,
          player1Id: 0,
          player2Id: 0,
          player1Score: 0,
          player2Score: 0,
          winnerId: null,
          isDraw: false,
          matchStartTime: '11:00 AM'
        }

        match.player1Id = (player1 != null) ? player1.userId : null;
        match.player2Id = (player2 != null) ? player2.userId : null;

        matches.push(match);

        participantIndex += 2;
      }

      console.log("Matches ", matches);
      matchService.createMatches(matches, this.$store.state.token).then(response => {
        if (response.status == 201) {
          console.log("Response Matches: ", response.data);
          let responseMatches = response.data;
          this.createBrackets(responseMatches);
        }
      }).catch(error => {
        console.log(error);
      });
    },
    confirmCloseTournament() {
      this.showCloseConfirmModal = false;
      const tournamentId = this.$store.state.tournamentId;
      const authToken = this.$store.state.token;
      TournamentService.closeTournament(tournamentId, authToken)
        .then(response => {
          if (response.status === 200) {
            this.tournaments.closed = true;
          }
        });
    },
    getMatches(tournamentId) {
      if (this.tournament.bracketId == null) return;

      matchService.getMatchesByTournamentId(tournamentId).then(response => {
        if (response.status === 200) {
          this.matches = response.data;
        }
      }).catch(error => {
        console.log(error);
      });
    },
    checkRegistrationStatus() {
      const tournamentId = this.$store.state.tournamentId;
      const authToken = this.$store.state.token;
      TournamentService.getTournamentParticipants(tournamentId, authToken)
        .then(response => {
          if (response.status === 200) {
            const participantArray = response.data;
            const userId = this.$store.state.user.id;
            this.registrationStatus = participantArray.some(participant => participant.userId === userId) ? 'Registered' : 'Not Registered';
          }
        })
        .catch(error => {
          console.log("Error checking registration status:", error);
        });
    },
    fetchAddress(tournamentId) {
      AddressService.getAddress(tournamentId, this.$store.state.token).then(response => {
        if (response.status == 200) {
          this.tournamentAddress = response.data;
        }
      }).catch(error => {
        console.log(error);
      })
    },
    fetchTournamentRegistrations(tournamentId) {
      const authToken = this.$store.state.token;
      TournamentService.getTournamentParticipants(tournamentId, authToken)
        .then(response => {
          if (response.status === 200) {
            this.registeredParticipants = response.data;
          }
        })
        .catch(error => {
          console.log("Error fetching participants:", error);
        });
    }
  },
  created() {
    const tournamentId = this.$route.params.tournamentId;
    this.fetchTournamentRegistrations(tournamentId);
    this.fetchAddress(tournamentId);
    this.getMatches(tournamentId);
  }
};
</script>

<style scoped>
.tournament-details {
  border: 1px solid #ddd;
  padding: 1rem;
  margin: 1rem;
  background-color: #f9f9f9;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.tournament-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1rem;
  width: 100%;
}

.tournament-header h2 {
  margin: 0;
}

button {
  margin: 0.5rem;
  padding: 0.5rem 1rem;
  border: none;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  border-radius: 5px;
  font-size: 1rem;

  &:disabled {
    background-color: black;
  }
}

button:hover {
  background-color: #0056b3;
}

.tournament-actions {
  display: flex;
  flex-direction: row;
  gap: 10px;
  margin-top: 1rem;
  justify-content: flex-end;
  width: 100%;
}

.brackets {
  margin: auto auto;
  justify-content: center;
  align-items: center;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  background-color: white;
  padding: 1rem 2rem;
  border-radius: 5px;
  text-align: center;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin: 0.5rem 0;
}
</style>