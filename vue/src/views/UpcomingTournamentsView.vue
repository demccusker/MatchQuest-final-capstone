<template>
    <h1>Upcoming Tournaments</h1>
    <div class="upcoming-tournaments-container">
        <tournament-list v-bind:filters="upcomingFilter" />
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
            upcomingFilter: [
                {
                    filterProperty: "startDate",
                    value: new Date(),
                    condition: ">"
                },
                {
                    filterProperty: "endDate",
                    value: null,
                    condition: "IS",
                    concat:
                        [
                            {
                                filterProperty: "endDate",
                                value: new Date(),
                                condition: ">",
                                operator: "|"
                            }
                        ]
                }
            ]
        };
    }
};
</script>

<style>
.upcoming-tournaments-container {
    display: flex;
    flex-wrap: wrap;
}

.tournament-list {
    display: flex;
    flex-wrap: wrap;
}
</style>