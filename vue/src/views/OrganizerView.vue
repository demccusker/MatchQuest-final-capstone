<template>
  <div class="dashboard-container">
    <nav-bar></nav-bar>
    <main class="dashboard-content">
      <h1>Organizer Dashboard</h1>
      <!-- <h2>This is the user's current role:{{ this.$store.state.currentRole }}</h2>
        <h2>This is user's intended role: {{ this.$store.state.isOrganizer }}</h2> -->
      <!-- Checking store state -->
      <!-- <h2>{{ this.$store.state }}</h2> -->
        <h2 class ="tournament_title upcoming">Upcoming Tournaments</h2>
        <tournament-list v-bind:filters="upcomingFilter"></tournament-list>

      <h2 class = "your_tournaments">Past and Active Tournaments</h2>
      <tournament-list v-bind:filters="creatorFilter"></tournament-list>
    </main>
  </div>

</template>


<script>
import TournamentList from '../components/TournamentList.vue';
import NavBar from '../components/NavBar.vue';
export default {
  components: {
    NavBar,
    TournamentList
  },
  data() {
    return {
      creatorFilter: [
        {
          filterProperty: "creatorId",
          value: this.$store.state.user.id,
          condition: "="
        }
      ],
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
        },
        {
          filterProperty: "creatorId",
          value: this.$store.state.user.id,
          condition: "="
        }
      ]
    }
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
  padding: 30px;
  background-color: white;
  box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.05); 
}

h1 {
  font-size: 70px;
  margin-bottom: 20px;
  text-align:center;
  padding-bottom: 30px;
}

.your_tournaments{
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