<template>
    <div class="dashboard-container">
        <nav-bar></nav-bar>
        <main class="dashboard-content">
            <h1>Edit Match</h1>
            <edit-match-form v-if="selectedMatch" v-bind:match="selectedMatch"></edit-match-form>
        </main>
    </div>

</template>

<script>
import NavBar from '../components/NavBar.vue';
import EditMatchForm from '../components/EditMatchForm.vue';
import MatchService from '../services/MatchService';

export default {
    components: {
        NavBar,
        EditMatchForm
    },
    data() {
        return {
            selectedMatch: null
        }
    },
    created() {
        this.fetchMatchData();
    },
    methods: {
        fetchMatchData() {
            const matchId = this.$route.params.matchId;
            console.log('matchId:', matchId);
            MatchService.getMatchById(matchId)
                .then((response) => {
                    if (response.status === 200) {
                        console.log('Match data:', response.data);
                        this.selectedMatch = response.data;
                    } else {
                        console.error('Error fetching match data:', response.status);
                    }
                })
                .catch((error) => {
                    console.error('Error fetching match data:', error);
                });
        }
    }
}
</script>