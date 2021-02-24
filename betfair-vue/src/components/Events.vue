<template>
  <div class="">
    <ul>
      <li v-for="event in events" :key="event.id">
        {{ event }}
      </li>
    </ul>
  </div>
</template>

<script>

export default {
  data () {
    return {
      events: {}
    }
  },
  beforeMount() {

    this.$http.get("events/1").then(response => {
      this.events = response.data.result.map(r => r.event);
    });
  },
}
</script>

<style lang="scss">

</style>

