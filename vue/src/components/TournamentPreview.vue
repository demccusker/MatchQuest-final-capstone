<template>
    <h2>
        {{ tournament.name }}
    </h2>
    <!-- return game name, not game id <h3>
        {{ tournament.game_id }}
    </h3> -->
    <p>
        {{ tournament.isScrim }} 
        <!-- make this into an icon -->
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