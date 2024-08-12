<template>
 <div class="dashboard-container">
      <aside class="sidebar">
        <div class="nav-top">
            <router-link v-bind:to="{ name: 'home' }" class="nav-link">Home</router-link>
            <router-link to="/my-tournaments" class="nav-link">My Tournaments</router-link>
        <router-link to="/past-tournaments" class="nav-link">Past Tournaments</router-link>
      </div>
      <div class="nav-bottom">
       
        <router-link to="/logout" class="nav-link">Logout</router-link>
      </div>
      </aside>
      <main class="dashboard-content">
        <h1>Tournament Details</h1>
        <TournamentDetails :tournament="this.tournament"/>

      </main>
    </div>
   
    
</template>

<script>
import TournamentDetails from '../components/TournamentDetails.vue';
import TournamentService from '../services/TournamentService';

export default {
  components: {
    TournamentDetails
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
.nav-top {
  display: flex;
  flex-direction: column;
}
.nav-bottom {
  display: flex;
  flex-direction: column;
}
.nav-link {
  color: white;
  text-decoration: none;
  display: block;
  padding: 10px 0;
  margin-bottom: 10px;
  justify-content: center;
  align-items: center;
  padding: 10px;
  margin-bottom: 10px;
  transition: background-color 0.3s ease;
  border: 1px solid #34495e;
  border-radius: 4px;
  height: 20px;
}

.nav-link:hover {
  background-color: #34495e;
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