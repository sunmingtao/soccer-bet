<template>
  <div class="small">
    <line-chart :chart-data="datacollection"></line-chart>
    <button @click="fillData()">Randomize</button>
  </div>
</template>

<script>
import LineChart from './LineChart.js'

export default {
  components: {
    LineChart
  },
  data () {
    return {
      datacollection: {}
    }
  },
  beforeMount() {

    this.$http.get("timedOdds/30186235").then(response => {
      console.log(response.data);
      const labels = response.data.map(x => x.dateTime)
      const winOdds = response.data.map(x => x.winOdds);
      const lossOdds = response.data.map(x => x.lossOdds);
      const drawOdds = response.data.map(x => x.drawOdds);
      this.fillData(labels, winOdds, lossOdds, drawOdds);
    });
  },
  methods: {
    fillData (labels, winOdds, lossOdds, drawOdds) {
      this.datacollection = {
        labels: labels,
        datasets: [
          {
            label: 'Win odds',
            fill: false,
            borderColor: 'red',
            data: winOdds
          }, {
            label: 'Loss odds',
            fill: false,
            borderColor: 'green',
            data: lossOdds
          }, {
            label: 'Draw odds',
            fill: false,
            borderColor: 'black',
            data: drawOdds
          }
        ]
      }
    },
    getRandomInt () {
      return Math.floor(Math.random() * (50 - 5 + 1)) + 5
    }
  }
}
</script>

<style lang="scss">
.small {
  max-width: 600px;
  margin:  150px auto;
}
</style>
