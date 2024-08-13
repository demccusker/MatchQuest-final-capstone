import { createStore as _createStore } from 'vuex';
import axios from 'axios';

export function createStore(currentToken, currentUser, currentRole, isOrganizer,currentTournamentId,currentTournamentCreatorId) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      currentRole: currentRole || 'public',
      isOrganizer: isOrganizer || false,
      tournamentId: currentTournamentId || -1,
      tournamentCreatorId: currentTournamentCreatorId || -1

    },
    mutations: {
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      },
      SET_USER(state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
      },
      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        localStorage.removeItem('currentRole');
        localStorage.removeItem('isOrganizer');
        localStorage.removeItem('tournamentId');
        localStorage.removeItem('tournamentCreatorId');
        state.token = '';
        state.user = {};
        state.currentRole = '';
        state.isOrganizer = false;
        state.tournamentId = -1;
        state.tournamentCreatorId = -1;
        axios.defaults.headers.common = {};
      },
      UPDATE_CURRENT_ROLE(state, newRole){
        state.currentRole = newRole;
        localStorage.setItem('currentRole',newRole);
      },
      IS_ORGANIZER(state, status){
        state.isOrganizer = status;
        localStorage.setItem('isOrganizer',status);
      },
      SET_TOURNAMENT_ID(state, tournamentId){
        state.tournamentId = tournamentId;
        localStorage.setItem('tournamentId',tournamentId); 
      },
      SET_TOURNAMENT_CREATOR_ID(state, tournamentCreatorId){
        state.tournamentCreatorId = tournamentCreatorId;
        localStorage.setItem('tournamentCreatorId',tournamentCreatorId);
      }

    },
    
    
  });
  return store;
}
