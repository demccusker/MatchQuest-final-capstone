<template>
   <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      <router-link v-bind:to="{ name: 'login' }" v-else>Login</router-link> &nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'register' }" v-if="$store.state.token == ''">Register</router-link> &nbsp;|&nbsp;
      <!-- <router-link v-bind:to="{ name: 'userDetails'}">User Details</router-link> -->
    </div>
  <div class="home">
    {{ this.$store.state }}
    
    <div class="container">
      
      <img src="../assets/pickleball_pic.jpg" alt="" id="pickleball_pic">
      <div class = "bottom_right">
        <h1 class = "title">Tourney</h1>
      </div>
    </div>
    
  </div>
</template>

<script>
export default {
};
</script>


<style>

#nav{
  text-align: right;
  font-family: Verdana;
 padding: 1rem; 
}
#nav a {
  background-color: #6e8fe2;
  text-decoration: none; 
  color: black;
  border: 1px solid light grey; 
  border-radius: 4px; 
  padding: 0.5rem 1rem; 
  transition: background-color 0.3s ease, color 0.3s ease; 
}
#nav a:hover {
  background-color: white; 
  color: blue; 
}

body{
  background-color: #6ba6e6e4;
  font-family: sans-serif;
  color: #070c4df4
}


.container {
  text-align: center;
  position: relative;

}
.bottom_right{
  position: absolute;
  bottom: -20px;
  right: 10px;
  
}
.title{
  font-size: xxx-large;
  color: white;
}

#pickleball_pic {
  width: 100%;
  height: auto;
}
</style>
