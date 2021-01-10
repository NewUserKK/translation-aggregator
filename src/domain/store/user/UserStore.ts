import User from "@/domain/model/user/User";

export class UserState {
  constructor(public loggedUser: User | null = null) {}
}

export enum UserMutation {}

export enum UserAction {}

const UserStore = {
  state: new UserState(),

  mutations: {
    setLoggedUser(state: UserState, loggedUser: User | null) {
      state.loggedUser = loggedUser;
    }
  },

  actions: {}
};

export default UserStore;
