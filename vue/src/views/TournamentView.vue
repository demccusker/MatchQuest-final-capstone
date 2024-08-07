<template>
   <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      <router-link v-bind:to="{ name: 'login' }" v-else>Login</router-link> &nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'register' }" v-if="$store.state.token == ''">Register</router-link> &nbsp;|&nbsp;
      <!-- <router-link v-bind:to="{ name: 'userDetails'}">User Details</router-link> -->
    </div>
    <div>
      <h1> Active Tournaments
      </h1>
    </div>
<div id="tournamentList"><tournament-list/></div>
    
    
    <!-- <button class="create">Create</button>
    <button class="edit">Edit/Modify</button> -->
    <div id="createForm"><create-tournament/></div>
    
</template> 


<script>
import CreateTournament from '../components/CreateTournament.vue';
import TournamentService from '../services/TournamentService';
import TournamentList from '../components/TournamentList.vue';

export default{
     components: {
        CreateTournament,
        TournamentList
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