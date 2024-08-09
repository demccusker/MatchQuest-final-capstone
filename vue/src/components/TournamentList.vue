<template>
    
        <div class="tournament-list-container">
            <a href=""
                v-for="tournament in tournaments" 
                v-bind:key="tournament.tournament_id"
                v-on:click="sendToTournamentDetailsPage(tournament)"
                >
                    <tournament-preview v-bind:tournament="tournament"/>
            </a>
                        
        </div>  
    
    
</template>


<script>
import TournamentService from '../services/TournamentService';
import TournamentPreview from './TournamentPreview.vue';

export default {
    components: {
        TournamentPreview
    },
    data() {
        return {
            tournaments: []
        }
    },
    created() {
        TournamentService.getTournaments().then(response => {
            if (response.status == 201) {
                this.tournaments = response.data;
            }
        }).catch(error => {
            console.log(error);
        });
    },
    methods:{
        sendToTournamentDetailsPage(tournament){
            console.log(tournament)
            this.$router.push({ name: 'tournamentDetails',params: { tournamentId: tournament.tournamentId }});
        },

    }
    
}
</script>

<style>

#tournamentList {
  margin: 25px 0px;
}

</style>