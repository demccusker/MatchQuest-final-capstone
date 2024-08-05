import axios from 'axios';
const http = axios.create({
    baseURL: import.meta.env.VITE_REMOTE_API
});

export default {
    getTournaments() {
        return http.get('/tournaments')
    },
    getTournament(id) {
        return http.get(`/tournaments/${id}`)
    },
    createTournament(tournament) {
        return http.post('/tournaments', tournament)
    }
    // updateTournament(id, tournament) {
    //     return http.put(`/tournaments/${id}`, tournament)
    // },
    // deleteTournament(id) {
    //     return http.delete(`/tournaments/${id}`)
    // }
}



