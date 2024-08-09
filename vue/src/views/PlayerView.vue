<template>
    <div class="dashboard-container">
      <aside class="sidebar">
        <div class="nav-top">
            <router-link v-bind:to="{ name: 'home' }" class="nav-link">Home</router-link>
          <router-link to="/player/past-tournaments" class="nav-link">Past Tournaments</router-link>
         
        </div>
        <div class="nav-bottom">
            <router-link to="/player/profile" class="nav-link">Profile</router-link>
          <router-link to="/login" class="nav-link">Logout</router-link>
        </div>
      </aside>
      <main class="dashboard-content">
        <h1>Player Dashboard</h1>
        <router-view></router-view>
      </main>
    </div>
  </template>
  
  <script>
  export default {
    
  
  }
  </script>

  <style scoped>
  .dashboard-container {
    display: flex;
    height: 100vh;
  }
  
  .sidebar {
    width: 200px;
    background-color: #2c3e50;
    color: white;
    padding: 20px;
    display: flex;
    flex-direction: column;
    justify-content: space-between; 
  
  }
  .nav-top {
    display: flex;
    flex-direction: column;
  }
  .nav-bottom {
    display: flex;
    flex-direction: column;
  }
  
  .nav-link {
    color: white;
    text-decoration: none;
    display: block;
    padding: 10px 0;
    margin-bottom: 10px;
    justify-content: center;
    align-items: center; /* Centers content vertically */
    padding: 10px;
    margin-bottom: 10px;
    transition: background-color 0.3s ease;
    border: 1px solid #34495e;
    border-radius: 4px;
    height: 20px;
  }
  
  .nav-link:hover {
    background-color: #34495e;
  }
  </style>
