<template>
  <div class="header-profile__container">
    <section v-if="!loggedUser">
      <button
        class="header-profile__sign-in-button"
        type="button"
        @click="navigateToLogin"
      >
        Sign in
      </button>
    </section>
    <section v-else>
      <p>{{ loggedUser.name }}</p>
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

    navigateToLogin() {
      this.$router.replace({ path: "/login" });
    }
  }
</script>

<style scoped>
  .header-profile__sign-in-button {
    padding: 8px 32px;

    border: 2px solid white;
    border-radius: 12px;
    background: none;
    font-size: 20px;
    color: white;
  }
</style>
