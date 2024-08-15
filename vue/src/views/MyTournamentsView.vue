<template>
    <div class="dashboard-content">
        <div v-bind:class="computeClasses()">

        </div>
    </div>
</template>

<script>

export default{
    data(){
        return {
            currentRole: this.$store.state.currentRole,
            
            
        }
    },
    methods:{
        computeClasses(){
            // if(currentRole==='organizer'){
                
            // }
        }
    }
}
</script>

<style>

</style>