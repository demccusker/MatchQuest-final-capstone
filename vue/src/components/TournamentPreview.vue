<template>
    <div class = "tournament-container"
    :style="hoverStyle"
    v-on:mouseover="hoverOver"
    v-on:mouseleave="hoverLeave"    
         >
        <h2>
        Tournament Name: <br>
        {{ tournament.name }}
        </h2>
        <!-- return game name, not game id <h3>
        {{ tournament.game_id }}
        </h3> -->
        <p>
        {{ tournament.isScrim }} 
        <!-- make this into an icon -->
        {{ tournament.location }}
        Start Date: {{ tournament.startDate }}
        End Date: {{ tournament.endDate }}
        {{ tournament.description }}
        </p>
    </div>
    
    
</template>

<script>
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
        }
    }
}


</script>

<style>

.tournament-container{
    /* display: flex;
  flex-wrap: wrap;
  justify-content: space-around; */
  border-radius: 8px;
  background-color: white;
  padding: 2rem 10rem;
}
</style>