<template>
    <div class="dashboard-container">
      <aside class="sidebar">
        <div class="nav-top">
            <router-link v-bind:to="{ name: 'home' }" class="nav-link">Home</router-link>
          <router-link to="/player/past-tournaments" class="nav-link">Past Tournaments</router-link>
         
        </div>
        <div class="nav-bottom">
            <router-link to="/organizer/dashboard" 
                v-on:click="goToOrganizerView" 
                v-if ="isButtonVisible"
                class="nav-link">Switch to Organizer</router-link>
            <router-link to="/player/profile" class="nav-link">Profile</router-link>
            <router-link to="/login" class="nav-link">Logout</router-link>
        </div>
      </aside>
      <main class="dashboard-content">
        <h1>Player Dashboard</h1>
        <h2>This is the user's current role: {{ this.$store.state.currentRole }}</h2>
        <h2>This is user's intended role: {{ this.$store.state.isOrganizer }}</h2>

        <h2>Upcoming Tournaments</h2>
        <!-- Limit 2 is a placeholder number depending on css styling for flex size -->
        <tournament-list v-bind:limit="2"
                         v-bind:filters="upcomingFilter"></tournament-list>
        <div class="section-content">

        </div>
        <router-view></router-view>
      </main>
    </div>
  </template>
  
  <script>
  import TournamentList from '../components/TournamentList.vue';
  export default {
    components: {
      TournamentList,
    },
    data(){
      return{
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
        ]
      }
    },
    computed:{
      isButtonVisible(){
        return this.$store.state.isOrganizer == true;
      }
    },
    methods:{
      goToOrganizerView(){
        this.$store.commit("UPDATE_CURRENT_ROLE","organizer");
        this.$store.push("/organizer/dashboard");
      }
    }
  
  };
  </script>

  <style scoped>
  .dashboard-container {
  display: flex;
  height: 100vh;
  font-family: 'Roboto', sans-serif;
}

.sidebar {
  width: 220px;
  background: linear-gradient(135deg, #2c3e50, #34495e);
  color: white;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.nav-top, .nav-bottom {
  display: flex;
  flex-direction: column;
}

.nav-link {
  color: white;
  text-decoration: none;
  padding: 10px;
  margin-bottom: 15px;
  display: flex;
  align-items: center;
  transition: all 0.3s ease;
  border-radius: 8px;
}

.nav-link i {
  margin-right: 10px;
  font-size: 1.2rem;
}

.nav-link:hover {
  background-color: rgba(255, 255, 255, 0.1);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
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
