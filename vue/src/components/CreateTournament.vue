<template>
    <div id="createTournament">
        <form v-on:submit.prevent="createNewTournament">
            <h1>Create New Tournament</h1>
            <div class="form-input-group">
                <label for="name">Tournament Name</label>
                <input type="text" id="tournamentName" v-model="tournament.name" required autofocus />
            </div>
            <div class="form-input-group">
                <label for="gameId">Game</label>
                <select v-if="gamesLoaded" id="gameId" v-model="tournament.gameId" required>
                    <option v-for="game in games" v-bind:key="game.gameId" v-bind:value="game.gameId">{{ game.name }}
                    </option>
                </select>
            </div>
            <div class="form-input-group">
                <label for="startDate">Start Date</label>
                <input type="date" id="startDate" v-model="tournament.startDate" required />
            </div>
            <div class="form-input-group">
                <label for="endDate">End Date</label>
                <input type="date" id="endDate" v-model="tournament.endDate" />
                <div>
                    <div class="form-input-group">
                        <label for="maxParticipants">Max Participants</label>
                        <input type="number" id="maxParticipants" min="2" max="255"
                            v-model="tournament.maxParticipants">

                    </div>
                </div>
            </div>
            <div class="form-input-group">
                <label for="isOnline">Online Tournament?</label>
                <input type="checkbox" id="isOnline" v-model="tournament.isOnline" />
            </div>
            <div class="form-input-group">
                <label for="isScrim">Scrimmage?</label>
                <input type="checkbox" id="isScrim" v-model="tournament.isScrim" />
            </div>
            <div class="form-input-group" v-show="!tournament.isOnline">
                <h4>Location</h4>
                <label for="streetNumber">Building Number and Street</label>
                <input type="text" id="streetNumber" v-model="tournament.location" />
                <label for="city">City</label>
                <input type="text" id="city" v-model="address.city" />
                <label for="province">Province</label>
                <input type="text" id="province" v-model="address.province" />
                <label for="country">Country</label>
                <input type="text" id="country" v-model="address.country" />
            </div>
            <button type="submit">Create</button>


        </form>
    </div>
</template>

<script>
import AddressService from '../services/AddressService';
import GamesService from '../services/GamesService';
import TournamentService from '../services/TournamentService';

export default {
    name: 'CreateTournament',
    data() {
        return {
            tournament: {
                name: '',
                gameId: null,
                bracketId: null,
                creatorId: this.$store.state.user.id,
                maxParticipants: 0,
                startDate: '',
                endDate: '',
                isOnline: false,
                isScrim: false,
                location: '',
            },
            address: {
                tournamentId: null,
                streetNumber: '',
                city: '',
                province: '',
                country: ''
            },
            games: [],
            selectedGameName: null,
            gamesLoaded: false
        }
    },
    created() {
        this.games = this.getGamesHere();
    },
    mounted() {

        this.gamesLoaded = true;
    },
    methods: {
        createNewTournament() {
            TournamentService.createTournament(this.tournament, this.$store.state.token)
                .then((response) => {
                    if (response.status == 201 && this.tournament.isOnline) {
                        this.$router.push("/tournaments");
                    } else if (response.status == 201) {
                        this.addAddress(response.data);
                    }
                }).catch((error) => {
                    const response = error.response;
                    if (response === 400) {
                        this.registrationErrorMsg = "An error occurred during tournament creation"
                    }
                });
        },
        addAddress(tournament) {
            this.address.tournamentId = tournament.tournamentId;
            AddressService.addAddress(this.address, this.$store.state.token).then(response => {
                this.$router.push("/tournaments");
            }).catch(error => {
                console.log(error);
            })
        },
        getGamesHere() {
            GamesService.getAllGames().then(response => {
                if (response.status == 200) {
                    this.games = response.data;

                }
            }).catch(error => {
                console.log(error);
            });
        }
    }



}
</script>


<style>
#createTournament {
    display: flex;
    justify-content: center;
    align-items: center;
}

form {
    /* justify-content: center;
    align-items: center; */
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 2rem;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    background-color: #fff;
    width: 100%;
    max-width: 400px;
}

h1 {
    margin-bottom: 1rem;
}

.form-input-group {
    margin-bottom: 1rem;
}

label {
    display: block;
    margin-bottom: 0.5rem;
}

input {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #ccc;
    border-radius: 4px;
}

button {
    width: 100%;
    padding: 0.75rem;
    border: 2px solid #007bff;
    border-radius: 4px;
    background-color: #007bff;
    color: white;
    font-size: 1rem;
    cursor: pointer;
    transition: background-color 0.3s ease, border-color 0.3s ease;
}

button:hover {
    background-color: #0056b3;
    border-color: #004080;
}

button:active {
    background-color: #004080;
    border-color: #00264d;
}
</style>
