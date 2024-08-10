<template>
    
    <div class="tournament-list-container">
        <a href=""
            v-for="tournament in limitTournamentPreviewList"
            
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
    const id = this.$store.state.user.id;
    console.log(id);
    console.log(this.$store.state.token);
    TournamentService.getTournamentsByCreatorId(id,2).then(response => {
        if (response.status == 200) {
            this.tournaments = response.data;
        }
    }).catch(error => {
        console.log(error);
    });
},
computed: {
    limitTournamentPreviewList(){
            console.log("hello")
            console.log(this.tournaments)
            return this.limit ? this.tournaments.slice(0, this.limit) : this.tournaments
        }
},
methods:{
    sendToTournamentDetailsPage(tournament){
        console.log(tournament)
        this.$router.push({ name: 'tournamentDetails',params: { tournamentId: tournament.tournamentId }});
    }
}

}
</script>

<style>

#tournamentList {
margin: 25px 0px;
}

</style>