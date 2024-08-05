<template>
    <div>
      <h1> Active Tournaments
      </h1>
    <tournament-list/>
          
    </div>
    
    <h1>Tournament Creator</h1>
    
    <button class="create">Create</button>
    <button class="edit">Edit/Modify</button>
    <!-- Component name here to type later> <!-->
    
</template> 


<script>
import CreateTournament from '../components/CreateTournament.vue';
import TournamentService from '../services/TournamentService';
import TournamentList from '../components/TournamentList.vue';

export default{
     components: {
        CreateTournament,
        TournamentList
     },
     data() {
        return {
            tournaments: []
        }
    }
}
</script>


<style>
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