<template>
  <div class="small">
    <line-chart :chartData="datacollection"></line-chart>
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
      datacollection: null
    }
  },
  mounted () {
    this.fillData()
  },
  methods: {
    fillData () {
      this.datacollection = {
        labels: ['1', '2'],
        datasets: [
          {
            label: 'Data One',
            fill: false,
            borderColor: 'black',
            data: [this.getRandomInt(), this.getRandomInt()]
          }, {
            label: 'Data two',
            fill: false,
            borderColor: 'red',
            data: [this.getRandomInt(), this.getRandomInt()]
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
