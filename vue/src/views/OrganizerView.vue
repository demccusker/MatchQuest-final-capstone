<template>
    <div class="dashboard-container">
      <aside class="sidebar">
        <div class="nav-top">
        <router-link to="/organizer/create-tournament" class="nav-link">Create Tournament</router-link>
        <router-link to="/organizer/past-tournaments" class="nav-link">Past Tournaments</router-link>
      </div>
      <div class="nav-bottom">
        <router-link to="/player/dashboard" v-on:click="goToPlayerView" class="nav-link">Switch to Player</router-link>
        <router-link to="/organizer/profile" class="nav-link">Profile</router-link>
        <router-link to="/logout" class="nav-link">Logout</router-link>
      </div>
      </aside>
      <main class="dashboard-content">
        <h1>Organizer Dashboard</h1>
        <h2>This is the user's current role:{{ this.$store.state.currentRole }}</h2>
        <h2>This is user's intended role: {{ this.$store.state.isOrganizer }}</h2>
       <tournament-list-by-creator-id/>
      </main>
    </div>
    
  </template>


<script>
import TournamentListByCreatorId from '../components/TournamentListByCreatorId.vue';
export default {
  components: {
    TournamentListByCreatorId
  },
  methods:{
    goToPlayerView(){
      this.$store.commit("UPDATE_CURRENT_ROLE","player");
      this.$store.push("/player/dashboard");
    }

  }

}
</script>
<style scoped>
.dashboard-container {
  display: flex;
  height: 100vh; /* Ensure full height */
}

.sidebar {
  width: 200px;
  background-color: #2c3e50;
  color: white;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.nav-top,
.nav-bottom {
  display: flex;
  flex-direction: column;
}

.nav-link {
  color: white;
  text-decoration: none;
  display: block;
  padding: 10px;
  margin-bottom: 10px;
  justify-content: center;
  align-items: center;
  transition: background-color 0.3s ease;
  border: 1px solid #34495e;
  border-radius: 4px;
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