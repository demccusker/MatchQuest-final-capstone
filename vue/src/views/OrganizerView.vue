<template>
  <div class="dashboard-container">
    <nav-bar></nav-bar>
    <main class="dashboard-content">
      <h1>Organizer Dashboard</h1>
      <!-- <h2>This is the user's current role:{{ this.$store.state.currentRole }}</h2>
        <h2>This is user's intended role: {{ this.$store.state.isOrganizer }}</h2> -->
      <!-- Checking store state -->
      <h2>{{ this.$store.state }}</h2>
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
      ]
    }
  }

}
</script>
<style scoped>
.dashboard-container {
  display: flex;
  height: 100vh;
  /* Ensure full height */
}

.dashboard-content {
  flex-grow: 1;
  padding: 30px;
  background-color: white;
  box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.05); /* Inner shadow for a soft look */
  border-radius: 8px;
  margin: 20px;
}

h1 {
  font-size: 40px;
  margin-bottom: 20px;
}
</style>