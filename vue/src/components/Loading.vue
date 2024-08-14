<template>
    <div v-if="isLoading" class="loading-overlay">
      <img src="@/assets/breakdance-raygun.gif" alt="Loading" class="loading-gif"/>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      isLoading: {
        type: Boolean,
        required: true
      }
    }
  }
  </script>
  
  <style scoped>
  .loading-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
  }
  
  .loading-gif {
  width: 850px; 
  height: auto; 
}
  </style>
  