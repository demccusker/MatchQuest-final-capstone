<template>
<h1>Player Profile</h1>
    <p>Player Display Name: {{ $store.state.user.username }}</p>
    <p>Player ID: {{ $store.state.user.id }}</p>
    <div>
        <a href="/users/:userId/edit">
            <button>Edit</button>
        </a>
    </div>
    
</template>

<script>
export default {
    name: 'DisplayUserDetails',
    data() {
        return {
            user: this.$store.state.user
        }
    }, 

}
</script>

<style>
button {
    width: 100px;
}

</style>