import Vue from 'vue'
import App from './App.vue'
import VueResource from 'vue-resource'
import {ClientTable, Event} from 'vue-tables-2'

Vue.use(VueResource);
Vue.use(ClientTable);

new Vue({
  el: '#app',
  render: h => h(App)
})
