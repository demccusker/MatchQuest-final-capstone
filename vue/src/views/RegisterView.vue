<template>
  <div id="register" v-if ='!userRegistrationSuccess' class="text-center">
    <form v-on:submit.prevent="register">
      <h1>Create Account</h1>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword">Confirm Password</label>
        <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
      </div>
      <button type="submit">Create Account</button>
      <p><router-link v-bind:to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
    </form>
  </div>

</template>

<script>
import authService from '../services/AuthService';
import RegistrationDetails from '../components/RegistrationDetails.vue';

export default {
    
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      userRegistrationSuccess: false,
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if ((response.status == 201)) {
              console.log(response.data);
              // this.$store.commit("SET_AUTH_TOKEN", response.data.token)
              
              
              this.$router.push("/login");
              this.userRegistrationSuccess = true;
            }
          })
          .catch((error) => {
            if(error){
              const response = error.response;
              this.registrationErrors = true;
                if (response.status === 400) {
                  this.registrationErrorMsg = 'Bad Request: Validation Errors';
                }
            }
            
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
.form-input-group {
  margin-bottom: 1rem;
}
label {
  margin-right: 0.5rem;
}

#register{
  display: flex;
  justify-content: center;
}
</style>
