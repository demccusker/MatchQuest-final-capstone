<template>
    <bracket-list v-bind:bracket="bracket"></bracket-list>
</template>

<script>
import BracketService from '../services/BracketService';
import BracketList from './BracketList.vue';

export default {
    prop : {
        tournamentId: {
            type: Number,
            required: true
        }
    },
    components: {
        BracketList
    }, 
    data() {
        return {
            bracket: {
                
            }
        }
    },
    created() {
        this.bracket = this.getBracket();
        console.log(this.bracket);
    },
    methods: {
        getBracket() {
            
            BracketService.getBracketsByTournamentId(this.$route.params.tournamentId).then(response => {
                if (response.status === 200) {
                    this.bracket = response.data;
                }
            }).catch(error => {
                console.log(error);
            })
        }
    } 
}
</script>