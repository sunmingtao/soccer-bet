<template>
  <div id="app">
    <h1>{{ msg }}</h1>
    <select v-model="teamSelected" @change="selectTeam($event)">
      <option v-for="team in teams" :key="team.name" :value="team.name">{{ team.name }}</option>
    </select>
    <v-client-table v-if="teamStats.length > 0" :data="teamStats" :columns="columns" :options="options"> </v-client-table>
    <p>
      Total Profit = {{ totalProfit }}
    </p>
    <p>
      Total Profit as Fav = {{ totalProfitAsFav }}
    </p>
    <p>
      Total Profit Back On Draw = {{ totalProfitBackOnDraw }}
    </p>
    <v-client-table :data="teams" :columns="teamColumns" :options="teamOptions"> </v-client-table>

  </div>
</template>

<script>
export default {
  name: 'app',
  data () {
    return {
      msg: 'Soccer bet',
      teamSelected: "",
      teams: [],
      teamStats: [],
      columns: ['opponent', 'homeOrAway', 'favouriteOrUnderDog', 'winProb', 'drawProb', 'expectedPoints', 'actualPoints',
        'pointsDifference', 'accumulativePointsDiff', 'last3MatchesPointsDiff', 'last5MatchesPointsDiff', 'profit', 'profitBackOnDraw'],
      teamColumns: ['name', 'accumulativePointsDiff', 'last3MatchesPointsDiff', 'last5MatchesPointsDiff',
        'totalProfit', 'totalProfitAsFav', 'totalProfitBackOnDraw'],
      options: {
        headings: {
          opponent: 'Opponent',
          homeOrAway: 'Home/Away',
          favouriteOrUnderDog: 'Fav/Under',
          winProb: 'Win Prob',
          drawProb: 'Draw Prob',
          expectedPoints: 'Expected Points',
          actualPoints: 'Actual Points',
          pointsDifference: 'Points Diff',
          accumulativePointsDiff: 'Accumulative Points Diff',
          last3MatchesPointsDiff: 'Last 3 Points Diff',
          last5MatchesPointsDiff: 'Last 5 Points Diff',
          profit: 'Profit',
          profitBackOnDraw: 'Profit Back on Draw'
        },
        perPage: 100,
        perPageValues: [100, 200]
      },
      teamOptions: {
        headings: {
          name: 'Team',
          accumulativePointsDiff: 'Accumulative Points Diff',
          last3MatchesPointsDiff: 'Last 3 Points Diff',
          last5MatchesPointsDiff: 'Last 5 Points Diff',
          totalProfit: 'Total profit',
          totalProfitAsFav: 'Total profit as Fav',
          totalProfitBackOnDraw: 'Total Profit Back On draw'
        },
        sortable: ['name', 'accumulativePointsDiff', 'last3MatchesPointsDiff', 'last5MatchesPointsDiff',
          'totalProfit', 'totalProfitAsFav', 'totalProfitBackOnDraw'],
        perPage: 100,
        perPageValues: [100, 200]
      }
    }
  },
  methods: {
    selectTeam(event) {
      const teamName = event.target.value
      this.$http.get(`http://localhost:8080/team?name=${teamName}`).then((response) => {
        this.teamStats = response.data
      })
    },
    calculateProfit(teamStats) {
      return teamStats.reduce((partialResult, currentValue) => partialResult + currentValue.profit, 0).toFixed(2);
    },
    calculateProfitBackOnDraw(teamStats) {
      return teamStats.reduce((partialResult, currentValue) => partialResult + currentValue.profitBackOnDraw, 0).toFixed(2);
    },
  },
  computed: {
    totalProfit() {
      return this.calculateProfit(this.teamStats)
    },
    totalProfitBackOnDraw() {
      return this.calculateProfitBackOnDraw(this.teamStats)
    },
    totalProfitAsFav() {
      return this.calculateProfit(this.teamStats.filter( value => value.favouriteOrUnderDog == 'Fav'))
    }
  },
  beforeMount: function(){
    this.$http.get("http://localhost:8080/teams").then((response) => {
      this.teams = response.data
    })
  },
}
</script>

<style lang="scss">
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
