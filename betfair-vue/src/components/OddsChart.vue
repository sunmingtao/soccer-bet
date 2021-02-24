<template>
  <div class="">
    <line-chart :chart-data="datacollection" :options="chartOptions"></line-chart>
  </div>
</template>

<script>
import LineChart from '../LineChart.js'

export default {
  props: {
    eventId: Number,
  },
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

    this.$http.get("timedOdds/" + this.eventId).then(response => {
      const labels = response.data.map(x => x.dateTime)
      const winOdds = response.data.map(x => x.winOdds);
      const lossOdds = response.data.map(x => x.lossOdds);
      const drawOdds = response.data.map(x => x.drawOdds);
      const datasetsLabel = response.data[0].eventName;
      this.fillData(labels, datasetsLabel, winOdds, lossOdds, drawOdds);
    });
  },
  methods: {
    fillData (labels, datasetsLabel, winOdds, lossOdds, drawOdds) {
      this.datacollection = {
        labels: labels,
        datasets: [
          {
            label: datasetsLabel,
            fill: false,
            borderColor: 'green',
            data: lossOdds
          }
        ]
      }
    }
  }
}
</script>

<style lang="scss">

</style>

