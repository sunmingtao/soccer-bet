<template>
  <div class="">
    <odds-chart :event-id="30186200"></odds-chart>
    <odds-chart :event-id="30186226"></odds-chart>
    <odds-chart :event-id="30186235"></odds-chart>
  </div>
</template>

<script>

import OddsChart from './components/OddsChart.vue'

export default {
  name: 'app',
  components: {
    'odds-chart': OddsChart
  }
}
</script>

<style lang="scss">

</style>
