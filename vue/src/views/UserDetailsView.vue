<template>
    <div class="user-details">
    <user-details/>
</div>
</template>

<script>
import UserDetails from "../components/UserDetails.vue";

export default{
    components:{
        UserDetails
    }
}
</script>


<style>
#user-details
{
    display: flex;
    justify-content: center;
    align-items: center;
}

</style>