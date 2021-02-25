<template>
  <div class="">
    <v-client-table :data="events" :columns="columns" :options="options"> </v-client-table>
  </div>
</template>

<script>

export default {
  data () {
    return {
      events: [],
      columns: ['id', 'name', 'countryCode', 'openDate'],
      options: {
        headings: {
          id: 'Event ID',
          name: 'Event Name',
          countryCode: 'Country Code',
          openDate: 'Match date'
        },
        sortable: ['countryCode', 'openDate'],
        perPage: 1000,
        perPageValues: [1000, 2000]
      }
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

