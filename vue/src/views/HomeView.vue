<template>
  <div id="nav">
    <router-link v-bind:to="{ name: 'home' }">Home</router-link>
    <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    <router-link v-bind:to="{ name: 'login' }" v-else>Login</router-link>
    <router-link v-bind:to="{ name: 'register' }" v-if="$store.state.token == ''">Register</router-link>
    <router-link v-bind:to="{ name: 'tournament' }">Tournament</router-link>
  </div>

  <div class="home">
    <div class="banner">
      <h1>Welcome to MatchQuest!</h1>
      <p>Your go-to platform for hosting and managing tournaments with ease.</p>
      <router-link to="/register" class="cta-button">Get Started</router-link>
    </div>

    <div>
      <h1 id="upcoming">Upcoming Tournaments</h1>
      <Carousel :tournaments="upcomingTournaments" />
    </div>

    <div class="content">
      <div class="info-image-container">
        <div class="info-section">
          <div class="about-section box">
            <h2>About MatchQuest</h2>
            <p>
              MatchQuest is your all-in-one solution for creating, managing, and tracking tournaments across a wide range of sports and games. We aim to make tournament management accessible and straightforward, so you can focus on what matters most—enjoying the game!
            </p>
          </div>

          <div class="reviews-section">
            <h2>What Our Users Are Saying</h2>
            <div class="review">
              <h3>Kim Thilavanh</h3>
              <p>"I would pay for this App for sure!!!!!!"</p>
            </div>
            <div class="review">
              <h3>Issac Chow</h3>
              <p>"I have a church group and we want to organize a pickleball tournament this app can definitely help us organize a pickle ball tournaments!!!!! "</p>
            </div>
          </div>
        </div>

        <div class="image-container">
          <img src="../assets/pickleball_pic.jpg" alt="Pickleball" id="pickleball_pic">
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

  <footer class="footer">
    <p>&copy; 2024 MatchQuest. All rights reserved. Creators: Andrew Le, Christine Urban, Darcy McCusker, Jenny Rodriguez, Phyo Naung </p>
  </footer>
</template>


<script>
import Carousel from '../components/Carousel.vue';
import TournamentService from '../services/TournamentService';

export default {
  components: { Carousel },
  data() {
    return {
      upcomingTournaments: [],
    };
  },
  created() {
    this.fetchUpcomingTournaments();
  },
  methods: {
    async fetchUpcomingTournaments() {
      try {
        const today = new Date().toISOString().split('T')[0];
        const query = {
          filters: [{ filterProperty: 'startDate', operator: '>', value: today }],
          operator: "&",
          limit: 255,
        };

        const response = await TournamentService.getTournamentByFilter(query);
        if (response.status === 200) {
          this.upcomingTournaments = response.data;
        }
      } catch (error) {
        console.error('Error fetching upcoming tournaments:', error);
      }
    },
  },
};
</script>

<style scoped>
#nav {
  display: flex;
  justify-content: center;

  padding: 1rem;
  font-family: Verdana;
  margin: 0; 
}

#nav a {
  color: black; 
  background-color: white; 
  text-decoration: none;
  border: 1px solid lightgrey;
  border-radius: 4px;
  padding: 0.5rem 2rem; 
  margin: 0 0.5rem;
  width: 500px; 
  text-align: center; 
  display: inline-block; 
  transition: background-color 0.3s ease, color 0.3s ease;
}
#nav a:hover {
  background-color:#6e8fe2 ; 
  color: white;
}


.banner {
  text-align: center;
  background: linear-gradient(to right, #6e8fe2, #34495e);
  color: white;
  padding: 4rem 2rem;
  margin: 0; 
}

.banner h1 {
  font-size: 4rem;
  margin-bottom: 1.5rem;
}

.banner p {
  font-size: 1.5rem;
  margin-bottom: 2.5rem;
}

.cta-button {
  background-color: white;
  color: #6e8fe2;
  padding: 1rem 3rem;
  border-radius: 4px;
  text-decoration: none;
  font-size: 1.25rem;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.cta-button:hover {
  background-color: #6e8fe2;
  color: white;
}

#upcoming {
  font-size: 2rem;
  text-align: center;
  padding: 1rem;
  margin-bottom: 2rem;
  color: black;
  border-radius: 10px;
  width: fit-content;
  margin: 0 auto 2rem auto;
}

.features-section {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  margin: 2rem 0 3rem 0;
}

.feature {
  flex: 1 1 30%;
  margin: 1rem;
  text-align: center;
  padding: 1.5rem;
  border-radius: 8px;
  background-color: #f9f9f9;
  border: 2px solid #34495e;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.feature:hover {
  transform: scale(1.1);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
}

.feature img {
  width: 50%;
  height: auto;
  margin-bottom: 1rem;
}

.feature h3 {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 0.5rem;
}

.feature p {
  font-size: 1.25rem;
  font-weight: bold;
  color: #555;
}

.content {
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
  margin-top: 3rem;
}

.info-image-container {
  display: flex;
  flex: 1;
  align-items: flex-start;
  gap: 2rem;
}

.info-section {
  flex: 2;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.about-section, .reviews-section {
  padding: 1rem;
  background-color: white;
  border: 2px solid #34495e;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 100%;
}

.about-section h2, .reviews-section h2 {
  font-size: 2rem;
  margin-bottom: 0.5rem;
}

.about-section p, .review p {
  font-size: 1rem;
  line-height: 1.6;
}

.reviews-section {
  margin-top: 1rem;
}

.review {
  margin-bottom: 1rem;
}

.review h3 {
  font-size: 1.25rem;
  margin-bottom: 0.5rem;
}

.review p {
  font-size: 1rem;
  line-height: 1.6;
  color: #555;
}

.image-container {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

#pickleball_pic {
  width: 100%;
  height: auto;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.footer {
  text-align: center;
  padding: 2rem 1rem;
  background-color: #6ba6e6e4;
  color: black;
  font-family: Verdana;
  bottom: 0;
  width: 100%;
}
</style>