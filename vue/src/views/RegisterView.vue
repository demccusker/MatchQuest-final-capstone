<template>
  <div id="register" v-if ='!userRegistrationSuccess' class="text-center">
    <header>
    <img 
        src="/assets/matchQuest.png" 
        alt="Logo" 
        :style="{ cursor: `pointer` }"
        v-on:click="this.$router.push({name: 'home'})"
        />
  </header>
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
import RegistrationDetails from '../components/UserDetails.vue';

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

header {
  display: flex;
  flex-direction: column;

  & img {
    width: 200px;
  }
}

#register{
  position: fixed;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  text-transform: none;
  font-size: 1.1rem;
  margin: 0; 
}
.form-input-group {
  margin-bottom: 1rem;
}
label {
  margin-right: 0.5rem;
}
input {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}
#login {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh; 
  text-transform: none;
  font-size: 1.1rem;
  margin: 0; 
}

form {
  border: 1px solid #ccc; 
  border-radius: 8px; 
  padding: 2rem; 
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); 
  background-color: #fff; 
  width: 100%;
  max-width: 500px; 
}

h1 {
  margin-bottom: 1rem;
}

.form-input-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem; 
}

input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  width: 100%;
  padding: 0.75rem;
  border: 2px solid #007bff; 
  border-radius: 4px;
  background-color: #007bff; 
  color: white;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease, border-color 0.3s ease;
}

button:hover {
  background-color: #0056b3; 
  border-color: #004080; 
}

button:active {
  background-color: #004080; 
  border-color: #00264d; 
}

router-link {
  display: inline-block;
  margin-top: 0.5rem;
  color: #669fdd; 
  text-decoration: none;
}
</style>../components/CreateUserDetails.vue
