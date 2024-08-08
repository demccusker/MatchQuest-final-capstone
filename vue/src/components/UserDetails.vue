<template>
    <form v-on:submit.prevent="createUserDetails">
        <h1> Edit Profile </h1>
        <div class="form-input-group">
            <h2>Display Name</h2>
            <input type="text" id="displayName" v-model="userDetails.displayName" required autofocus />
        </div>
        
        <div class="form-input-group">
            <h2>Elo Rating</h2>
            <input type="number" id="eloRating" v-model="userDetails.eloRating" />
        </div>

        <div class="form-input-group">
            <label for="isStaff">Are you an organizer? </label>
            <input type="checkbox" id="isStaff" v-model="userDetails.isStaff" />
        </div>
        <button type="submit">Submit</button>
    </form>
</template>

<script>
import { mapState } from 'vuex';
import UserDetailsService from '../services/UserDetailsService';
export default {

    // Your component code here
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
                    this.$router.push("/");
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
flex-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
}

.form-input-group {
    margin: 20px;
}

button {
    flex-grow: 1;
}
</style>