<template>
  <div class="teams">

    <select v-model="teamSelected" @change="selectTeam($event)">
      <option v-for="team in teams" :key="team.name" :value="team.name">{{ team.name }}</option>
    </select>
    <v-client-table v-if="teamStats.length > 0" :data="teamStats" :columns="columns" :options="options">
    </v-client-table>
    <p>Total Profit = {{ totalProfit }}</p>
    <p>Total Profit as Fav = {{ totalProfitAsFav }}</p>
    <p>Total Profit as Strict Fav = {{ totalProfitAsStrictFav }}</p>
    <p>Total Profit Back On Draw = {{ totalProfitBackOnDraw }}</p>
    <p>Total Profit Lay On Draw Fixed Liability = {{ totalProfitLayOnDraw }}</p>
    <v-client-table :columns="teamColumns" :data="teams" :options="teamOptions" @row-click="rowClicked">
      <div slot="child_row" slot-scope="props">
        More info
      </div>
    </v-client-table>
    <p>Total Profit For Lay = {{ leagueStats.totalProfitForLay }} </p>
    <p>Total Profit For Lay As Fav = {{ leagueStats.totalProfitForLayAsFav }} </p>
    <p>Total Profit For Lay As Strict Fav = {{ leagueStats.totalProfitForLayAsStrictFav }} </p>
    <p>Total Profit For Back Draw = {{ leagueStats.totalProfitForBackDraw }} </p>
    <p>Total Profit For Lay Draw Fixed Win = {{ leagueStats.totalProfitForLayDrawFixedWin }} </p>
    <p>Total Profit For Lay Draw Fixed Liability = {{ leagueStats.totalProfitForLayDrawFixedLiability }} </p>
    <p>Total Draw Count = {{ leagueStats.totalDrawCount }} </p>
    <p>Total match count  = {{ leagueStats.totalMatchCount }} </p>
    <p>Draw rate  = {{ leagueStats.totalDrawCount / leagueStats.totalMatchCount}} </p>
    <p>Total Goal count = {{ leagueStats.totalGoalCount }} </p>
    <p>Goal per match = {{ leagueStats.totalGoalCount / leagueStats.totalMatchCount}} </p>
  </div>
</template>

<script>
export default {

  props: {
    league: String,
    season: String
  },
  data () {
    return {
      msg: 'Soccer bet',
      teamSelected: "",
      teams: [],
      leagues: [],
      teamStats: [],
      leagueStats: {},
      columns: ['opponent', 'homeOrAway', 'favouriteOrUnderDog', 'winProb', 'drawProb', 'expectedPoints', 'actualPoints',
        'pointsDifference', 'accumulativePointsDiff',
        'profitFixedWin', 'profitBackOnDrawFixedWin', 'profitLayOnDrawFixedLiability','drawRate',
        'drawRateForLast10Matches', 'averageGoal', 'averageGoalConceded'],
      teamColumns: ['name', 'accumulativePointsDiff',
        'totalProfit', 'totalProfitAsFav', 'totalProfitBackOnDraw', 'drawRate', 'drawRateForLast10Matches',
        'averageGoal', 'averageGoalConceded'],
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
          profitFixedWin: 'Profit',
          profitBackOnDrawFixedWin: 'Profit Back on Draw',
          profitLayOnDrawFixedLiability: 'Profit Lay on Draw',
          drawRate: 'Draw Rate',
          drawRateForLast10Matches: 'Last 10 Draw Rate',
          averageGoal: 'Average Goal Scored',
          averageGoalConceded: 'Average Goal Conceded'
        },
        filterable: false,
        perPage: 100,
        perPageValues: [100, 200]
      },
      teamOptions: {
        headings: {
          name: 'Team',
          accumulativePointsDiff: 'Accumulative Points Diff',
          totalProfit: 'Total profit',
          totalProfitAsFav: 'Total profit as Fav',
          totalProfitBackOnDraw: 'Total Profit Back On Draw',
          drawRate: 'Draw Rate',
          drawRateForLast10Matches: 'Last 10 Draw Rate',
          averageGoal: 'Average Goal Scored',
          averageGoalConceded: 'Average Goal Conceded'
        },
        sortable: ['name', 'accumulativePointsDiff', 'last3MatchesPointsDiff', 'last5MatchesPointsDiff',
          'totalProfit', 'totalProfitAsFav', 'totalProfitBackOnDraw', 'drawRate',
          'drawRateForLast10Matches', 'averageGoal', 'averageGoalConceded'],
        perPage: 100,
        perPageValues: [100, 200]
      }
    }
  },
  methods: {
    selectTeam(event) {
      const teamName = event.target.value
      this.$http.get(`team/${this.season}?name=${teamName}`).then(response => {
        this.teamStats = response.data
      })
    },
    rowClicked(event) {
      console.log("Data", event);
    },
    calculateProfit(teamStats) {
      return teamStats.reduce((partialResult, currentValue) => partialResult + currentValue.profitFixedWin, 0).toFixed(2);
    },
    calculateProfitBackOnDraw(teamStats) {
      return teamStats.reduce((partialResult, currentValue) => partialResult + currentValue.profitBackOnDrawFixedWin, 0).toFixed(2);
    },
    calculateProfitLayOnDraw(teamStats) {
      return teamStats.reduce((partialResult, currentValue) => partialResult + currentValue.profitLayOnDrawFixedLiability, 0).toFixed(2);
    },
  },
  computed: {
    totalProfit() {
      return this.calculateProfit(this.teamStats)
    },
    totalProfitBackOnDraw() {
      return this.calculateProfitBackOnDraw(this.teamStats)
    },
    totalProfitLayOnDraw() {
      return this.calculateProfitLayOnDraw(this.teamStats)
    },
    totalProfitAsFav() {
      return this.calculateProfit(this.teamStats.filter( value => value.favouriteOrUnderDog == 'Fav'))
    },
    totalProfitAsStrictFav() {
      return this.calculateProfit(this.teamStats.filter( value => value.winProb > 0.5))
    },
  },
  beforeMount: function() {
    console.log(`Reload ${this.season} ${this.league}`);
    this.$http.get(`teams/${this.season}/${this.league}`).then(response => {
      this.teams = response.data.teams
      this.leagueStats = response.data
    })
  }
}
</script>

<style lang="scss">
.VueTables__child-row-toggler {
  width: 16px;
  height: 16px;
  line-height: 16px;
  display: block;
  margin: auto;
  text-align: center;
}

.VueTables__child-row-toggler--closed::before {
  content: "+";
}

.VueTables__child-row-toggler--open::before {
  content: "-";
}
</style>

