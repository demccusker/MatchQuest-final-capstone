<template>
    <ul>
        <bracket-element v-bind:bracket="bracket"></bracket-element>
    </ul>
</template>

<script>
import BracketElement from './BracketElement.vue';

export default {
    components: {
        BracketElement
    },
    props: {
        bracket: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
        }
    }
}
</script>