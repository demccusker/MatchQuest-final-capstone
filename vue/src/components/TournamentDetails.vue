<template>
    <div class="tournament-details">
      <div class="tournament-header">
        <h2>{{ tournament.name }}</h2>
        <button @click="showConfirmModal = true">Register</button>
      </div>
  
      <!-- Confirmation Modal -->
      <div v-if="showConfirmModal" class="modal-overlay">
        <div class="modal">
          <p>Are you sure you want to register for this tournament?</p>
          <button @click="confirmRegistration">Yes</button>
          <button @click="showConfirmModal = false">No</button>
        </div>
      </div>
  
      <!-- Success Modal -->
      <div v-if="showSuccessModal" class="modal-overlay">
        <div class="modal">
          <p>You have successfully registered for the tournament!</p>
          <button @click="showSuccessModal = false">OK</button>
        </div>
      </div>
  
      <p>Start Date: {{ tournament.startDate }}</p>
      <p>End Date: {{ tournament.endDate }}</p>
     

      <h3>Match "Bracket"</h3>
    </div>
  </template>

<script>
export default {
    props: {
        tournament: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            showConfirmModal: false,
            showSuccessModal: false
        };
    },
    methods: {
        confirmRegistration() {
            this.showConfirmModal = false;

            this.showSuccessModal = true;
        }
    }
};
</script>

<style scoped>
.tournament-details {
  border: 1px solid #ddd;
  padding: 1rem;
  margin: 1rem;
  background-color: #f9f9f9;
}

.tournament-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1rem;
}

.tournament-header h2 {
  margin: 0;
}

button {
  margin: 0.5rem;
  padding: 0.5rem 1rem;
  border: none;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  border-radius: 5px;
}

button:hover {
  background-color: #0056b3;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  background-color: white;
  padding: 1rem 2rem;
  border-radius: 5px;
  text-align: center;
}
</style>