<template>
    <div class="container">
        <h3>
            Match Players: {{ player1UserDetails.displayName }} vs. 
    {{ player2UserDetails.displayName }}
        </h3>
        <h4>
            Tournament: {{ tournament.name }}
            
            Location: {{ tournament.online ? "Online" : tournament.location }}
            Date: {{ tournament.startDate }}
            <!-- Time: add start time later  -->

        </h4>
        <p>
            Final Scores: {{ match.player1Score }} - {{ match.player2Score }}
            Match Winner: {{ match.winnerId == match.player1Id ? player1UserDetails.displayName : player2UserDetails.displayName }}
            Tournament id: {{ tournament.tournamentId }}
        </p>
    </div>
    <!-- <div class="bracket">
        <tournament-brackets v-bind:tournamentId="tournament.tournamentId"
        v-bind:tournamentDate="tournament.startDate"></tournament-brackets>

    </div> -->

</template>

<script>
import MatchService from '../services/MatchService';
import TournamentService from '../services/TournamentService';
import UserDetailsService from '../services/UserDetailsService';
import TournamentBrackets from '../components/TournamentBrackets.vue';
export default {
    components : {
        TournamentBrackets
    },
    data() {
        return {
            match : {

            },
            tournament: {

            },
            player1UserDetails: {

            },
            player2UserDetails: {

            }
        
        }
    },
    created() {
        this.getMatch();
        this.getTournament();
        
        
    },
    methods: {
        getMatch() {
            MatchService.getMatchById(this.$route.params.matchId)
                .then(response => {
                    if (response.status === 200) {
                        this.match = response.data;
                    }
                })
                .catch(error => {
                    console.error('Error fetching match details:', error);
                });
        },
        getTournament() {
            TournamentService.getTournament(this.$route.params.tournamentId)
                .then(response => {
                    if (response.status === 200) {
                        this.tournament = response.data;
                    }
                })
                .catch(error => {
                    console.error('Error fetching tournament details:', error);
                });
        },
        getUserDetailsPlayer1() {
            UserDetailsService.getUserDetails(this.match.player1Id)
                .then(response => {
                    if (response.status === 200) {
                        this.player1UserDetails = response.data;
                    }
                })
                .catch(error => {
                    console.error('Error fetching user details:', error);
                });
        },
        getUserDetailsPlayer2() {
            UserDetailsService.getUserDetails(this.match.player2Id)
                .then(response => {
                    if (response.status === 200) {
                        this.player2UserDetails = response.data;
                    }
                })
                .catch(error => {
                    console.error('Error fetching user details:', error);
                });
        }
    },
    watch: {
        'match.player1Id': function(newPlayer1Id) {
            if (newPlayer1Id) { 
                this.getUserDetailsPlayer1();
            }
        },
        'match.player2Id': function(newPlayer2Id) {
            if (newPlayer2Id) { 
                this.getUserDetailsPlayer2();
            }
        }
    },
}
</script>

<style>
#container {
    display: flex;
    flex-direction: column;
    align-items: center;
}
</style>