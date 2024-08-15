<template>
    <div id="editTournament">
        <form v-on:submit.prevent="editTournamentMethod">
            <div class="form-input-group">
                <label for="name">Tournament Name</label>
                <input type="text" id="tournamentName" v-model="editTournament.name" required autofocus />
            </div>
            <div class="form-input-group">
                <label for="gameId">Game</label>
                <select v-if="gamesLoaded" id="gameId" v-model="editTournament.gameId" required>
                    <option v-for="game in games" v-bind:key="game.gameId" v-bind:value="game.gameId" >{{ game.name }}</option>
                </select>
            </div>
            <div class="form-input-group">
                <label for="startDate">Start Date</label>
                <input type="date" id="startDate" v-model="editTournament.startDate" required />
            </div>
            <div class="form-input-group">
                <label for="endDate">End Date</label>
                <input type="date" id="endDate" v-model="editTournament.endDate" required />

            </div>
            <div class="form-input-group">
                <label for="isOnline">Online Tournament?</label>
                <input type="checkbox" id="isOnline" v-model="editTournament.isOnline" />
            </div>
            <div class="form-input-group">
                <label for="isScrim">Scrimmage?</label>
                <input type="checkbox" id="isScrim" v-model="editTournament.isScrim" />
            </div>
            <div class="form-input-group" v-show="!editTournament.isOnline">
                <label for="location">Location</label>
                <input type="text" id="location" v-model="editTournament.location" />
            </div>
            <button type="submit">Edit</button>
        </form>


    </div>
</template>

<script>
import TournamentService from '../services/TournamentService';
import GamesService from '../services/GamesService';

export default {
    name: 'EditTournament',
    props: {
        tournament: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            editTournament: {} ,
            games : [],
            gamesLoaded: false
        }
        
    },
    created() {
        this.games = this.getGamesHere();
    },
    mounted() {
        this.fetchTournamentData();
        this.gamesLoaded = true;

    },
    methods: {
        fetchTournamentData() {
            const tournamentId = this.$route.params.id;
            TournamentService.getTournament(tournamentId) 
                .then((response) => {
                    if (response.status === 200){
                        this.editTournament = response.data; 
                    } else {
                        console.error('Error fetching tournament data:', response.status);
                    }
                })
                .catch((error) => {
                    console.error('Error fetching tournament data:', error);
                    
                });
        },
        editTournamentMethod() {
            // console.log("Editing tournament");
            // console.log(this.tournament);
            TournamentService.updateTournament(this.tournament.tournamentId, this.editTournament, this.$store.state.token) 
                .then((response) => {
                    if (response.status == 200) {  
                        this.$router.push("/organizer/dashboard"); 
                    } 
                })
                .catch((error) => {
                    const response = error.response;
                    if (response === 400) {
                        this.registrationErrorMsg = "An error occurred while editing tournament";
                    }
                });
        },
        getGamesHere() {
            GamesService.getAllGames().then(response => {
                if (response.status == 200) {
                    this.games = response.data;
                    // console.log(this.games);
                }
            }).catch(error => {
                console.log(error);
            });
        }
    }
};
</script>
