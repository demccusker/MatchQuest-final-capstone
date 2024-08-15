import axios from 'axios';

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API
});

export default {
  getBracketsByTournamentId(tournamentId, authToken) {
    let authHeader = { headers: { Authorization: `Bearer ${authToken}` } };
    return http.get(`tournaments/${tournamentId}/brackets`, authHeader);
  },

  getBracketTree(bracketId) {
    return http.get(`/brackets/${bracketId}/tree`);
  },

  createBracketTree(tournamentId) {
    return http.post(`tournaments/${tournamentId}/brackets`);
  },

  updateBrackets(brackets, authToken) {
    let authHeader = { headers: { Authorization: `Bearer ${authToken}` } };
    return http.put('/bracket', brackets, authHeader);
  }
};
