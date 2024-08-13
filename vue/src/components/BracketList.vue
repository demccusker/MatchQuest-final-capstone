<template>
    <ul>
        <bracket-element v-for="bracket in brackets" v-bind:key="bracket.bracketId" v-bind:bracket="bracket" v-bind:startDate="tournamentDate"></bracket-element>
    </ul>
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
        tournamentDate: {
            type: String,
            required: true
        }
    },
    data() {
        return {
        }
    }
}
</script>