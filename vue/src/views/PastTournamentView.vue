<template>
    <h1>Past Tournaments</h1>
    <div class="past-tournaments-container">
        <tournament-list v-bind:filters="pastFilter"/>
    </div>
</template>

<script>
import TournamentList from '../components/TournamentList.vue';

export default {
    components: {
        TournamentList
    },
    data() {
        return {
            pastFilter: [
                {
                    filterProperty: "endDate",
                    value: new Date(),
                    condition: "<"
                },
                {
                    filterProperty: "endDate",
                    value: null,
                    condition: "IS NOT"
                }
            ]
        };
    }
};
</script>

<style>
.past-tournaments-container {
    display: flex;
    flex-wrap: wrap;
}

.tournament-list {
    display: flex;
    flex-wrap: wrap;
}
</style>