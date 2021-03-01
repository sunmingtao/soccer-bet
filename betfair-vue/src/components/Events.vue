<template>
  <div class="">
    <v-client-table :data="events" :columns="columns" :options="options">
      <div slot="action" slot-scope="props">
        <div v-if="props.row.watch">
          <button @click="removeFromWatch(props.row.id)">Remove from watch</button>
        </div>
        <div v-else>
          <button @click="addToWatch(props.row.id)">Add to watch</button>
        </div>
      </div>
    </v-client-table>
  </div>
</template>

<script>

export default {
  data () {
    return {
      events: [],
      columns: ['id', 'name', 'countryCode', 'openDate', 'action'],
      options: {
        headings: {
          id: 'Event ID',
          name: 'Event Name',
          countryCode: 'Country Code',
          openDate: 'Match date',
          action: 'Action'
        },
        sortable: ['countryCode', 'openDate'],
        perPage: 1000,
        perPageValues: [1000, 2000]
      }
    }
  },
  methods: {
    addToWatch(eventId) {
      this.$http.put('soccer/watch/' + eventId).then(() => {
        this.events.filter(event => event.id === eventId)[0].watch = true;
      }, error => {
        console.log(error);
      });
    },
    removeFromWatch(eventId) {
      this.$http.delete('soccer/unwatch/' + eventId).then(() => {
        this.events.filter(event => event.id === eventId)[0].watch = false;
      }, error => {
        console.log(error);
      });
    }
  },
  beforeMount() {
    this.$http.get("soccer/events").then(response => {
      this.events = response.data;
    });
  },
}
</script>

<style lang="scss">

</style>

