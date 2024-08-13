<template>
  <div class="carousel-container" aria-label="Upcoming Tournaments Carousel">
    <Carousel ref="carousel" :itemsToShow="4" :itemsToScroll="1" :autoplay="false" :loop="true" :navigationEnabled="false" @afterChange="updateCurrentIndex">
      <Slide v-for="(tournament, index) in upcomingTournaments" :key="index">
        <div class="slide-content">
          <h3>{{ tournament.name }}</h3>
          <p>{{ tournament.startDate }} - {{ tournament.endDate }}</p>
          <a :href="`/tournament/${tournament.id}`">View Details</a>
        </div>
      </Slide>
    </Carousel>

    <button @click="prevSlide" aria-label="Previous slide">
      <span>&lt;</span>
    </button>

    <button @click="nextSlide" aria-label="Next slide">
      <span>&gt;</span>
    </button>

    <div class="carousel-indicators">
      <span v-for="(tournament, index) in upcomingTournaments" :key="index" :class="{ active: index === currentIndex }" @click="goToSlide(index)" aria-label="Go to slide {{ index + 1 }}"></span>
    </div>
  </div>
</template>

<script>
import { Carousel, Slide } from 'vue3-carousel';
import 'vue3-carousel/dist/carousel.css';
import TournamentService from '../services/TournamentService'; // Import your service for fetching tournaments

export default {
  components: { Carousel, Slide },
  data() {
    return {
      upcomingTournaments: [],
      currentIndex: 0,
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
          filters: [{ field: 'startDate', operator: '>', value: today }],
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
    prevSlide() {
      this.$refs.carousel.prev();
    },
    nextSlide() {
      this.$refs.carousel.next();
    },
    goToSlide(index) {
      this.$refs.carousel.goTo(index);
      this.updateCurrentIndex(index);
    },
    updateCurrentIndex(index) {
      this.currentIndex = index;
    },
  },
};
</script>

<style scoped>
/* Add your styling here */
.carousel-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  position: relative;
  padding: 20px; /* Add padding inside the container */
  background-color: #ffffff; /* Set the background to white */
  border-radius: 15px; /* Rounded corners */
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1); /* Subtle shadow for depth */
  padding-bottom: 50px;
}

.carousel .slide-content {
  text-align: center;
  padding: 20px;
  background: #f5f5f5;
  border-radius: 10px;
}

.carousel h3 {
  margin: 0 0 10px;
}

.carousel p {
  margin: 0 0 20px;
}

.carousel a {
  text-decoration: none;
  color: #6e8fe2;
}

button {
  position: absolute;
  top: 83%; /* Center vertically */
  transform: translateY(-50%);
  background: #6ba6e6e4;
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  border-radius: 50%;
  transition: background-color 0.3s ease, transform 0.2s ease;
  font-size: 18px;
  z-index: 1;
}

button:hover {
  background: #0056b3;
}

button:active {
  transform: translateY(-50%) scale(0.95);
}

button:nth-of-type(1) {
  left: 20px;
}

button:nth-of-type(2) {
  right: 20px;
}

.carousel-indicators {
  text-align: center;
  padding-top: 10px;
}



.carousel-indicators span.active {
  background-color: #007bff;
}

.carousel-indicators span:hover {
  background-color: #0056b3;
}
</style>