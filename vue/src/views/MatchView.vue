<template>
    <div class="dashboard-container">
        <nav-bar></nav-bar>
        <main class="dashboard-content">
            <h1>Match Details</h1>
            <match-details></match-details>
    
        </main>
    </div>
</template>

<script>
import NavBar from '../components/NavBar.vue';
import MatchService from '../services/MatchService';
import MatchDetails from '../components/MatchDetails.vue';

export default {
    data () {
        return {
            
        }
    },
    components: {
        NavBar,
        MatchDetails
    },
    created() {
        
        
    }

}
</script>