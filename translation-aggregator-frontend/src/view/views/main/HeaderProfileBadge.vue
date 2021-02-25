<template>
  <div class="header-profile__container">
    <section v-if="!loggedUser">
      <button
        class="header-profile__button"
        type="button"
        @click="navigateToLogin"
      >
        Sign in
      </button>
    </section>
    <section v-else class="header-profile-signed">
      <p class="header-profile-signed__username">{{ loggedUser.name }}</p>
      <button
        id="header-profile__history-button"
        class="header-profile__button"
        type="button"
        @click="navigateToHistory"
      >
        History
      </button>
      <button class="header-profile__button" type="button" @click="signOut">
        Sign out
      </button>
    </section>
  </div>
</template>

<script lang="ts">
  import { Component, Vue } from "vue-property-decorator";
  import { UserState } from "@/domain/store/user/UserStore";
  import User from "@/domain/model/user/User";

  @Component
  export default class HeaderProfileBadge extends Vue {
    userState: UserState = this.$store.state.UserStore;

    get loggedUser(): User | null {
      return this.userState.loggedUser;
    }

    navigateToHistory() {
      this.$router.push({ path: "history" });
    }

    navigateToLogin() {
      this.$emit("signInPress");
    }

    signOut() {
      this.$emit("signOutPress");
    }
  }
</script>

<style scoped>
  .header-profile__button {
    margin-left: 12px;
    padding: 8px 32px;

    border: 2px solid white;
    border-radius: 12px;
    background: none;
    font-size: 20px;
    color: white;
  }

  .header-profile-signed {
    display: flex;
    flex-direction: row;
    align-items: center;
  }

  .header-profile-signed__username {
    font-size: 20px;
    font-weight: 600;
  }
</style>
