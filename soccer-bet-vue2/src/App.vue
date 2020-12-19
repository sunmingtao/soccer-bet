<template>
  <div id="app">
    <h1>{{ msg }}</h1>
    <select v-model="teamSelected" @change="selectTeam($event)">
      <option v-for="team in teams" :key="team.name" :value="team.name">{{ team.name }}</option>
    </select>
    <v-client-table :data="rows" :columns="columns" :options="options"> </v-client-table>

    <div>
      {{ teamStats }}
    </div>
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
      columns: ['opponent', 'homeOrAway', 'winProb', 'drawProb', 'expectedPoints', 'actualPoints',
        'pointsDifference', 'accumulativePointsDiff', 'last3MatchesPointsDiff', 'last5MatchesPointsDiff'],
      options: {
        headings: {
          opponent: 'Opponent',
          homeOrAway: 'Home/Away',
          winProb: 'Win Prob',
          drawProb: 'Draw Prob',
          expectedPoints: 'Expected Points',
          actualPoints: 'Actual Points',
          pointsDifference: 'Points Diff',
          accumulativePointsDiff: 'Accumulative Points Diff',
          last3MatchesPointsDiff: 'Last 3 Points Diff',
          last5MatchesPointsDiff: 'Last 5 Points Diff'
        },
        perPage: 100,
        perPageValues: [100, 200]
      },
      rows: []
    }
  },
  methods: {
    selectTeam(event) {
      const teamName = event.target.value
      this.$http.get(`http://localhost:8080/team?name=${teamName}`).then((response) => {
        this.rows = response.data
        this.teamStats = response.data
      })
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
