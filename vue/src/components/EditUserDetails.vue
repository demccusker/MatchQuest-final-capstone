<template>
    <form class="form" v-on:submit.prevent="updateUserDetails">
        <h1> Edit Profile </h1>
        <div class="form-input-group">
            <h2>Display Name</h2>
            <input type="text" id="displayName" v-model="editUserDetails.displayName" required autofocus />
        </div>
        
        <div class="form-input-group">
            <h2>Elo Rating</h2>
            <input type="number" id="eloRating" v-model="editUserDetails.eloRating" />
        </div>

        <div class="form-input-group">
            <label for="isStaff">Are you an organizer? </label>
            <input type="checkbox" id="isStaff" v-model="editUserDetails.isStaff" />
        </div>
        <button type="submit">Submit</button>
    </form>
</template>
<script>
import UserDetailsService from '../services/UserDetailsService';
export default {
    props: {
        userDetails: {
            type: Object,
            required: true
        },
        userId: {
            required: true
        }
    },
    data() {
        return {
            editUserDetails: {
                displayName: '',
                eloRating: 0,
                isStaff: false,
            },
            registrationErrors: false,
            registrationErrorMsg: 'There were problems editing this user.',
        };
    },
    created() {
        console.log(this.userDetails);
    this.editUserDetails = this.userDetails;
    },
    
    // mounted() {
    //     this.fetchUserDetails();
    // },

    methods:{
        fetchUserDetails() {
            const userId = this.$route.params.id;
            console.log(userId);
            UserDetailsService.getUserDetails(userId)
                .then((response) => {
                    if (response.status === 200){
                        this.editUserDetails = response.data; 
                    } else {
                        console.error('Error fetching user data:', response.status);
                    }
                })
                .catch((error) => {
                    console.error('Error fetching user data:', error);
                    
                });
        },
        updateUserDetails(){
    
            const authToken = this.$store.state.token;
            UserDetailsService.updateUserDetails(this.$store.state.user.id, this.userDetails,authToken).then((response)=>{
                if(response.status == 201){
                    this.$router.push("/tournaments");
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
#form {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>