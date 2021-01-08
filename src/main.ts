import Vue from "vue";
import App from "./App.vue";

import VueAxios from "vue-axios";

import "./registerServiceWorker";
import router from "./view/router/routes";
import store from "./domain/store/Store";
import { axiosInstance } from "@/axios";

Vue.config.productionTip = false;
Vue.config.errorHandler = function(err: Error, vm, info) {
  // prettier-ignore
  if (err.message.startsWith("Avoided redundant navigation to current location")) {
    return;
  }

  throw err;
};

Vue.use(VueAxios, axiosInstance);

const app = new Vue({
  router,
  store,
  render: h => h(App)
});

app.$mount("#app");
