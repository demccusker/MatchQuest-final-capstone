<template>
  <div id="nav">
    <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
    <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    <router-link v-bind:to="{ name: 'login' }" v-else>Login</router-link>&nbsp;|&nbsp;
    <router-link v-bind:to="{ name: 'register' }" v-if="$store.state.token == ''">Register</router-link>&nbsp;|&nbsp;
    <router-link v-bind:to="{ name: 'tournament'}" >Tournament</router-link>
  </div>

  <div class="home">
    <div class="banner">
      <h1>Welcome to Tourney!</h1>
      <p>Your go-to platform for hosting and managing tournaments with ease.</p>
      <router-link to="/register" class="cta-button">Get Started</router-link>
    </div>
    <!-- <div class="tournament-section">
      <h2>Upcoming Tournaments</h2>
      <TournamentList />
    </div> -->

    <div class="content">
      <div class="about-section box">
        <h2>About Tourney</h2>
        <p>
          Tourney helps you easily create, manage, and track tournaments across various sports and games. Whether you're organizing a small local event or a large-scale competition, Tourney simplifies the process.
        </p>
      </div>

      <div class="container">
        <img src="../assets/pickleball_pic.jpg" alt="" id="pickleball_pic">
        <div class="bottom_right">
          <h1 class="title">Tourney</h1>
        </div>
      </div>
    </div>
   
    <div class="features-section">
      <div class="feature">
        <img src="../assets/easy_to_use.jpg" alt="Easy Setup" id="easy_logo">
        <h3>Easy Setup</h3>
        <p>Create tournaments in minutes with our intuitive interface.</p>
      </div>
      <div class="feature">
        <img src="../assets/match_logo.jpg" alt="Manage Matches" id="match_logo">
        <h3>Manage Matches</h3>
        <p>Track and manage matches with real-time updates.</p>
      </div>
      
      <div class="feature">
        <img src="../assets/community_logo.jpg" alt="Community" id="community_logo">
        <h3>Join the Community</h3>
        <p>Connect with players and organizers in your area.</p>
      </div>
      
    </div>
  </div>
</template>



<style>

.feature {
  flex: 1;
  text-align: center;
  padding: 1.5rem;
  border-radius: 8px;
  margin: 0 1rem;
  background-color: #f9f9f9;
  border: 2px solid #34495e;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.feature:hover {
  transform: scale(1.1); 
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2); 
}

.feature img {
  width: 80px;
  height: auto;
  margin-bottom: 1rem;
}


#match_logo, #easy_logo, #community_logo {
  width: 50%;
  height: auto;
  margin-bottom: .5rem;
}
.features-section {
  display: flex;
  justify-content: space-between;
  margin-top: 2rem;
}


.feature {
  flex: 1;
  text-align: center;
  padding: 1.5rem;
  border-radius: 8px;
  margin: 0 1rem;
  background-color: #f9f9f9;
  border: 2px solid #34495e;
}

.feature img {
  width: 80px;
  height: auto;
  margin-bottom: 1rem;
}

.feature h3 {
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
}

.feature p {
  font-size: 1rem;
  color: #555;
}

#match_logo, #easy_logo, #community_logo {
  width: 50%;
  height: auto;
  margin-bottom: .5rem;
}


.content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.about-section {
  flex: 1;
  margin-right: 2rem;
  text-align: left;
}


.box {
  border: 2px solid #34495e;
  padding: 1.5rem;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.container {
  flex: 1;
  position: relative;
  text-align: center;
}

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
#match_logo {
  width: 10%;
  height: auto;
}
#easy_logo {
  width: 10%;
  height: auto;
}
#community_logo {
  width: 10%;
  height: auto;
}
#nav {
  text-align: right;
  font-family: Verdana;
  padding: 1rem;
}

#nav a {
  background-color: #6e8fe2;
  text-decoration: none;
  color: black;
  border: 1px solid lightgrey;
  border-radius: 4px;
  padding: 0.5rem 1rem;
  transition: background-color 0.3s ease, color 0.3s ease;
}

#nav a:hover {
  background-color: white;
  color: blue;
}

body {
  background-color: #6ba6e6e4;
  font-family: sans-serif;
  color: #070c4df4;
}

.home {
  padding: 2rem;
}

.banner {
  text-align: center;
  background: linear-gradient(to right, #6e8fe2, #34495e);
  color: white;
  padding: 2rem 1rem;
  margin-bottom: 2rem;
}

.banner h1 {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.banner p {
  font-size: 1.25rem;
  margin-bottom: 2rem;
}

.cta-button {
  background-color: white;
  color: #6e8fe2;
  padding: 0.75rem 2rem;
  border-radius: 4px;
  text-decoration: none;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.cta-button:hover {
  background-color: #6e8fe2;
  color: white;
}

/* New styles added */
.content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.about-section {
  flex: 1;
  margin-right: 2rem;
  text-align: left;
}

/* Added box styling */
.box {
  border: 2px solid #34495e;
  padding: 1.5rem;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.about-section h2 {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.container {
  flex: 1;
  position: relative;
  text-align: center;
}

#pickleball_pic {
  width: 100%;
  height: auto;
}

.bottom_right {
  position: absolute;
  bottom: -20px;
  right: 10px;
}

.title {
  font-size: xxx-large;
  color: white;
}

.footer {
  text-align: center;
  padding: 1rem;
  background-color: #6e8fe2;
  color: white;
}

</style>

