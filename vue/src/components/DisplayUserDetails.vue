<template>
<h1>Player Profile</h1>
    <p>Player Display Name: {{ userDetails.displayName }}</p>
    <p>Player ID: {{ $store.state.user.id }}</p>
    <div>
        <router-link class="nav-link" v-bind:to="`/users/${$store.state.user.id}/edit`">
            <button>Edit</button>
        </router-link>
    </div>
    
</template>

<script>
import UserDetailsService from '../services/UserDetailsService';
export default {
    name: 'DisplayUserDetails',
    data() {
        return {
            user: this.$store.state.user,
            userDetails : {
                displayName: '',
                eloRating: 0,
                isStaff: false,
            },
            
        }
    }, 
    async created() {
        this.userDetails = await this.fetchUserDetails();
    },
    methods:{
        fetchUserDetails() {
            const userId = this.$store.state.user.id;
            UserDetailsService.getUserDetails(userId)
                .then((response) => {
                    if (response.status === 200){
                        this.userDetails = response.data;      
                    } else {
                        console.error('Error fetching user data:', response.status);
                    }
                })
                .catch((error) => {
                    console.error('Error fetching user data:', error);
                    
                });
        },
    }

}
</script>

<style>
button {
    width: 100px;
}

</style>