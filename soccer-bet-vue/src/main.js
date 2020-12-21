import { createApp } from 'vue'
import App from './App.vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import {ServerTable, ClientTable, EventBus} from 'v-tables-3'

const app = createApp(App)
app.use(ClientTable, [options = {}], [theme = 'bootstrap3'], [swappables = {}])
app.use(VueAxios, axios)
app.mount('#app')
