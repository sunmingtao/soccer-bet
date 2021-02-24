<template>
  <div class="">
    <odds-chart :event-id="30186200"></odds-chart>
    <odds-chart :event-id="30186226"></odds-chart>
    <odds-chart :event-id="30186235"></odds-chart>
    <odds-chart :event-id="30304098"></odds-chart>
    <odds-chart :event-id="30303976"></odds-chart>
    <odds-chart :event-id="30303813"></odds-chart>
    <odds-chart :event-id="30289008"></odds-chart>
    <odds-chart :event-id="30289005"></odds-chart>
    <odds-chart :event-id="30288633"></odds-chart>
  </div>
</template>

<script>

import OddsChart from './OddsChart.vue'

export default {
  name: 'app',
  components: {
    'odds-chart': OddsChart
  }
}
</script>

<style lang="scss">

</style>
