import { expect } from "chai";
import { createLocalVue, shallowMount } from "@vue/test-utils";
import MainPage from "@/view/views/main/MainPage.vue";

import sinon from "sinon";
import Vuex from "vuex";
import {
  TranslationAction,
  TranslationState
} from "@/domain/store/translation/TranslationStore";
import HeaderProfileBadge from "@/view/views/main/HeaderProfileBadge.vue";
import { UserState } from "@/domain/store/user/UserStore";
import User from "@/domain/model/user/User";

afterEach(() => {
  sinon.restore();
});

const localVue = createLocalVue();
localVue.use(Vuex);

describe("MainPage.vue", () => {
  let actions: any;
  let store: any;

  beforeEach(() => {
    const state: TranslationState = new TranslationState();

    actions = {
      [TranslationAction.TRANSLATE_WORD]: sinon.spy()
    };

    store = new Vuex.Store({
      modules: {
        TranslationStore: {
          state,
          actions
        }
      }
    });
  });

  it("dispatches translation action on input", async () => {
    const wrapper = shallowMount(MainPage, { store, localVue });

    const input = wrapper.find("#main-translations__input");
    (input.element as any).value = "test";
    await input.trigger("input");

    setTimeout(() => {
      expect(actions[TranslationAction.TRANSLATE_WORD].calledOnce);
    }, 350);
  });
});

describe("HeaderProfileBadge", () => {
  function createStore(state: UserState) {
    return new Vuex.Store({
      modules: {
        UserStore: {
          state
        }
      }
    });
  }

  it("displays sign in button when user not logged", () => {
    const state = {
      loggedUser: null
    };

    const wrapper = shallowMount(HeaderProfileBadge, {
      store: createStore(state),
      localVue
    });

    wrapper.get("#header-profile__sign-in-button");
    expect(wrapper.find("#header-profile__username").exists()).to.be.false;
  });

  it("displays username and sign out button when logged", () => {
    const username = "test";

    const state = new UserState(new User(username, 0));

    const wrapper = shallowMount(HeaderProfileBadge, {
      store: createStore(state),
      localVue
    });

    setTimeout(() => {
      expect(wrapper.find("#header-profile__sign-in-button").exists()).to.be
        .false;
      expect(wrapper.get("#header-profile__username").text()).to.eq(username);
      wrapper.get("#header-profile__sign-out-button");
    }, 10);
  });
});
