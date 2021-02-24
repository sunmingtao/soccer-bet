import Vue from 'vue'
import App from './App.vue'
import Events from './components/Events.vue'
import EventOdds from './components/EventOdds.vue'
import VueResource from 'vue-resource'
import VueRouter from 'vue-router';

Vue.use(VueResource);
Vue.use(VueRouter);

const routes = [
  { path: '/', component: EventOdds },
  {
    path: '/events',
    component: Events
  }
]

export const router = new VueRouter({
  mode: 'history',
  routes: routes
})

Vue.http.options.root="http://localhost:8080"

Vue.config.productionTip = false

new Vue({
  router: router,
  render: h => h(App),
}).$mount('#app')
