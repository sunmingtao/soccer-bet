import Vue from 'vue'
import App from './App.vue'
import VueResource from 'vue-resource'
import {ClientTable, Event} from 'vue-tables-2'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(VueResource)
Vue.use(ClientTable)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

new Vue({
  el: '#app',
  render: h => h(App)
})
