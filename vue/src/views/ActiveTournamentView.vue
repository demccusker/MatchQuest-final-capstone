<template>
    <h1>Active Tournaments</h1>
    <div class="active-tournaments-container">
        <tournament-list v-bind:filters="activeFilter" />
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
            activeFilter: [
                {
                    filterProperty: "startDate",
                    value: new Date(),
                    condition: "<="
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
            ],
        };
    }
};
</script>

<style>
.active-tournaments-container {
    display: flex;
    flex-wrap: wrap;
}

.tournament-list {
    display: flex;
    flex-wrap: wrap;
}
</style>