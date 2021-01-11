import Vue from "vue";
import Vuex, { ActionContext } from "vuex";
import createPersistedState from "vuex-persistedstate";
import UserStore from "@/domain/store/user/UserStore";
import TranslationStore from "@/domain/store/translation/TranslationStore";

Vue.use(Vuex);

export type StoreContext<T> = ActionContext<T, any>;

const persistedState = createPersistedState({});

export default new Vuex.Store({
  modules: {
    UserStore,
    TranslationStore
  },
  plugins: [persistedState]
});
