<template>

    <div>
        <form>
            <h1> Welcome, let's grab some details! </h1>
            <div class="form-input-group">
                <label for="displayName">Come up with a snazzy display name! </label>
                <input type="text" id="displayName" v-model="userDetails.displayName" required autofocus />
            </div>
            <div class="checkbox" />
            <label for="isOrganizer">Are you an organizer? </label>
            <input type="checkbox" id="isOrganizer" v-model="userDetails.isOrganizer" />
            <div class="form-input-group">
                <label for="eloRating">What is your Elo rating? </label>
                <input type="number" id="eloRating" v-model="userDetails.eloRating" />
            </div>
            <button type="submit">Submit</button>
        </form>
    </div>

</template>

<script>
export default {
    // Your component code here
    name: 'RegistrationDetails',

    props: [      
        'user',
       ],


    data() {
        return {
            userDetails: {
                username: '',
                password: '',
                displayName: '',
                isOrganizer: false,
                eloRating: 1000,
        
            },
            registrationErrors: false,
            registrationErrorMsg: 'There were problems registering this user.',
        };
    },

    created() {
        this.userDetails = this.user;
    },

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


</style>