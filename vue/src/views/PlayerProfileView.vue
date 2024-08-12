<template>
    <div class="profile-view-container">
        <div class="nav-bar">
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
        </div>
        <div class="profile-view">
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

<style>

.nav-bar {
    height: 100vh;
}
.profile-view-container {
  display: flex;
  height: 100vh;
  font-family: 'Roboto', sans-serif;
}

.sidebar {
  width: 220px;
  background: linear-gradient(135deg, #2c3e50, #34495e);
  height: 93vh;
  color: white;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.nav-top, .nav-bottom {
  display: flex;
  flex-direction: column;
}

.nav-link {
  color: white;
  text-decoration: none;
  padding: 10px;
  margin-bottom: 15px;
  display: flex;
  align-items: center;
  transition: all 0.3s ease;
  border-radius: 8px;
}

.nav-link i {
  margin-right: 10px;
  font-size: 1.2rem;
}

.nav-link:hover {
  background-color: rgba(255, 255, 255, 0.1);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.profile-view {
  flex: 1;
  padding: 40px;
  background-color: #ecf0f1;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
}

@media (max-width: 768px) {
  .dashboard-container {
    flex-direction: column;
  }

  .sidebar {
    width: 100%;
    flex-direction: row;
    justify-content: space-between;
  }

  .dashboard-content {
    padding: 20px;
  }

  .dashboard-title {
    font-size: 1.5rem;
  }
}
</style>