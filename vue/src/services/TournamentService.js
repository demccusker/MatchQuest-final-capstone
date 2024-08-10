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
    createTournament(tournament, authToken) {
        let authHeader = {headers:{"Authorization": `Bearer ${authToken}`}};
        return http.post('/tournaments', tournament,authHeader)
    },
    updateTournament(id, tournament,authToken) {
        let authHeader = {headers:{"Authorization": `Bearer ${authToken}`}};
        return http.put(`/tournaments/${id}/update`, tournament,authHeader)
    },
    getTournamentByCreatorId(id) {
        return http.post('/tournaments/query', {
            filter: [
                {
                    filterProperty: "creatorId",
                    value: id
                }
            ],
            operator: "&",
            limit: 1
        });
    },
    getTournamentByFilter(filter) {
        return http.post('/tournaments/query', filter);
    }
    // deleteTournament(id) {
    //     return http.delete(`/tournaments/${id}`)
    // }
}



