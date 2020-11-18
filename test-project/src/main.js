// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store/store.js'
import axios from 'axios'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

require('./mock/mock.js');
//跨域请求携带cookie
axios.defaults.withCredentials = true;
Vue.config.productionTip = false

Vue.use(ElementUI);
Vue.prototype.$axios = axios;

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
})
