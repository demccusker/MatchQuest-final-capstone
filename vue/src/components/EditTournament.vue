<template>
    <div id="editTournament">
        <form v-on:submit.prevent="editTournamentMethod">
            <div class="form-input-group">
                <label for="name">Tournament Name</label>
                <input type="text" id="tournamentName" v-model="editTournament.name" required autofocus />
            </div>
            <div class="form-input-group">
                <!-- this is currently hardcoded for the options in our test data; needs to be dynamic -->
                <label for="game_id">Game</label>
                <select id="game_id" v-model="editTournament.gameId" required>
                    <option value=1>Chess</option>
                    <option value=2>Soccer</option>
                    <option value=3>Golf</option>
                    <option value=4>CS2</option>
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
                <input type="number" id="location" v-model="editTournament.location" />
            </div>
            <button type="submit">Edit</button>
        </form>


    </div>
</template>

<script>
import TournamentService from '../services/TournamentService';

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
            editTournament: {} 
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
            console.log("Editing tournament");
            console.log(this.tournament);
            TournamentService.updateTournament(this.tournament.tournamentId, this.editTournament, this.$store.state.token) 
                .then((response) => {
                    if (response.status == 200) {  
                        this.$router.push("/organizer"); 
                    } 
                })
                .catch((error) => {
                    const response = error.response;
                    if (response === 400) {
                        this.registrationErrorMsg = "An error occurred while editing tournament";
                    }
                });
        }
    }
};
</script>
