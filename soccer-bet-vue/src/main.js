import { createApp } from 'vue'
import App from './App.vue'
import axios from 'axios'
import VueAxios from 'vue-axios'

const createdApp = createApp(App)
createdApp.use(VueAxios, axios)
createdApp.mount('#app')
