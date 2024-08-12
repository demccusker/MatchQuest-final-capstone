<template>
  

  <div class="dashboard-container">
      <aside class="sidebar">
        <div class="nav-top">
          <router-link v-bind:to="{ name: 'home' }" class="nav-link">Home</router-link>
          <router-link v-bind:to="nextPage" class="nav-link">Dashboard</router-link>
          <router-link to="/player/past-tournaments" class="nav-link">Past Tournaments</router-link>
          <router-link to="/tournaments" class="nav-link">Tournaments</router-link>
         
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
        <div class = tournament_row>
          <h2 class = tournament_title>Upcoming Tournaments</h2>
          <tournament-list v-bind:filters="upcomingFilter"/> 
        </div>

        <div class=tournament_row>
          <h2 class=tournament_title>Active Tournaments</h2>
          <tournament-list v-bind:filters="activeFilterWithEndDate" />
        </div>

        <div class = "tournament_row">
          <h2 class = tournament_title>Past Tournaments</h2>
          <tournament-list v-bind:filters="pastFilter"/>
        </div>
    </main>
    </div>

</template>


<script>
import TournamentList from '../components/TournamentList.vue';

export default {
  components: {

    TournamentList
  },
  data() {
    return {
      endDateFilter: {
        filterProperty: "endDate",
        value: null,
        condition: "IS",
        concat:
        [
          {
            filterProperty: "endDate",
            value: new Date(),
            condition: ">",
            operator: "|"
          }
        ]
      },
      upcomingFilter: [
        {
          filterProperty: "startDate",
          value: new Date(),
          condition: ">"
        },
        {
          filterProperty: "endDate",
          value: null,
          condition: "IS",
          concat:
          [
            {
              filterProperty: "endDate",
              value: new Date(),
              condition: ">",
              operator: "|"
            }
          ]
        }
      ],
      activeFilter: [
        {
          filterProperty: "startDate",
          value: new Date(),
          condition: "<="
        },
        {
          filterProperty: "endDate",
          value: null,
          condition: "IS",
          concat:
          [
            {
              filterProperty: "endDate",
              value: new Date(),
              condition: ">",
              operator: "|"
            }
          ]
        }
      ],
      pastFilter: [
        {
          filterProperty: "endDate",
          value: new Date(),
          condition: "<"
        },
        {
          filterProperty: "endDate",
          value: null,
          condition: "IS NOT"
        }
      ]
    }

<<<<<<< Updated upstream
=======


  },
  computed:{
    nextPage(){
      
      const currentRole = this.$store.state.currentRole;
      if(currentRole==="player"){
        return "/player/dashboard";
      }else if(currentRole==="organizer"){
        return "/organizer/dashboard";
      }else
        return "/";
    }
>>>>>>> Stashed changes
  }
}
</script>


<style>
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