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
import BracketService from '../services/BracketService';
import MatchService from '../services/MatchService';
import TournamentService from '../services/TournamentService';

export default {
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
            players: [],
            ancestorCount: null,
        }
    },
    mounted() {
        this.fetchMatchData();
    },
    methods: {
        editTheMatch() {
            this.editMatchDto = this.matchToMatchDto(this.editMatch);
            const matchId = this.$route.params.matchId;
            const tournamentId = this.$route.params.tournamentId;

            console.log(this.editMatchDto);
            MatchService.updateMatch(matchId, this.editMatchDto, this.$store.state.token)
                .then((response) => {
                    if (response.status === 200) {
                        if (this.editMatchDto.winnerId === 0) {
                            this.$router.push({ name: "tournamentDetails", params: { tournamentId: this.$route.params.tournamentId } });
                        } else if (this.ancestorCount === 0) {
                            this.concludeTournament();
                        } else {
                            this.passWinner();
                        }
                    } else {
                        console.error('Error editing match:', response.status);
                    }
                })
                .catch((error) => {
                    console.error('Error editing match:', error);
                });
        },
        concludeTournament() {
            TournamentService.concludeTournament(this.$route.params.tournamentId, this.$store.state.token).then(response => {
                this.$router.push({ name: "tournamentDetails", params: { tournamentId: this.$route.params.tournamentId } });
            })
        },
        fetchAncestorCount(matchId) {
            BracketService.getAncestors(matchId).then(response => {
                if (response.status === 200) {
                    this.ancestorCount = response.data.length;
                }
            }).catch(error => {
                console.log(error);
            })
        },
        fetchMatchData() {
            const matchId = this.$route.params.matchId;
            MatchService.getMatchById(matchId)
                .then((response) => {
                    if (response.status === 200) {
                        this.editMatch = response.data;
                        this.fetchAncestorCount(this.editMatch.matchId);
                        this.fillPlayers();

                    } else {
                        console.error('Error fetching match data:', response.status);
                    }
                })
                .catch((error) => {
                    console.error('Error fetching match data:', error);
                });
        },
        bubbleUpWinner(parentMatch = null) {
            if (parentMatch == "") return;

            if (parentMatch.player1Id === 0) {
                parentMatch.player1Id = this.editMatchDto.winnerId;
            } else if (parentMatch.player2Id === 0) {
                parentMatch.player2Id = this.editMatchDto.winnerId;
            }
            
            MatchService.updateMatch(parentMatch.matchId, parentMatch, this.$store.state.token).then(request => {
                this.$router.push({ name: "tournamentDetails", params: { tournamentId: this.$route.params.tournamentId } });
            })
        },
        passWinner() {
            MatchService.getParentMatch(this.editMatchDto.matchId).then(response => {
                if (response.status == 200) {
                    this.bubbleUpWinner(response.data);
                }
            }).catch(error => {
                console.log(error);
            });
        },
        matchToMatchDto(match) {
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