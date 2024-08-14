<template>
  <li v-visible="isValid" class="tournament-bracket-item">
    <div class="tournament-bracket-match" tabindex="0">
      <table class="tournament-bracket-table">
        <caption class="tournament-bracket-caption"><time :datetime="this.startDate">{{ this.startDate }}</time>
        </caption>
        <thead class="sr-only">
          <tr>
            <th>Competitor</th>
            <th>Score</th>
          </tr>
        </thead>
        <tbody class="tournament-bracket-content">
          <tr class="tournament-bracket-team" :class="{ 'tournament-bracket-team--winner': isP1Winner }">
            <td class="tournament-bracket-competitor"><abbr class="tournament-bracket-competitor-name"
                title="Hikarus Nakamura">{{ this.player1Name }}</abbr></td>
            <td class="tournament-bracket-score"><span class="tournament-bracket-number">{{ this.matchInfo.player1Score }}</span></td>
          </tr>
          <tr class="tournament-bracket-team" :class="{ 'tournament-bracket-team--winner': isP2Winner }">
            <td class="tournament-bracket-competitor"><abbr class="tournament-bracket-competitor-name"
                title="Magnus Carlsen">{{ this.player2Name }}</abbr></td>
            <td class="tournament-bracket-score"><span class="tournament-bracket-number">{{ this.matchInfo.player2Score }}</span></td>
          </tr>
        </tbody>
      </table>
    </div>
  </li>
</template>

<script>
import MatchService from '../services/MatchService';
import UserDetailsService from '../services/UserDetailsService';

export default {
  props: {
    bracket: {
      Type: Object,
      required: true
    },
    startDate: {
      Type: String,
      required: true
    }
  },
  data() {
    return {
      isValid: false,
      assignedBracket: {
        bracketId: 0,
        matchId: 0,
        name: "",
        parentBracket: 0
      },
      matchInfo: {
        matchId: 0,
        gameId: 0,
        isScrim: false,
        player1Id: 0,
        player2Id: 0,
        player1Score: 0.0,
        player2Score: 0.0,
        winnerId: 0,
        isDraw: false
      },
      player1Name: "",
      player2Name: "",
    }
  },
  computed: {
    isP1Winner() {
      return this.matchInfo.winnerId === this.matchInfo.player1Id;
    },
    isP2Winner() {
      return this.matchInfo.winnerId === this.matchInfo.player2Id;
    }
  },
  created() {
    this.assignedBracket = this.bracket;

    if (this.assignedBracket.matchId === 0) return;
    MatchService.getMatchById(this.assignedBracket.matchId).then(response => {
      if (response.status == 200) {
        this.matchInfo = response.data;
        this.grabPlayerNames();

        this.isValid = true;
      }
    }).catch(error => {
      console.log(error);
    })
  },
  methods: {
    grabPlayerNames() {
      UserDetailsService.getUserDetails(this.matchInfo.player1Id).then(response => {
        if (response.status == 200) {
          this.player1Name = response.data.displayName;
        }
      }).catch(error => {
        console.log(error);
      });

      UserDetailsService.getUserDetails(this.matchInfo.player2Id).then(response => {
        if (response.status == 200) {
          this.player2Name = response.data.displayName;
        }
      }).catch(error => {
        console.log(error);
      })
    }
  }
}
</script>

<style scoped>
.hidden {
  margin:60px
}

.li {
  
}
</style>