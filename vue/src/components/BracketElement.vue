<template>
    <li class="tournament-bracket-item">
        <div class="tournament-bracket-match" tabindex="0">
            <table class="tournament-bracket-table">
                <caption class="tournament-bracket-caption"><time datetime="2020-02-18">18 February 2020</time>
                </caption>
                <thead class="sr-only">
                    <tr>
                        <th>Competitor</th>
                        <th>Score</th>
                    </tr>
                </thead>
                <tbody class="tournament-bracket-content">
                    <tr class="tournament-bracket-team">
                        <td class="tournament-bracket-competitor"><abbr class="tournament-bracket-competitor-name"
                                title="Hikarus Nakamura">Hikaru</abbr></td>
                        <td class="tournament-bracket-score"><span class="tournament-bracket-number">{{ this.matchInfo.player1Score }}</span></td>
                    </tr>
                    <tr class="tournament-bracket-team tournament-bracket-team--winner">
                        <td class="tournament-bracket-competitor"><abbr class="tournament-bracket-competitor-name"
                                title="Magnus Carlsen">Magnus</abbr></td>
                        <td class="tournament-bracket-score"><span class="tournament-bracket-number">{{ this.mathcInfo.player2Score }}</span></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </li>
</template>

<script>
// import MatchService 

export default {
  props: {
    bracket: {
      Type: Object,
      required: true
    }
  },
  data() {
    return {
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
      }
    }
  },

  created() {
    this.assignedBracket = this.bracket;

    /*matchService.getMatch(this.assignedBracket.matchId).then(response => {
      if (response.status == 200) {
        this.matchInfo = response.data;
      }
    }).catch(error => {
      console.log(error);
    })*/
  }
}
</script>

<style>
@import 'https://fonts.googleapis.com/css?family=Work+Sans:300,400,600,700&subset=latin-ext';

* {
  &,
  &::before,
  &::after {
    box-sizing: border-box;
  }
}

html {
  font-size: 15px;
  
  @media (min-width: 24em) { font-size: 14px; }
  @media (min-width: 52em) { font-size: 15px; }
  @media (min-width: 72em) { font-size: 16px; }
}

.sr-only {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0,0,0,0);
  border: 0;
}

.tournament-bracket-item {
  display: flex;
  flex: 0 1 auto;
  justify-content: center;
  flex-direction: column;
  align-items: flex-start;
  position: relative;
  padding: 2% 0;
  width: 48%;
  transition: padding 0.2s linear;
  
  &:nth-child(odd) {
    margin-right: 2%;
  }
  
  &:nth-child(even) {
    margin-left: 2%;
  }
  
  &::after {
    transition: width 0.2s linear;
  }
  
  @media (max-width: 24em) {
    width: 100%;
    
    &:nth-child(odd),
    &:nth-child(even) {
      margin-left: 0;
      margin-right: 0;
    }
  }
  
  @media (min-width: 38em) {
    padding: 0.5em 1em;
    flex-grow: 2;
    width: 100%;
    
    &:nth-child(odd),
    &:nth-child(even) {
      margin: 0;
    }
    
    &::after {
      position: absolute;
      right: 0;
      content: '';
      display: block;
      width: 1em;
      height: 45%;
      border-right: 2px solid #9e9e9e;
    }

    &:nth-child(odd)::after {
      top: 50%;
      border-top: 2px solid #9e9e9e;
      transform: translateY(-1px);
      
      .tournament-bracket--rounded & {
        border-top-right-radius: 0.6em;
      }
    }
    
    &:nth-child(even)::after {
      bottom: 50%;
      border-bottom: 2px solid #9e9e9e;
      transform: translateY(1px);
      
      .tournament-bracket--rounded & {
        border-bottom-right-radius: 0.6em;
      }
    }
    .tournament-bracket-round:first-child & {
       padding-left: 0;
    }
    .tournament-bracket-round:last-child & {
       padding-right: 0;

       &::after {
         display: none;
       }
    }

    .tournament-bracket-round:nth-last-child(2) & {
      &::after {
        border-radius: 0;
        border-right: 0;
      }
    }  
  }
  
  @media (min-width: 52em) {
    padding: 0.5em 1.5em;
    
    &::after {
      width: 1.5em;
    }
  }
}

.tournament-bracket-match {
  display: flex;
  width: 100%;
  background-color: #ffffff;
  padding: 1em;
  border: 1px solid transparent;
  border-radius: 0.1em;
  box-shadow: 4px 2px 0 0 #e5e5e5;
  outline: none; 
  cursor: pointer;
  transition: padding 0.2s ease-in-out, border 0.2s linear;
  
  &:focus {
    border-color: #2196F3;
  }
  
  &::before,
  &::after {
    transition: all 0.2s linear;
  }
  
  @media (max-width: 24em) {
    padding: 0.75em 0.5em;
  }
  
  @media (min-width: 38em) {
    &::before,
    &::after {
      position: absolute;
      left: 0;
      z-index: 1;
      content: '';
      display: block;
      width: 1em;
      height: 10%;
      border-left: 2px solid #9e9e9e;
    }

    &::before  {
      bottom: 50%;
      border-bottom: 2px solid #9e9e9e;
      transform: translate(0, 1px);
      
      .tournament-bracket--rounded & {
        border-bottom-left-radius: 0.6em;
      }
    }

    &::after  {
      top: 50%;
      border-top: 2px solid #9e9e9e;
      transform: translate(0, -1px);
      
      .tournament-bracket--rounded & {
        border-top-left-radius: 0.6em;
      }
    }
  }
  
  @media (min-width: 52em) {
    &::before,
    &::after {
      width: 1.5em;
    }
    
    &::before {
      transform: translate(0, 1px);
    }
    
    &::after {
      transform: translate(0, -1px);
    }
  }
}

.tournament-bracket-content {
  display: flex;
  
  &::after {
    content: ':';
    width: 1em;
    text-align: center;
    padding: 0.2em 0.1em;
    
    @media (min-width: 38em) {
       order: 1;
    }
  }
  
  & .tournament-bracket-team:first-child {
    width: 50%;
    order: 0;
    text-align: right;
    
    @media (min-width: 38em) and (max-width: 52em) {
      align-items: flex-end;
    }
    
    & .tournament-bracket-competitor {
      order: 2;
      justify-content: flex-end;
      
      @media (min-width: 24em) {
        order: 0;
      } 
      
      @media (min-width: 38em) and (max-width: 52em) {
        flex-direction: column-reverse;
        align-items: flex-end;
      }
    }
    
    & .tournament-bracket-score {
      order: 0;
      
      @media (min-width: 24em) {
         order: 2;
      }
    }
  }
  
  & .tournament-bracket-team:last-child {
    width: 50%;
    order: 2;
    text-align: left;
    
    @media (min-width: 38em) and (max-width: 52em) {
      align-items: flex-start;
    }
    
    & .tournament-bracket-competitor {
      @media (min-width: 38em) {
        justify-content: flex-start;
      }
      
      @media (min-width: 38em) and (max-width: 52em) {
        align-items: flex-start;
      }
    }
    
    .tournament-bracket-competitor-name {
      order: 1;
    }
  }
}

.tournament-bracket-table {
  width: 100%;
}

.tournament-bracket-caption {
  font-size: 0.8rem;
  color: #BDBDBD;
  font-weight: 300;
  padding-bottom: 0.75em;
}

.tournament-bracket-team {
  display: flex;
  flex-direction: row-reverse;
  justify-content: space-between;
  
  @media (min-width: 24em) {
    flex-direction: column-reverse;
  }
  
  @media (min-width: 38em) {
    flex-direction: column-reverse;
  }
}

.tournament-bracket-competitor {
  font-size: 0.95rem;
  display: flex;
  margin-top: 0.5em;
  align-items: center;
  
  @media (max-width: 24em) {
    margin-top: 0;
  }
  
  @media (min-width: 38em) and (max-width: 52em) {
    display: flex;
    flex-direction: column;
    
    .tournament-bracket-competitor-name {
      margin-top: 0.2em;
    }
  }
}

.tournament-bracket-competitor-name {
  padding: 0 0.5em;
  color: #212121;
  font-weight: 600;
  text-transform: uppercase;
  border: 0;
  text-decoration: none;
  cursor: help;
  transition: padding 0.2s ease-in-out;
  
  @media (max-width: 24em) {
    padding: 0 0.25em;
  }
  
  @media (min-width: 38em) and (max-width: 52em) {
    padding: 0;
  }
}

.tournament-bracket-score {
  display: flex;
  align-items: center;
  
  .tournament-bracket-team:first-child & {
    flex-direction: row-reverse;
    padding-left: 0.75em;
  }
  
  .tournament-bracket-team:last-child & {
    padding-right: 0.75em;
  }
}

.tournament-bracket-number {
  display: inline-block;
  padding: 0.2em 0.4em 0.2em;
  border-bottom: 0.075em solid transparent;
  font-size: 0.95rem;
  background-color: #F5F5F5;
  border-color: spin(shade(#F5F5F5, 10%), -10);
  
  .tournament-bracket-team--winner & {
    background-color: #FFF176;
    border-color: spin(shade(#FFF176, 2%), -10);
  }
}
</style>