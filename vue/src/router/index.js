import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import RegisterView from '../views/RegisterView.vue';
import TournamentView from '../views/TournamentView.vue';
import TournamentDetailsView from '../views/TournamentDetailsView.vue';
import RegisterDetailsView from '../views/UserDetailsView.vue';
import UserProfileEditView from '../views/UserProfileEditView.vue';
import OrganizerCreateTournamentView from '../views/OrganizerCreateTournamentView.vue';
import OrganizerView from '../views/OrganizerView.vue';
import PlayerView from '../views/PlayerView.vue';
import OrganizerEditTournamentView from '../views/OrganizerEditTournamentView.vue';
import PlayerProfileView from '../views/PlayerProfileView.vue';
import UpcomingTournamentsView from '../views/UpcomingTournamentsView.vue';
import PastTournamentView from '../views/PastTournamentView.vue';
import ActiveTournamentView from '../views/ActiveTournamentView.vue';
import MyTournamentsView from '../views/MyTournamentsView.vue';
import OrganizerCloseTournamentView from '../views/OrganizerCloseTournamentView.vue';
import MatchView from '../views/MatchView.vue';
import EditMatchView from '../views/EditMatchView.vue';
/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/tournaments",
    name: "tournament",
    component: TournamentView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/tournaments/:tournamentId",
    name: "tournamentDetails",
    component: TournamentDetailsView,
    meta:{
      requiresAuth: false
    }
  },
  {
    path: "/users/:userId/details",
    name: "userDetails",
    component: RegisterDetailsView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/users/:userId/edit",
    name: "profileEdit",
    component: UserProfileEditView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/organizer/create-tournament",
    name: "organizerCreateTournament",
    component: OrganizerCreateTournamentView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/organizer/dashboard",
    name: "organizerView",
    component: OrganizerView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/player/dashboard",
    name: "playerView",
    component: PlayerView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/organizer/:id/edit",
    name: "organizerEditTournament",
    component: OrganizerEditTournamentView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/organizer/close-tournament",
    name: "organizerCloseTournament",
    component: OrganizerCloseTournamentView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/player/profile",
    name: "playerProfileView",
    component: PlayerProfileView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/tournaments/upcoming",
    name: "upcomingTournaments",  
    component: UpcomingTournamentsView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/tournaments/past",
    name: "pastTournaments",
    component: PastTournamentView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/tournaments/active",
    name: "activeTournaments",
    component: ActiveTournamentView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/tournaments/my",
    name: "myTournaments",
    component: MyTournamentsView,
    meta: {
      requiresAuth:true
    }
  },
  {
    path: "/tournaments/:tournamentId/:matchId",
    name: "matchDetails",
    component: MatchView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/tournaments/:tournamentId/:matchId/update",
    name: "editMatch",
    component: EditMatchView,
    meta: {
      requiresAuth: true
    }
  }


];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;
