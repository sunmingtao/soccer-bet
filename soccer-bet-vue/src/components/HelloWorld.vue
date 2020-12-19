<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <select v-model="teamSelected" @change="selectTeam($event)">
      <option v-for="team in teams" :key="team.name" :value="team.name">{{ team.name }}</option>
    </select>
    <span>Selected: {{ teamSelected }}</span>

  </div>
</template>

<script>

export default {

  data() {
    return {
      teamSelected: "",
      teams: [],
      teamStats: []
    }
  },
  methods: {
    selectTeam(event) {
      const teamName = event.target.value
      this.axios.get(`http://localhost:8080/team?name=${teamName}`).then((response) => {
        this.teamStats = response.data
      })
    }
  },
  props: {
    msg: String
  },
  beforeMount: function(){
    this.axios.get("http://localhost:8080/teams").then((response) => {
      this.teams = response.data
    })
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
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
