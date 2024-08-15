<template>
<div class="editMatchForm">
    <form v-on:submit.prevent="editTheMatch">
        <div><h2>Edit Match</h2></div>
        <div class="form-input-group">
            <label for="player1Score">Player 1 Score</label>
            <input type="text" id="player1Score" v-model="editMatch.player1Score" required autofocus />
        </div>
        <div class="form-input-group">
            <label for="player2Score">Player 2 Score</label>
            <input type="text" id="player2Score" v-model="editMatch.player2Score" required />
        </div>
        <div class="form-input">
            <label for="winnerId">Match Winner</label>
            <select id="winnerId" v-model="editMatch.winnerId" required>
                <option value=0>TBD</option>
                <option v-for="player in players" v-bind:value="player" v-bind:key="player">Player {{ players.indexOf(player) + 1 }}</option>
                
            </select>
        </div>
        <div class="form-input-group">
            <label for="isDraw">Draw?</label>
            <input type="checkbox" id="isDraw" v-model="editMatch.isDraw" />
        </div>
        <div class="form-input-group">
            <label for="matchStartTime">Match Time</label>
            <input type="text" id="matchStartTime" v-model="editMatch.matchStartTime" />
        </div>
        <button type="submit">Edit Match</button>

    </form>

</div>
</template>

<script>
import MatchService from '../services/MatchService';

export default{
    // props: {
    //     match: {
    //         type: Object,
    //         required: true
    //     }
    // },
    data() {
        return {
            editMatch: {},
            editMatchDto: {
                
                player1Score: '',
                player2Score: '',
                winnerId: '',
                isDraw: false,
                matchStartTime: '',
                matchId: 0
            },
            players : []
        }
    },
    mounted() {
        this.fetchMatchData();
        // console.log("Player 1 Id: ",this.editMatch.player1Id);
        
        // console.log("Players: ",this.players);
        
    },
    methods : {
        editTheMatch() {
            this.editMatchDto = this.matchToMatchDto(this.editMatch);
            // console.log("matchId before service call",this.editMatchDto.matchId)
            const matchId = this.$route.params.matchId;
            const tournamentId = this.$route.params.tournamentId;
            MatchService.updateMatch(matchId,this.editMatchDto,this.$store.state.token)
                .then((response) => {
                    if (response.status === 200) {
                        this.$router.push({ name: 'matchDetails', params: {tournamentId:tournamentId, matchId: matchId } });
                    } else {
                        console.error('Error editing match:', response.status);
                    }
                })
                .catch((error) => {
                    console.error('Error editing match:', error);
                });
        },
        fetchMatchData() {
            const matchId = this.$route.params.matchId;
            MatchService.getMatchById(matchId)
                .then((response) => {
                    if (response.status === 200) {
                        this.editMatch = response.data;
                        this.fillPlayers();
                        
                    } else {
                        console.error('Error fetching match data:', response.status);
                    }
                })
                .catch((error) => {
                    console.error('Error fetching match data:', error);
                });
        },
        matchToMatchDto(match){
            this.editMatchDto.player1Score = match.player1Score;
            this.editMatchDto.player2Score = match.player2Score;
            this.editMatchDto.winnerId = match.winnerId;
            this.editMatchDto.isDraw = match.isDraw;
            this.editMatchDto.matchStartTime = match.matchStartTime;
            this.editMatchDto.matchId = match.matchId;
            return this.editMatchDto;
        },
        fillPlayers() {
            this.players.push(this.editMatch.player1Id);
            this.players.push(this.editMatch.player2Id);
        }
    },
}
</script>

<style scoped>
form {
    justify-content: center;
    align-items: center;
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