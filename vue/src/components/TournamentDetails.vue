<template>
    <div class="tournament-details">
      <div class="tournament-header">
        <h2>{{ tournament.name }}</h2>
        <button v-if="registrationStatus !== 'Registered'" @click="showConfirmModal = true">Register</button>
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
      <h3>Registration Status: {{ registrationStatus }}</h3>
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
      registrationStatus: 'Not Registered' ,
      tournaments: {},
      matches: []
    };
  },
  methods: {
    confirmRegistration() {
      this.showConfirmModal = false;
      this.showSuccessModal = true;
      this.registrationStatus = 'Registered'; 
    },
    getMatches() {
        matchService.getMatchesByTournamentId(this.$route.params.tournamentId).then(response => {
          if (response.status === 200) {
            this.matches = response.data;
          }
        }).catch(error => {
          console.log(error);
        })
      }
  },
  created() {
      const tournamentId = this.$route.params.tournamentId;
      TournamentService.getTournament(tournamentId)
        .then(response => {
          if (response.status === 200) {
            this.tournaments = response.data;
          }
        })
        .catch(error => {
          if (error.response && error.response.status === 404) {
            this.$router.push({ name: 'notFound' });
          } else {
            console.error('Error fetching tournament details:', error);
          }
        });
    }, 
    
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