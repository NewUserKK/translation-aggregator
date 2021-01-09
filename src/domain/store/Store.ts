import Vue from "vue";
import Vuex, { ActionContext } from "vuex";
import createPersistedState from "vuex-persistedstate";
import UserStore from "@/domain/store/UserStore";

Vue.use(Vuex);

export type StoreContext<T> = ActionContext<T, any>;

const persistedState = createPersistedState({});

export default new Vuex.Store({
  modules: {
    UserStore
  },
  plugins: [persistedState]
});
