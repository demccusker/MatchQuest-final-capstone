<template>
  <div id="capstone-app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      <router-link v-bind:to="{ name: 'login' }" v-else>Login</router-link>
    </div>
    <router-view />
  </div>
</template>


<style>
#nav{
  text-align: right;
  font-family: Verdana
}

body{
  background-color: #007bff48;
  font-family: sans-serif;
  color: #0011fff4
}


</style>