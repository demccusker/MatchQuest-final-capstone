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
    getParentMatch(matchId) {
      return http.get(`/matches/${matchId}/parent`);
    },
    getMatchesByPlayerId(playerId) {
      return http.get(`/matches/player/${playerId}`);
    },
    getMatchesByTournamentId(tournamentId) {
      return http.get(`/matches/tournaments/${tournamentId}`);
    },
    updateMatch(matchId, matchDto, authToken) {
      let authHeader = { headers:{"Authorization": `Bearer ${authToken}`} };
      return http.put(`/matches/${matchId}/update`, matchDto, authHeader);
    },
    createMatches(matches, authToken) {
      let authHeader = { headers:{"Authorization": `Bearer ${authToken}`} };
      return http.post('/matches/array-create', matches, authHeader);
    }
  };