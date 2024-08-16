import axios from 'axios';

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API
});

export default {
  getAddress(tournamentId, authToken) {
    let authHeader = { headers: { Authorization: `Bearer ${authToken}` } };
    return http.get(`/address/${tournamentId}`, authHeader);
  },

  addAddress(address, authToken) {
    let authHeader = { headers: { Authorization: `Bearer ${authToken}` } };
    return http.post('/address', address, authHeader);
  },

  updateAddress(address, authToken) {
    let authHeader = { headers: { Authorization: `Bearer ${authToken}` } };
    return http.put('/address', address, authHeader);
  },

  deleteAddress(tournamentId, authToken) {
    let authHeader = { headers: { Authorization: `Bearer ${authToken}` } };
    return http.delete(`/address/${tournamentId}`, authHeader);
  }
  
};
