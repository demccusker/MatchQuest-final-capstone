import axios from 'axios';

const http = axios.create({
    baseURL: import.meta.env.VITE_REMOTE_API
});

export default{
    getUserDetails(userId){
        return http.get(`/users/${userId}/details`);
    },
    createUserDetails(userId, details,authToken){
        let authHeader = {headers:{"Authorization": `Bearer ${authToken}`}};
        return http.post(`/users/${userId}`, details,authHeader);
    },
    updateUserDetails(userId,details, authToken){
        let authHeader = {headers:{"Authorization": `Bearer ${authToken}`}};
        return http.put(`/users/${userId}/details`,details, authHeader);
    }
}
