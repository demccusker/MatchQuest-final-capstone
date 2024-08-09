<template>
    <div>
        <edit-tournament v-if="selectedTournament" v-bind:tournament="selectedTournament"/>
    </div>
</template>

<script>
import EditTournament from '../components/EditTournament.vue';
import TournamentService from '../services/TournamentService'; // Import your service

export default {
    components: {
        EditTournament
    },
    data() {
        return {
            selectedTournament: null 
        };
    },
    mounted() {
        
        this.fetchTournamentData();
    },
    methods: {
        fetchTournamentData() {
            const tournamentId = this.$route.params.id; 
            TournamentService.getTournamentById(tournamentId) 
                .then((response) => {
                    if (response.status === 200) { 
                        this.selectedTournament = response.data; 
                    } else {
                        console.error('Error fetching tournament data:', response.status);   
                    }
                })
                .catch((error) => {
                    console.error('Error fetching tournament data:', error);
                });
        }
    }
};
</script>