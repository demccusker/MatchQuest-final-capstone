<template>
    <div class="dashboard-container">
      <nav-bar></nav-bar>
      <main class="dashboard-content">
        <h1>Organizer Dashboard</h1>
        <!-- <h2>This is the user's current role:{{ this.$store.state.currentRole }}</h2>
        <h2>This is user's intended role: {{ this.$store.state.isOrganizer }}</h2> -->
        <!-- Checking store state -->
        <h2>{{ this.$store.state }}</h2>
       <tournament-list-by-creator-id/>
      </main>
    </div>
    
  </template>


<script>
import TournamentListByCreatorId from '../components/TournamentListByCreatorId.vue';
import NavBar from '../components/NavBar.vue';
export default {
  components: {
    TournamentListByCreatorId,
    NavBar
  }

}
</script>
<style scoped>
.dashboard-container {
  display: flex;
  height: 100vh; /* Ensure full height */
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