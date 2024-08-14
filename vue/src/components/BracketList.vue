<template>
    <div class="tournament-bracket-round" :class="{ 
            'tournament-bracket-round--quarterfinals': isBracketClass('Quarter-Finals'),
            'tournament-bracket-round--semifinals': isBracketClass('Semi-Finals'),
            'tournament-bracket-round--finals': isBracketClass('Finals')
        }">

        <h3 class="tournament-bracket-round-title">{{ this.bracketTitle }}</h3>
        <ul class="tournament-bracket-list">
            <bracket-element v-for="bracket in brackets" v-bind:bracket="bracket" v-bind:startDate="tournamentDate"></bracket-element>
        </ul>
    </div>
</template>

<script>
import BracketElement from './BracketElement.vue';

export default {
    components: {
        BracketElement
    },
    props: {
        brackets: {
            type: Array,
            required: true
        },
        treeFloor: {
            type: Number,
            required: true
        },
        treeFloorTotal: {
            type: Number,
            required: true
        },
        tournamentDate: {
            type: String,
            required: true
        }
    },
    computed: {
        bracketTitle() {
            switch (this.brackets.length) {
                case 1:
                    return "Finals";
                case 2:
                    return "Semi-Finals";
                case 3:
                    return "Quarter-Finals";
                case 4:
                    return "Quarter-Finals";
                default:
                    return "Qualifiers";
            }
        }
    },
    methods: {
        isBracketClass(bracketTier) {
            return this.bracketTitle === bracketTier;
        }
    },
    data() {
        return {
        }
    }
}
</script>