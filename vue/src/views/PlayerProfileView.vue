<template>
  <div class="profile-view-container">
    <aside class="sidebar">
      <div class="nav-top">
        <router-link v-bind:to="{ name: 'home' }" class="nav-link">Home</router-link>
        <router-link to="/player/dashboard" class="nav-link">Player Dashboard</router-link>
      </div>
      <div class="nav-bottom">
        <router-link to="/player/profile" class="nav-link">Profile</router-link>
        <router-link to="/login" class="nav-link">Logout</router-link>
      </div>
    </aside>
    <div class="profile-view">
      <div class="profile-header">
      
      
      </div>
      <display-user-details />
    </div>
  </div>
</template>

<script>
import DisplayUserDetails from '../components/DisplayUserDetails.vue';

export default {
  components: {
    DisplayUserDetails
  }
}
</script>

<style scoped>
.profile-view-container {
  display: flex;
  height: 100vh;
  font-family: 'Roboto', sans-serif;
}

.sidebar {
  width: 260px; 
  background: linear-gradient(135deg, #2c3e50, #34495e);
  height: 100vh;
  color: white;
  padding: 30px; 
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.2);
}

.nav-link {
  color: white;
  text-decoration: none;
  padding: 15px 20px; 
  display: block;
  border-radius: 8px;
  transition: all 0.3s ease;
  font-size: 1.4rem;
 }




.profile-view {
  flex: 1;
  padding: 40px;
  background-color: #ecf0f1;
  display: flex;
  flex-direction: column;
  align-items: center; 
  justify-content: center; 
  overflow-y: auto;
}

.profile-header {
  width: 100%;
  max-width: 1200px; 
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px; 
}

.edit-button {
  padding: 15px 30px;
  font-size: 2rem; 
  border: none;
  background-color: #007bff;
  color: white;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.edit-button:hover {
  background-color: #0056b3;
}


</style>