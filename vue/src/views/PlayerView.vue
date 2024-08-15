<template>
  <div class="dashboard-container">
    <nav-bar></nav-bar>
    <main class="dashboard-content">

      <h1>Player Dashboard</h1>
      <!-- Checking store state
      <h2>{{ this.$store.state }}</h2> -->
      <h2 class = "tournament_title upcoming">Upcoming Tournaments</h2>
      <tournament-list  v-bind:filters="upcomingFilter"></tournament-list>

      <h2 class="tournament_title registered">Tournaments Registered</h2>
      <tournaments-registered-list></tournaments-registered-list>
      <div class="section-content"></div>
      <router-view></router-view>
    </main>


  </div>
</template>

<script>
import TournamentList from '../components/TournamentList.vue';
import TournamentsRegisteredList from '../components/TournamentsRegisteredList.vue';
import NavBar from '../components/NavBar.vue';

export default {
  components: {
    TournamentList,
    NavBar,
    TournamentsRegisteredList
  },
  data() {
    return {
      upcomingFilter: [
        {
          filterProperty: "endDate",
          value: null,
          condition: "IS"
        },
        {
          filterProperty: "startDate",
          value: new Date(),
          condition: ">"
        }
      ]
      
    };
  }

};
</script>

<style scoped>
h1{
  font-size: 70px;
  margin-bottom: 20px;
  text-align:center;
  padding-bottom: 30px;
}
.dashboard-container {
  display: flex;
  height: 100vh;
  font-family: 'Roboto', sans-serif;
}


/* this is the css for the player dashboard */
.dashboard-content {
  flex-grow: 1;
  padding: 30px;
  background-color: #ecf0f1;
  /* overflow-y: auto; */
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.dashboard-title {
  font-size: 2rem;
  color: #34495e;
  margin-bottom: 30px;
}

@media (max-width: 768px) {
  .dashboard-container {
    flex-direction: column;
  }

  .sidebar {
    width: 100%;
    flex-direction: row;
    justify-content: space-between;
  }

  .dashboard-content {
    padding: 20px;
  }

  .dashboard-title {
    font-size: 1.5rem;
  }


}
.registered{
  width: 100%;
  padding: 30px;
  padding-left: 20px;
  font-family: 'Arial', sans-serif;
  font-size: 30px;
  color: white;
  background: linear-gradient(135deg, #6e8fe2, #34495e);
  border-radius: 10px;
}
</style>
