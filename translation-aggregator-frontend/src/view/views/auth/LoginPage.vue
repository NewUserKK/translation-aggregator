<template>
  <div class="login-container">
    <div class="login-form-wrapper">
      <h1 class="login-header">Authenticate</h1>
      <form class="login-form">
        <section class="login-form-input">
          <label>
            <input v-model="username" type="text" placeholder="Login" />
          </label>
        </section>
        <section class="login-form-input">
          <label>
            <input v-model="password" type="password" placeholder="Password" />
          </label>
        </section>
        <section class="login-form-buttons">
          <button class="login-form__button" type="button" @click="register">
            Register
          </button>
          <button class="login-form__button" type="button" @click="login">
            Sign In
          </button>
        </section>
      </form>

      <p v-if="!!loginError">
        {{ loginError }}
      </p>

      <p v-if="showRegisterSuccess">
        Registered successfully!
      </p>
    </div>
  </div>
</template>

<script lang="ts">
  import { Component, Vue } from "vue-property-decorator";
  import { UserAction } from "@/domain/store/user/UserStore";

  @Component
  export default class AuthScreen extends Vue {
    username = "";
    password = "";

    loginError: Error | null = null;
    showRegisterSuccess = false;

    async register() {
      try {
        await this.$store.dispatch(UserAction.REGISTER_USER, {
          username: this.username,
          password: this.password
        });
        this.loginError = null;
        this.username = "";
        this.password = "";
        this.showRegisterSuccess = true;
        setTimeout(() => {
          this.showRegisterSuccess = false;
        }, 5000);
      } catch (e) {
        this.loginError = e;
      }
    }

    async login() {
      try {
        await this.$store.dispatch(UserAction.AUTHENTICATE, {
          username: this.username,
          password: this.password
        });
        this.loginError = null;
        await this.$router.replace("main");
      } catch (e) {
        this.loginError = e;
      }
    }
  }
</script>

<style scoped>
  .login-container {
    display: flex;
    flex-direction: column;
    align-items: center;

    width: 100%;
    height: 100%;
  }

  .login-form-wrapper {
    display: flex;
    flex-direction: column;

    margin: auto;
  }

  .login-header {
    text-align: center;
    font-size: 28px;
    margin-bottom: 16px;
  }

  .login-form {
    padding: 16px;

    border: 1px solid #dddddd;
    border-radius: 20px;
  }

  .login-form-input {
    margin: 4px 0px;
  }

  .login-form-buttons {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }

  .login-form__button {
    flex-grow: 1;
    box-sizing: border-box;

    margin-right: 8px;
    margin-top: 8px;
    padding: 10px 80px;

    background: #1989fa;

    border: none;
    border-radius: 10px;

    font-weight: bold;
    color: white;
  }

  .login-form__button:last-child {
    margin-right: 0px;
  }
</style>
