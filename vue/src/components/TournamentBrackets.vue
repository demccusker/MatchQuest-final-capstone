<template>
    <bracket-list v-for="(brackets, index) in bracketTree" v-bind:key="index" v-bind:brackets="brackets" v-bind:tournamentDate="tournamentDate"></bracket-list>
</template>

<script>
import BracketService from '../services/BracketService';
import BracketList from './BracketList.vue';

export default {
    props: {
        tournamentId: {
            type: Number,
            required: true
        },
        tournamentDate: {
            type: String,
            required: true
        }
    },
    components: {
        BracketList
    }, 
    data() {
        return {
            bracketTree: []
        }
    },
    mounted() {
        this.getBracket();
    },
    methods: {
        getBracket() {
            BracketService.getBracketsByTournamentId(this.tournamentId).then(response => {
                if (response.status === 200) {
                    const treeHeight = Math.floor(Math.log2(response.data.length) + 1);

                    for (let level = 0, bracketCount = 0; level < treeHeight; level++) {
                        const maxBracketsPerFloor = Math.pow(2, level);
                        let brackets = [];

                        for (let bracketPos = 0; bracketPos < maxBracketsPerFloor; bracketPos++) {
                            if (!(bracketCount < response.data.length)) break;

                            brackets.push(response.data[bracketCount]);
                            bracketCount++; 
                        }

                        this.bracketTree.push(brackets);
                    }

                    console.log(this.bracketTree);
                }
            }).catch(error => {
                console.log(error);
            })
        }
    } 
}
</script>