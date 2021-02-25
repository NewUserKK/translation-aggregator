import User from "@/domain/model/user/User";
import * as AuthService from "@/data/auth/AuthService";
import { StoreContext } from "@/domain/store/Store";

export class UserState {
  constructor(public loggedUser: User | null = null) {}
}

export enum UserMutation {
  SET_LOGGED_USER = "setLoggedUser"
}

export enum UserAction {
  REGISTER_USER = "registerUser",
  AUTHENTICATE = "authenticate",
  LOGOUT = "logout"
}

interface AuthPayload {
  username: string;
  password: string;
}

const UserStore = {
  state: new UserState(),

  mutations: {
    setLoggedUser(state: UserState, loggedUser: User | null) {
      state.loggedUser = loggedUser;
    }
  },

  actions: {
    async registerUser(
      context: StoreContext<UserState>,
      { username, password }: AuthPayload
    ) {
      (await AuthService.register(username, password)).matcher().throwOnLeft();
    },

    async authenticate(
      { commit }: StoreContext<UserState>,
      { username, password }: AuthPayload
    ) {
      const user = (await AuthService.authenticate(username, password))
        .matcher()
        .selfOnRight()
        .throwOnLeft()
        .match();

      commit(UserMutation.SET_LOGGED_USER, user);
    },

    async logout({ commit }: StoreContext<UserState>) {
      (await AuthService.logout())
        .matcher()
        .onRight(() => {
          commit(UserMutation.SET_LOGGED_USER, null);
        })
        .throwOnLeft();
    }
  }
};

export default UserStore;
