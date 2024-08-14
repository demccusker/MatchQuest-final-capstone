<template>
    <div class="tournament-details">
      <div class="tournament-header">
        <h2>{{ tournament.name }}</h2>
        <p>Start Date: {{ tournament.startDate }}</p>
        <p>End Date: {{ tournament.endDate }}</p>
        <h3>Registration Status: {{ isUserRegistered ? 'Registered' : 'Not Registered' }}</h3>
      </div>
  
      <div class="tournament-actions">
        <button v-on:click="showStartConfirmModal = true" v-if=" !tournament.started">Start Tournament</button>
        <button v-on:click="showCloseConfirmModal = true" v-if="!tournament.closed">Close Tournament</button>
        <button v-on:click="showConfirmModal = true" v-if="!isUserRegistered && !tournament.started && !tournament.closed">Register</button>
      </div>
  
      <div v-if="showStartConfirmModal" class="modal-overlay">
        <div class="modal">
          <p>Are you sure you want to start this tournament?</p>
          <button v-on:click="confirmStartTournament">Yes</button>
          <button v-on:click="showStartConfirmModal = false">No</button>
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
  
      <h4>Match "Bracket"</h4>
        <ul>
            <li v-for="match in matches" :key="match.matchId">
            <router-link :to="{ name: 'MatchDetails', params: { id: match.matchId } }">{{ match.name }}</router-link>
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
        showStartConfirmModal: false,
        showCloseConfirmModal: false,
        registrationStatus: 'Not Registered',
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
      confirmStartTournament() {
        this.showStartConfirmModal = false;
        const tournamentId = this.$store.state.tournamentId;
        const authToken = this.$store.state.token;
        TournamentService.startTournament(tournamentId, authToken)
          .then(response => {
            if (response.status === 200) {
              this.tournaments.started = true;
            }
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
      getMatches() {
        matchService.getMatchesByTournamentId(this.$route.params.tournamentId).then(response => {
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
      this.getMatches();
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
  