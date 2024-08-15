<template>
    <div class="dashboard-container">
        <main class="dashboard-content">
            <edit-match-form v-if="selectedMatch" v-bind:match="selectedMatch"></edit-match-form>
        </main>
    </div>

</template>

<script>

import EditMatchForm from '../components/EditMatchForm.vue';
import MatchService from '../services/MatchService';

export default {
    components: {
        
        EditMatchForm
    },
    data() {
        return {
            selectedMatch: null
        }
    },
    created() {
        this.fetchMatchData();
    },
    methods: {
        fetchMatchData() {
            const matchId = this.$route.params.matchId;
            // console.log('matchId:', matchId);
            MatchService.getMatchById(matchId)
                .then((response) => {
                    if (response.status === 200) {
                        // console.log('Match data:', response.data);
                        this.selectedMatch = response.data;
                    } else {
                        console.error('Error fetching match data:', response.status);
                    }
                })
                .catch((error) => {
                    console.error('Error fetching match data:', error);
                });
        }
    }
}
</script>

<style scoped>

/* 
.dashboard-container {
     
    background-color: black;
    display: flex;
    height: 100vh;
} */

.dashboard-content {
    background-color: #6ba6e6e4;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-grow: 1;
    padding: 20px;
}


#form {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    margin-top: 20px;
    
}

edit-match-form {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    margin-top: 20px;
}


</style>