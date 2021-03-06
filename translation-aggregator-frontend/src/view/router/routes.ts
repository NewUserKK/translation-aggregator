import Vue from "vue";
import axios from "axios";
import { BASE_URL } from "@/axios";
import VueRouter, { RouteConfig } from "vue-router";

import AuthScreen from "@/view/views/auth/LoginPage.vue";
import AuthenticatedPage from "@/view/views/auth/AuthenticatedPage.vue";
import ComponentsTestPage from "@/view/views/dev/ComponentsTestPage.vue";
import MainPage from "@/view/views/main/MainPage.vue";
import NotFoundPage from "@/view/views/NotFoundPage.vue";
import HistoryPage from "@/view/views/history/HistoryPage.vue";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    redirect: "/main"
  },

  {
    path: "/login",
    component: AuthScreen,
    beforeEnter(to, from, next) {
      /*  important! here we use default axios instance without interceptor
          to avoid infinite recursive logins
      */
      axios.get(`${BASE_URL}/auth/ping`).then(
        () => {
          next({ path: "/" });
        },
        () => {
          next();
        }
      );
    }
  },

  {
    path: "/authenticated",
    component: AuthenticatedPage
  },

  {
    path: "/main",
    component: MainPage
  },

  {
    path: "/history",
    component: HistoryPage
  }
];

if (process.env.NODE_ENV === "development") {
  routes.push(...[{ path: "/dev/components", component: ComponentsTestPage }]);
}

routes.push({ path: "*", component: NotFoundPage });

const router = new VueRouter({ mode: "hash", routes });

export default router;
