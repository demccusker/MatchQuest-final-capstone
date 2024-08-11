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
    props:{
        limit:{
            type: Number,
            default: 0
        },
        filters:{
            type: Array,
            default: []
        }
    },
    components: {
        TournamentPreview
    },
    data() {
        return {
            tournaments: []
        }
    },
    created() {
        //Limit here is tentatively 5 unless flex sizing demands more below
        const query = {
            filters: this.filters,
            operator: "&",
            limit: this.limit
        }

        TournamentService.getTournamentByFilter(query).then((response) => {
            if (response.status == 200) {
                console.log(response.data);
                this.tournaments = response.data;
            }
        }).catch(error => {
            console.log(error);
        })
            
        //return this.limit ? this.tournaments.slice(0, this.limit) : this.tournaments;
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
.tournament-list-container{
    display:flex;
    padding: 10px;
    justify-content: space-between;
    gap: 30px;
    

}

</style>