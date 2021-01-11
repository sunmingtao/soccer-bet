<template>
  <div id="app">
    <h1>{{ msg }}</h1>
    <select v-model="seasonSelected">
      <option v-for="season in seasons" :key="season" :value="season">{{ season }}</option>
    </select>
    <div v-if="seasonSelected != ''">
      <b-tabs content-class="mt-3">
        <b-tab v-for="league in leagues" :title="league" :key="league">
          <app-teams v-bind:league="league" v-bind:season="seasonSelected" :key="seasonSelected"></app-teams>
        </b-tab>
      </b-tabs>
    </div>

  </div>
</template>

<script>

import Teams from './components/Teams.vue'

export default {
  name: 'app',
  components: {
    'app-teams': Teams
  },
  data () {
    return {
      msg: 'Soccer bet',
      leagues: [],
      seasons: [],
      seasonSelected: ""
    }
  },
  beforeMount: function() {
    this.$http.get("leagues").then(response => {
      this.leagues = response.data
    });
    this.$http.get("seasons").then(response => {
      this.seasons = response.data;
      this.seasonSelected = this.seasons[this.seasons.length - 1];
    });

  }
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
