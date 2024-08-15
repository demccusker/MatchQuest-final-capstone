<template>
  <div class="dashboard-container">
    <nav-bar></nav-bar>
    <main class="dashboard-content">
      <div class=tournament_row>
        <!-- Checking store state -->
        <!-- <h2>{{ this.$store.state }}</h2> -->
        <h2 class=tournament_title>Upcoming Tournaments</h2>
        <tournament-list v-bind:filters="upcomingFilter" />
      </div>

      <div class=tournament_row>
        <h2 class=tournament_title>Active Tournaments</h2>
        <tournament-list v-bind:filters="activeFilter" />
      </div>

      <div class="tournament_row">
        <h2 class=tournament_title>Past Tournaments</h2>
        <tournament-list v-bind:filters="pastFilter" />
      </div>
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
  }
 
}
</script>


<style>
.dashboard-container {
  display: flex;
  position: fixed;
  width: 100%;
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
  position: relative;
  width: 100rem;
  padding: 20px;
  background-color: rgb(219, 236, 235);
}

h1 {
  font-size: 40px;
  margin-bottom: 20px;
}
</style>