import axios from 'axios';

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API
});
export default {
    getAllMatches() {
      return http.get('/matches');
    },
    getMatchById(matchId) {
      return http.get(`/matches/${matchId}`);
    },
    getMatchesByPlayerId(playerId) {
      return http.get(`/matches/player/${playerId}`);
    },
    getMatchesByTournamentId(tournamentId) {
      return http.get(`/matches/tournaments/${tournamentId}`);
    }
  };