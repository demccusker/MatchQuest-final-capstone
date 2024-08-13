<template>
    <div class="tournament-details-container">
      <h1>{{ tournament.name }}</h1>
      <p><strong>Date:</strong> {{ tournament.date }}</p>
      <p><strong>Description:</strong> {{ tournament.description }}</p>
      <!-- Add more details as needed -->
      <router-link :to="{ name: 'home' }" class="nav-link">Back to Home</router-link>
    </div>
  </template>

<script>
import TournamentService from '../services/TournamentService';

export default {
    props: {
        tournament: {
            type: Object,
            required: true
        }
    },
    data() {
      return {
        tournament: {}
      };
    },
    created() {
      const tournamentId = this.$route.params.tournamentId;
      TournamentService.getTournament(tournamentId)
        .then(response => {
          if (response.status === 200) {
            this.tournament = response.data;
          }
        })
        .catch(error => {
          if (error.response && error.response.status === 404) {
            this.$router.push({ name: 'notFound' });
          } else {
            console.error('Error fetching tournament details:', error);
          }
        });
    }
  };
  </script>
  
  <style scoped>
  .tournament-details-container {
    padding: 20px;
    background-color: rgb(219, 236, 235);
  }
  
  h1 {
    font-size: 40px;
    margin-bottom: 20px;
  }
  
  p {
    margin-bottom: 10px;
  }
  
  .nav-link {
    color: #007bff;
    text-decoration: none;
    font-weight: bold;
  }
  
  .nav-link:hover {
    text-decoration: underline;
  }
  </style>