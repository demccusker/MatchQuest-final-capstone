<template>
 <div class="dashboard-container">
      <nav-bar></nav-bar>
      <main class="dashboard-content">
        <h1>Tournament Details</h1>
        <h2>{{ this.$store.state }}</h2>
        <p>{{ tournament.tournamentId }} 
          
        </p>
        <TournamentDetails :tournament="this.tournament"
        />
        <tournament-brackets v-bind:tournamentId="this.$route.params.tournamentId" v-bind:tournamentDate="this.tournament.startDate"></tournament-brackets>
        <span>{{ this.tournament.startDate }}</span>

      </main>
    </div>
   
    
</template>

<script>
import TournamentBrackets from '../components/TournamentBrackets.vue';
import TournamentDetails from '../components/TournamentDetails.vue';
import TournamentService from '../services/TournamentService';
import NavBar from '../components/NavBar.vue';
export default {
  components: {
    TournamentDetails,
    TournamentBrackets,
    NavBar
  },
  data() {
    return {
      tournament: {}
    }
  },
  created() {
    const tournamentId = this.$route.params.tournamentId;
    TournamentService.getTournament(tournamentId)
      .then(response => {
        if (response.status === 200) {
          this.tournament = response.data;
        }
      })
      .catch(error => {
        if (error.response && error.response.status === 404) {
          this.$router.push({ name: 'notFound' });
        } else {
          console.error('Error fetching tournament details:', error);
        }
      });
  }
}
</script>
<style scoped>
.dashboard-container {
  display: flex;
  height: 100vh;
}


.dashboard-content {
  flex-grow: 1;
  padding: 20px;
  background-color: rgb(219, 236, 235);
}

h1 {
  font-size: 40px;
  margin-bottom: 20px;
}

</style>