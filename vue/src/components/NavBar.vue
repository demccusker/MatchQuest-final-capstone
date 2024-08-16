<template>
    <aside class="sidebar">
        <div class="nav-top">
            <router-link v-bind:to="{ name: 'home' }" class="nav-link">Home</router-link>
            <!-- <router-link to="/tournaments/my" v-if="isButtonVisibleForOrganizersOnly" class="nav-link">Your Tournaments</router-link> -->
           
            <router-link to="/tournaments" class="nav-link">Tournaments</router-link>
            <br>
            <br>
            <div class="organizer" v-if="isButtonVisibleForOrganizersOnly">
                <h2>Organizer's Features</h2>
                <router-link 
                to="/organizer/create-tournament" class="nav-link" id="create">Create Tournament</router-link>
                <div class="organizer-edit-close" v-if="viewTournamentEditCloseFeatures()">
                    <!-- {{ currentTournamentId }} -->
                    <router-link v-bind:to="{name:'organizerEditTournament', params:{id:currentTournamentId}}" class="nav-link" id="edit">Edit Tournament</router-link>

                </div>
            </div>
            
            

        </div>
        <div class="nav-bottom" v-if="isButtonVisibleForUsers">
            <router-link 
                v-bind:to="switchRoleLink" 
                v-on:click="switchToAlternateRoleView"
                 class="nav-link">{{ alternateRole }}</router-link>
            <router-link to="/player/profile" class="nav-link">Profile</router-link>
            <router-link to="/login" v-on:click="logOut" class="nav-link">Logout</router-link>
        </div>
    </aside>
</template>

<script>
export default {
    data(){
        return{

        }
    },
    computed: {
        isButtonVisibleForUsers() {
            //This link only visible to organizers/players to switch currentRoles
            return this.$store.state.currentRole === "organizer" || this.$store.state.currentRole === "player";
        },
        isButtonVisibleForOrganizersOnly(){
            return this.$store.state.currentRole === "organizer";
        },
        
        switchRoleLink() {
            const currentRole = this.$store.state.currentRole;
            if (currentRole === "player") {
                return "/organizer/dashboard";
            } else {
                return "/player/dashboard";
            }
        },
        alternateRole() {
            const currentRole = this.$store.state.currentRole;
            return currentRole === "player" ? "Switch to Organizer" : "Switch to Player";
        },
        currentTournamentId(){
            console.log("Current tournamentId",this.$store.state.tournamentId);
            return this.$store.state.tournamentId;
        }

    },
    methods: {
        switchToAlternateRoleView() {
            const currentRole = this.$store.state.currentRole;
            if (currentRole === "player") {
                this.$store.commit("UPDATE_CURRENT_ROLE", "organizer")
                this.$router.push("/organizer/dashboard");

            } else {
                this.$store.commit("UPDATE_CURRENT_ROLE", "player");
                this.$router.push("/player/dashboard");
            }

        },
        logOut() {
            this.$store.commit("LOGOUT");
            this.$router.push("/login");
        },
        viewTournamentEditCloseFeatures(){
            const currentUserId = this.$store.state.user.id;
            const creatorId = this.$store.state.tournamentCreatorId;
            const editTournamentRoute = /^\/tournaments\/\d+$/.test(this.$route.path);

            return currentUserId === creatorId && editTournamentRoute;
        }
    }
};
</script>

<style scoped>

.sidebar {
    width: 300;
    background: linear-gradient(135deg, #2c3e50, #34495e);
    color: white;
    padding: 20px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: 100vh;
}

.nav-top,
.nav-bottom {
    display: flex;
    flex-direction: column;
}

.nav-link {
  color: white;
  text-decoration: none;
  padding: 10px;
  margin-bottom: 15px;
  display: flex;
  align-items: center;
  transition: all 0.3s ease;
  border-radius: 8px;
  min-width: 150px;
  text-align: center; 
}

.organizer h2{
  background-color: #f8f9fa;
  border: 1px solid #dee2e6; 
  padding: 15px;
  margin-bottom: 20px; 
  border-radius: 8px; 
  text-align: center; 
  font-size: 1.15rem; 
  color: #343a40; 
}
#create, #close, #edit  {
  color: black;
  background-color: white;
  text-decoration: none;
  padding: 10px;
  margin-bottom: 15px;
  display: flex;
  align-items: center;
  transition: all 0.3s ease;
  border-radius: 8px;
  min-width: 75px;
  text-align: center; 
}

.nav-link:hover {
    background-color: rgba(255, 255, 255, 0.1);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
</style>