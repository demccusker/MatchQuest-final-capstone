<template>
  <div class="dashboard-container">
    <nav-bar></nav-bar>
    <main class="dashboard-content">
      <h1>Player Dashboard</h1>
      <!-- Checking store state
      <h2>{{ this.$store.state }}</h2> -->
      <h2>Upcoming Tournaments</h2>
      <tournament-list v-bind:limit="2" v-bind:filters="upcomingFilter"></tournament-list>
      <div class="section-content"></div>
      <router-view></router-view>
    </main>
  </div>
</template>

<script>
import TournamentList from '../components/TournamentList.vue';
import NavBar from '../components/NavBar.vue';

export default {
  components: {
    TournamentList,
    NavBar
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
.dashboard-container {
  display: flex;
  height: 100vh;
  font-family: 'Roboto', sans-serif;
}


/* this is the css for the player dashboard */
.dashboard-content {
  flex: 1;
  padding: 40px;
  background-color: #ecf0f1;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
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
</style>
