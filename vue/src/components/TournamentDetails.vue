<template>
  <div class="tournament-details">
    <div class="tournament-header">
      <h2>{{ tournament.name }}</h2>
      <button @click="handleClick">
        {{ buttonText }}
      </button>
    </div>
    
    
    

    <div v-if="showConfirmModal" class="modal-overlay">
      <div class="modal">
        <p>Are you sure you want to register for this tournament?</p>
        <button @click="confirmRegistration">Yes</button>
        <button @click="showConfirmModal = false">No</button>
      </div>
    </div>


    <div v-if="showSuccessModal" class="modal-overlay">
      <div class="modal">
        <p>You have successfully registered for the tournament!</p>
        <button @click="showSuccessModal = false">OK</button>
      </div>
    </div>

    <p>Start Date: {{ tournament.startDate }}</p>
    <p>End Date: {{ tournament.endDate }}</p>
    <h3>Registration Status: {{ (isUserRegistered) ? 'Registered':'Not registered' }}</h3>
    <h4>Match "Bracket"</h4>
    <ul v-if="matches.length">
      <li v-for="match in matches" :key="match.id">
        Match {{ match.id }}: {{ match.player1 }} vs {{ match.player2 }} - {{ match.status }}
      </li>
    </ul>


  </div>
</template>

<script>
import matchService from '../services/MatchService';
import TournamentService from '../services/TournamentService';


export default {
  props: {
    tournament: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      showConfirmModal: false,
      showSuccessModal: false,
      registrationStatus: 'Not Registered',
      tournaments: {},
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

      const checkUserIsRegistered = this.registeredParticipants.some(
        (participant) => participant.userId === userId);
      console.log("Test isUserRegistered results:", checkUserIsRegistered);
      return checkUserIsRegistered;
    }
  },
  methods: {

    handleClick() {
      if (this.isUserRegistered) {
        // this.leaveTournament();
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
          // console.log(response);
          // console.log("Before",this.registrationsStatus)
          if(response.status === 200){
            this.showSuccessModal = true;
            this.registrationStatus = 'Registered';
            this.fetchTournamentRegistrations(tournamentId);

          }
         
          // console.log("After",this.registrationsStatus)
        });
      

    },
    getMatches() {
      matchService.getMatchesByTournamentId(this.$route.params.tournamentId).then(response => {
        if (response.status === 200) {
          this.matches = response.data;
        }
      }).catch(error => {
        console.log(error);
      })
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
        })
    },


  },

  created() {
    const tournamentId = this.$route.params.tournamentId;
    this.fetchTournamentRegistrations(tournamentId);
    

  }


};
</script>

<style scoped>
.tournament-details {
  border: 1px solid #ddd;
  padding: 1rem;
  margin: 1rem;
  background-color: #f9f9f9;
}

.tournament-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1rem;
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
}

button:hover {
  background-color: #0056b3;
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