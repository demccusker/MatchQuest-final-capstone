<template>
    <edit-user-details  v-bind:userDetails="selectedUserDetails" v-bind:userId="userId"/>
</template>

<script>
import EditUserDetails from '../components/EditUserDetails.vue';
import UserDetailsService from '../services/UserDetailsService';

export default{
    components: {
        EditUserDetails
    },
    data() {
        return {
            selectedUserDetails: {

            }, 
            userId: 0
        };
    },
    mounted() {
        
        this.fetchUserDetails();
    },
    methods: {
        fetchUserDetails() {

            this.userId = this.$route.params.userId; 

            UserDetailsService.getUserDetails(this.userId)
                .then((response) => {
                    if (response.status === 200) { 
                        this.selectedUserDetails = response.data; 
                        console.log(this.selectedUserDetails);
                    } else {
                        console.error('Error fetching user data:', response.status);   
                    }
                })
                .catch((error) => {
                    console.error('Error fetching user data:', error);
                });
        }
    }
}
</script>