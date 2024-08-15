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
            <img :src=gameLogos[tournament.gameId] alt="Logo" />
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
            },
            gameLogos:{
                //Chess
                1: '/assets/chess_logo.jpg',
                //Golf
                2: '/assets/golf_logo.jpg',
                //CS2
                3: '/assets/cs2_logo.jpg',
                //War
                4: '/assets/war_logo.jpg',
                //Minecraft
                5: '/assets/minecraft_logo.jpg',
                //Tennis
                6: '/assets/tennis_logo.jpg',
                //Pickleball
                7: '/assets/pickleball.png',
                //Breakdance
                8: '/assets/breakdancing.png',
                //Pool  
                9: '/assets/pool.png',
                //Corn Hole
                10: '/assets/cornhole.png',
                //Horshoes
                11: '/assets/horseshoe.png'


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
  grid-template-columns: 1fr auto; /* 1fr allows the first column to take up remaining space */
  gap: 1rem;
  border-radius: 8px;
  background-color: white;
  padding: 1rem;
  align-items: stretch; /* Ensure all items stretch to match the height of the tallest item */
  min-height: 80px; /* Ensure consistent height */
}

.text-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  flex-grow: 1; /* Allow the text content to grow and fill available space */
  overflow: hidden;
}

.logo {
  display: flex;
  align-items: center;
  justify-content: center;
}

.tournament-container .tournamentName,
.tournament-container .tournamentShortPreview {
  font-size: clamp(0.75rem, 2vw, 1rem); /* Adjusts between 0.75rem and 1rem based on viewport width */
  color: black;
  margin: 0;
  text-align: left;
}

.logo img {
  width: 70px; /* Set desired width */
  height: 70px; /* Set desired height */
  border-radius: 50%; /* Makes the image circular */
  object-fit: cover; /* Ensures the image covers the circle without distortion */
}
</style>