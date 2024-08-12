<template>
    <div class = "tournament-container"
    :style="hoverStyle"
    v-on:mouseover="hoverOver"
    v-on:mouseleave="hoverLeave"    
         >
        <div class = "textContent">
            <h2 class="tournamentName">
                {{ tournament.name }}
            </h2>
        
            <p class = "tournamentShortPreview">
                {{ gameName }}
                <br>
                {{ tournament.startDate }}
            </p>
        </div>
        <div class="logo">
            <!-- Replace with your logo or image -->
            <img src="../assets/match_logo.jpg" alt="Logo" />
    </div>
        
    </div>
    
    
</template>

<script>
import GamesService from "../services/GamesService.js";
export default {
    props : {
        tournament: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            gameName: '',
            hoverStyle:{
                backgroundColor: 'white'
            }
        }
    },
    methods:{
        hoverOver(){
            this.hoverStyle.backgroundColor = 'lightgrey';
        },
        hoverLeave(){
            this.hoverStyle.backgroundColor = 'white';
        },
        getGameName(){
            const gameId = this.tournament.gameId;
            GamesService.getGameNameById(gameId)
            .then(response=>
            {
                if(response.status == 200){
                    this.gameName = response.data.name;
                }
            })
        }
        
    },
    created(){
        this.getGameName(this.tournament);
    }
}


</script>

<style>

.tournament-container {
  display: grid;
  grid-template-columns: 1fr auto; /* Two columns: one flexible and one auto-sized */
  grid-template-rows: auto; /* Rows will adjust based on content */
  gap: 1rem; /* Space between columns */
  border-radius: 8px;
  background-color: white;
  padding: 1rem; /* Adjust padding as needed */
}

.text-content {
  display: flex;
  flex-direction: column;
  justify-content: center; /* Center the text content vertically */
  
}

.logo {
  display: flex;
  align-items: center; /* Center the logo vertically */
  justify-content: center; /* Center the logo horizontally */
}

.tournament-container .tournamentName {
  font-size: 0.75rem; /* Adjust the font size */
  color: black;
  margin: 0; /* Remove default margin */
  text-align: left; /* Align text to the left */
}

.tournament-container .tournamentShortPreview {
  font-size: 0.75rem;
  margin-top: 0.5rem; /* Add some space above */
}

.logo img {
  max-width: 70px; /* Adjust size of the logo as needed */
  height: auto; /* Maintain aspect ratio */
}
</style>