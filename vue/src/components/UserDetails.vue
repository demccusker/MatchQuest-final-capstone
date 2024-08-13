<template>
    <div class="flex-container">
        <form v-on:submit.prevent="createUserDetails" class="form-container">
            <h1>Create Profile</h1>
            <div class="form-input-group">
                <h2>Display Name</h2>
                <input type="text" id="displayName" v-model="userDetails.displayName" required autofocus />
            </div>
            
            <div class="form-input-group">
                <h2>Elo Rating</h2>
                <input type="number" id="eloRating" v-model="userDetails.eloRating" />
            </div>

            <div class="form-input-group">
                <label for="isStaff">Are you an organizer?</label>
                <input type="checkbox" id="isStaff" v-model="userDetails.isStaff" />
            </div>
            <button type="submit">Submit</button>
        </form>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import UserDetailsService from '../services/UserDetailsService';
export default {

    
    data() {
        return {
            userDetails: {
                displayName: '',
                isStaff: false,
                eloRating: 1000,
            },
            registrationErrors: false,
            registrationErrorMsg: 'There were problems registering this user.',
        };
    },
    methods:{
        createUserDetails(){
            console.log("ID: " + this.$store.state.user.id);
            const authToken = this.$store.state.token;
            UserDetailsService.createUserDetails(this.$store.state.user.id, this.userDetails,authToken).then((response)=>{
                if(response.status == 201){
                    if(this.userDetails.isStaff==true){
                        this.$store.commit("UPDATE_CURRENT_ROLE","organizer")
                        this.$router.push("/organizer/dashboard")
                    }else{
                        this.$store.commit("UPDATE_CURRENT_ROLE","player")
                        this.$router.push("/player/dashboard");
                    }
                    
                }
            }).catch((error)=>{
                const response = error.response;
                if(response === 400){
                    this.registrationErrorMsg = "An error occurred during register user-details"
                }
            });
        },
    }

};
</script>

<style>
.flex-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    margin: 0;
}

.form-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
    background-color: #f7f7f7;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 400px;
}

.form-input-group {
    width: 100%;
    margin-bottom: 15px;
}

h1 {
    margin-bottom: 20px;
    font-size: 1.5rem;
}

h2 {
    margin-bottom: 5px;
    font-size: 1.1rem;
}

input[type="text"],
input[type="number"],
input[type="checkbox"] {
    width: 95%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 1rem;
}

button {
    width: 100%;
    padding: 10px;
    background-color: #007BFF;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 1.1rem;
}

button:hover {
    background-color: #0056b3;
}

label {
    font-size: 1rem;
    margin-bottom: 10px;
}
</style>