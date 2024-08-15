<template>
    <div class="container">
  
      <div class="header-section"> 
        <h2>
          Match Players: {{ player1UserDetails.displayName }} vs.
          {{ player2UserDetails.displayName }}
        </h2>
        <div class="buttons">
          <button v-on:click="sendToEditMatchForm">Edit</button>
          
          
        </div>
      </div> 
  
      <div class="next-row">
        <div class="tournament-info">
        <h4>
          Tournament: {{ tournament.name }} <br>
          Location: {{ tournament.online ? "Online" : tournament.location }} <br>
          Date: {{ tournament.startDate }} <br>
          <div v-show="time"> Time: {{ match.matchStartTime }} <br></div>
        </h4>
    </div>
        <div class="buttons">
            <button v-on:click="sendToTournament">Return to Tournament</button>
        </div>
      </div>
  
      <div class="bottom-row">
        <p>
          Final Scores: {{ match.player1Score }} - {{ match.player2Score }} <br>
          Match Winner: {{ match.winnerId == match.player1Id ? player1UserDetails.displayName :
          player2UserDetails.displayName }}
          
        </p>
      </div>

    </div>
  </template>

<script>
import MatchService from '../services/MatchService';
import TournamentService from '../services/TournamentService';
import UserDetailsService from '../services/UserDetailsService';
import TournamentBrackets from './TournamentBrackets.vue';


export default {
    components: {
        TournamentBrackets
    },

    data() {
        return {
            match: {

            },
            tournament: {

            },
            player1UserDetails: {

            },
            player2UserDetails: {

            },
            time: false

        }
    },
    created() {
        this.getMatch();
        this.getTournament();
        


    },
    methods: {
        getMatch() {
            MatchService.getMatchById(this.$route.params.matchId)
                .then(response => {
                    if (response.status === 200) {
                        this.match = response.data;
                    }
                })
                .catch(error => {
                    console.error('Error fetching match details:', error);
                });
        },
        getTournament() {
            TournamentService.getTournament(this.$route.params.tournamentId)
                .then(response => {
                    if (response.status === 200) {
                        this.tournament = response.data;
                    }
                })
                .catch(error => {
                    console.error('Error fetching tournament details:', error);
                });
        },
        getUserDetailsPlayer1() {
            UserDetailsService.getUserDetails(this.match.player1Id)
                .then(response => {
                    if (response.status === 200) {
                        this.player1UserDetails = response.data;
                    }
                })
                .catch(error => {
                    console.error('Error fetching user details:', error);
                });
        },
        getUserDetailsPlayer2() {
            UserDetailsService.getUserDetails(this.match.player2Id)
                .then(response => {
                    if (response.status === 200) {
                        this.player2UserDetails = response.data;
                    }
                })
                .catch(error => {
                    console.error('Error fetching user details:', error);
                });
        },
        sendToEditMatchForm() {
            const tournamentId = this.$route.params.tournamentId;
            const matchId = this.$route.params.matchId;
            this.$router.push(`/tournaments/${tournamentId}/${matchId}/update`);
        },
        sendToTournament() {
            const tournamentId = this.$route.params.tournamentId;
            this.$router.push(`/tournaments/${tournamentId}`);
        },
        isThereATime() {
            time = (this.match.matchStartTime != null);
        }
    },
    watch: {
        'match.player1Id': function (newPlayer1Id) {
            if (newPlayer1Id) {
                this.getUserDetailsPlayer1();
            }
        },
        'match.player2Id': function (newPlayer2Id) {
            if (newPlayer2Id) {
                this.getUserDetailsPlayer2();
            }
        },
        'match.matchStartTime': function (newMatchStartTime) {
            if (newMatchStartTime) {
                this.isThereATime();
            }
        }
    },
}
</script>

<style scoped>
.container {
 border: 1px solid #ddd;
  padding: 1rem;
  margin: 1rem;
  background-color: #f9f9f9;
  height: 35%;
  
  display: flex;
  flex-direction: column; /* Stack children vertically */
}

.header-section {
width: 100%;
  display: flex;
  flex-direction: row; 
  align-items: flex-start; 
    justify-content: space-between; 
}

.next-row {
  width: 100%;
  display: flex;
  flex-direction: row; 
  align-items: flex-start; 
  justify-content: space-between; 
}

.bottom-row {
width: 100%;
  display: flex;
  flex-direction: row; 
  align-items: flex-start; 
    justify-content: space-between; 
}

.tournament-info {
    align-items: left;
    justify-content: left;
    align-items: left;
    text-align: left;
}

.tournament-info h4 { 
  margin: 0; 
}
.buttons { 

  display: flex; 
  flex-direction: column;
  align-self: flex-end; 
  padding: 10px;
  justify-content: space-between;
}

</style>