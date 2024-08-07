<template>
    <h2>
        Tournament Name: <br>
        {{ tournament.name }}
    </h2>
    <!-- return game name, not game id <h3>
        {{ tournament.game_id }}
    </h3> -->
    <p>
        {{ tournament.isScrim }} 
        <!-- make this into an icon -->
        {{ tournament.location }}
        Start Date: {{ tournament.startDate }}
        End Date: {{ tournament.endDate }}
        {{ tournament.description }}
    </p>
    
</template>

<script>
export default {
    props : {
        tournament: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            gameName: ''
        }
    }
}


</script>

<style>
</style>