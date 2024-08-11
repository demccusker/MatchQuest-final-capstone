<template>
  <div id="nav">
    <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
    <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    <router-link v-bind:to="{ name: 'login' }" v-else>Login</router-link> &nbsp;|&nbsp;
    <router-link v-bind:to="{ name: 'register' }" v-if="$store.state.token == ''">Register</router-link> &nbsp;|&nbsp;
    <router-link v-bind:to="{ name: 'tournament' }">Tournament</router-link>

    <!-- <router-link v-bind:to="{ name: 'userDetails'}">User Details</router-link> -->
  </div>

  <div class=tournaments>

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

  </div>


</template>


<script>

import TournamentService from '../services/TournamentService';
import TournamentList from '../components/TournamentList.vue';

export default {
  components: {

    TournamentList
  },
  data() {
    return {
      
      activeFilterWithNull: [
        {
          filterProperty: "startDate",
          value: new Date(),
          condition: "<"
        },
        {
          filterProperty: "endDate",
          value: new Date(),
          condition: ">"
        }

      ],
      activeFilterWithEndDate: [
        {
          filterProperty: "startDate",
          value: new Date(),
          condition: "<"
        },
        {
          filterProperty: "endDate",
          value: new Date(),
          condition: ">"
        }

      ],
      pastFilter: [
        {
          filterProperty: "endDate",
          value: new Date(),
          condition: "<"
        }
      ],
      upcomingFilter: [
        {
          filterProperty: "startDate",
          value: new Date(),
          condition: ">"
        }
      ],
      activeQueries: [
        {
          filters: this.activeFilterWithEndDate,
          operator: "|",
          limit: 3,
        },
        {
          filters: this.activeFilterWithNull,
          operator: "&",
          limit: 3,
        },
      ],
    }



  }
}
</script>


<style>
#tournamentList {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;

}

#createForm {
  display: flex;
  justify-content: center;
}

/* Title names of each tournament containers */
.tournament-container h2 {
  font-size: 20px;
  /* Adjust the font size */
  color: black;
  /* Change the text color */
  margin-top: 10px;
  /* Add some space below the heading */
  margin-left: -150px;
  text-align: center;
  /* Center the heading text */
}

.tournament-container {
  width: 10%;
  height: 30%;
}



/* .bracket-maker {
  text-align: center;
  padding: 2rem;
  background: linear-gradient(to bottom, #007bff, #6610f2);
  color: white;
.create button {
  background: #343a40;
  color: white;
  padding: 0.75rem 2rem;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  transition: background 0.3s;
} */
</style>