<template>
<div class = "tournament-container" v-for="tournament in tournaments" v-bind:key="tournament">
<tournament-preview v-bind:tournament="tournament"/>
</div>
</template>


<script>
import TournamentService from '../services/TournamentService';
import TournamentPreview from './TournamentPreview.vue';

export default {
    component: {
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
    }
}
</script>