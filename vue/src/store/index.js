import { createStore as _createStore } from 'vuex';
import axios from 'axios';

export function createStore(currentToken, currentUser, currentRole, isOrganizer) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      currentRole: currentRole || 'public',
      isOrganizer: isOrganizer || false

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
        state.token = '';
        state.user = {};
        state.currentRole = '';
        state.isOrganizer = false;
        axios.defaults.headers.common = {};
      },
      UPDATE_CURRENT_ROLE(state, newRole){
        state.currentRole = newRole;
        localStorage.setItem('currentRole',newRole);
      },
      IS_ORGANIZER(state, status){
        state.isOrganizer = status;
        localStorage.setItem('isOrganizer',status);
      }
        
      
    },
    
    
  });
  return store;
}
