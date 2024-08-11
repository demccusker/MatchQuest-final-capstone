<template>
    <h1>Active Tournaments</h1>
    <div class="active-tournaments-container">
        <tournament-list v-bind:filters="activeFilter" />
    </div>
</template>

<script>
import TournamentList from '../components/TournamentList.vue';
import TournamentService from '../services/TournamentService'; // Import your service

export default {
    components: {
        TournamentList
    },
    data() {
        return {
            selectedTournament: null,
            activeFilter:   [
                            {
                              filterProperty: "startDate",
                              value: new Date(),
                              condition: "<"
                            },
                            {
                              filterProperty: "endDate",
                              value: new Date(),
                              condition: ">"
                            }
                          ],
        };
    },
    mounted() {

        this.fetchTournamentData();
    },
    methods: {
        fetchTournamentData() {
            const tournamentId = this.$route.params.id;
            TournamentService.getTournament(tournamentId)
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

<style>
.active-tournaments-container {
    display: flex;
    flex-wrap: wrap;
}

.tournament-list {
    display: flex;
    flex-wrap: wrap;
}
</style>