<template>
    <div class="dashboard-container">
        <nav-bar></nav-bar>
        <main class="dashboard-content">
            <h5>Match Details</h5>
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

<style scoped>
div.container {
    
    display: flex;
    flex-direction: column;
    justify-content: left;
    align-items: top;
    
}
#returnButton {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 20px;
}

h5 {
    font-size: 40px;
    margin-top: 10px;
}
</style>