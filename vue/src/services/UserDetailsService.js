import axios from 'axios';

const http = axios.create({
    baseURL: import.meta.env.VITE_REMOTE_API
});

export default{
    getUserDetails(userId){
        return http.get(`/users/${userId}/details`);
    },
    createUserDetails(userId, details){
        return http.post(`/users/${userId}`, details);
    },
    updateUserDetails(userId){
        return http.put(`/users/${userId}/details`);
    }
}
