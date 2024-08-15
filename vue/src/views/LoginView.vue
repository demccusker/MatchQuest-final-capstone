<template>
  <div id="login">
    <header> 
      <router-link v-bind:to="{ name: 'home' }">Tourney</router-link>
    </header>
    <form v-on:submit.prevent="login">
      <h1>Please Sign In</h1>
      <div role="alert" v-if="invalidCredentials">
        Invalid username and password !
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button type="submit">Sign in</button>
      <p>
        <router-link v-bind:to="{ name: 'register' }">Need an account? Sign up.</router-link>
      </p>
      <!-- <h2>{{ this.$store.state }}</h2> -->
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";
import detailsService from "../services/UserDetailsService";

export default {
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false,
      userDetails:{}
      
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);

            // Determine if the user is brand new or exist//
            this.nextPage(response.data.user.id);
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
    nextPage(userId) {
      detailsService.getUserDetails(userId).then(response => {
        // console.log("response below");
        // console.log(response.data);
        
        this.userDetails = response.data;

        
        if(this.userDetails.isStaff===false){
          //This case is for player users only
          this.$store.commit("UPDATE_CURRENT_ROLE","player");
          this.$store.commit("IS_ORGANIZER",false);
          this.$router.push('/player/dashboard');
        }
        else{
          //This case is for organizer users only
          this.$store.commit("UPDATE_CURRENT_ROLE", "organizer");
          this.$store.commit("IS_ORGANIZER", true);
          this.$router.push('/organizer/dashboard');
        }
        

      }).catch(error => {
        if (error) {
          if (error.response.status == 404) {
            //{ name: 'profileEdit', params: { userId: userId } }
            //This pushes to create the userDetailsView for the first time
            this.$router.push({ name: 'userDetails', params: { userId: userId } });
          }
        }
      });
    }
  }
};
</script>

<style scoped>

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
  max-width: 400px; 
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
  color: #669fdd; /* Link color */
  text-decoration: none;
}


</style>