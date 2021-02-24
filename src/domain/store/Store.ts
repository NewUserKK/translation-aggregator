import Vue from "vue";
import Vuex, { ActionContext } from "vuex";
import UserStore from "@/domain/store/user/UserStore";
import TranslationStore from "@/domain/store/translation/TranslationStore";

Vue.use(Vuex);

export type StoreContext<T> = ActionContext<T, any>;

export default new Vuex.Store({
  modules: {
    UserStore,
    TranslationStore
  }
});
