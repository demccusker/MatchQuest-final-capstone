<template>
    <div class="dashboard-container">
      <aside class="sidebar">
        <div class="nav-top">
        <router-link to="/organizer/create-tournament" class="nav-link">Create Tournament</router-link>
        <router-link to="/organizer/past-tournaments" class="nav-link">Past Tournaments</router-link>
      </div>
      <div class="nav-bottom">
        <router-link to="/player/dashboard" class="nav-link">Switch to Player</router-link>
        <router-link to="/organizer/profile" class="nav-link">Profile</router-link>
        <router-link to="/logout" class="nav-link">Logout</router-link>
      </div>
      </aside>
      <main class="dashboard-content">
        <h1>Organizer Dashboard</h1>
       <tournament-list-by-creator-id/>
      </main>
    </div>
    
  </template>

<script>
import TournamentListByCreatorId from '../components/TournamentListByCreatorId.vue';
export default {
  components: {
    TournamentListByCreatorId
  }

}
</script>
<style scoped>
.dashboard-container {
  display: flex;
  height: auto;
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
