<template>
    <div class="tournament-list-container">
      <Carousel
        ref="carousel"
        :itemsToShow="8"
        :itemsToScroll="1"
        :loop="false"
        :autoplay="false"
        :navigationEnabled="true"
        :breakpoints="breakpoints"
      >
        <Slide v-for="tournament in tournaments" :key="tournament.tournament_id">
          <div v-on:click="sendToTournamentDetailsPage(tournament)" class="tournament-slide">
            <tournament-preview :tournament="tournament" />
    TournamentID: {{ tournament.tournamentId }}        
    Tournament creatorID: {{ tournament.creatorId }}
          </div>
        </Slide>
      </Carousel>
      <!-- Next Button -->
      <button @click="nextSlide" class="next-button" aria-label="Next slide">
        <span>&gt;</span>
      </button>
      <!-- Previous Button (Optional) -->
      <button @click="prevSlide" class="prev-button" aria-label="Previous slide">
        <span>&lt;</span>
      </button>
    </div>
    
  </template>

<script>
import { Carousel, Slide } from 'vue3-carousel';
import 'vue3-carousel/dist/carousel.css';
import TournamentService from '../services/TournamentService';
import TournamentPreview from './TournamentPreview.vue';

export default {
  props: {
    limit: {
      type: Number,
      default: 0
    },
    filters: {
      type: Array,
      default: []
    }
  },
  components: {
    TournamentPreview,
    Carousel,
    Slide
  },
  data() {
    return {
      tournaments: []
    };
  },
  created() {
    const query = {
      filters: this.filters,
      operator: "&",
      limit: this.limit
    };

    TournamentService.getTournamentByFilter(query).then((response) => {
      if (response.status === 200) {
        this.tournaments = response.data;
      }
    }).catch(error => {
      console.log(error);
    });
  },
  methods: {
    sendToTournamentDetailsPage(tournament) {
      console.log(tournament);
      this.$store.commit('SET_TOURNAMENT_ID',tournament.tournamentId);
      this.$store.commit('SET_TOURNAMENT_CREATOR_ID',tournament.creatorId);
      this.$router.push({ name: 'tournamentDetails', params: { tournamentId: tournament.tournamentId } })
      
      
      
    },
    nextSlide() {
      this.$refs.carousel.next();
    },
    prevSlide() {
      this.$refs.carousel.prev();
    }
  },
  computed: {
    breakpoints() {
      return {
        768: {
          itemsToShow: 1,
          itemsToScroll: 1
        },
        1024: {
          itemsToShow: 2,
          itemsToScroll: 2
        },
        1440: {
          itemsToShow: 3,
          itemsToScroll: 1
        }
      };
    }
  }
};
</script>
<style scoped>
.tournament-list-container {
  width: 20%;
  padding: 10px;
  position: relative; /* To position the buttons */
}

.tournament-slide {
  cursor: pointer;
  padding: 0;
  margin: 0;
}

button {
  position: absolute;
  top: 50%; /* Center vertically */
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

.next-button {
  right: 10px; /* Position next button on the right */
}

.prev-button {
  left: 10px; /* Position prev button on the left */
}

button:hover {
  background-color: #5a94d8;
  transform: scale(1.1);
}

.carousel .slide-content {
  text-align: center;
  padding: 5px;
  margin: 0;
  background: #f5f5f5;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}
</style>