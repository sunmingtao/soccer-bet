<template>
  <div class="">
    <line-chart :chart-data="datacollection" :options="chartOptions"></line-chart>
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
      datacollection: {},
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        width: 800
      }
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
            label: 'Loss odds',
            fill: false,
            borderColor: 'green',
            data: lossOdds
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
