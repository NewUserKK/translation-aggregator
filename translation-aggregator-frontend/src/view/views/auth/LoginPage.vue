<template>
  <div class="login-container">
    <div class="login-form-wrapper" @submit.prevent="login">
      <h1 class="login-header">Authenticate</h1>
      <form class="login-form">
        <section class="login-form-input">
          <label>
            <input type="text" placeholder="Login" />
          </label>
        </section>
        <section class="login-form-input">
          <label>
            <input type="password" placeholder="Password" />
          </label>
        </section>
        <button type="submit">Sign In</button>
      </form>

      <p v-if="!!loginError">
        {{ loginError }}
      </p>
    </div>
  </div>
</template>

<script lang="ts">
  import { Component, Vue } from "vue-property-decorator";

  @Component
  export default class AuthScreen extends Vue {
    loginError: Error | null = null;

    login() {
      this.$router.replace("main");
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

  button {
    color: white;
    font-weight: bold;
    background: #1989fa;
    border: none;
    border-radius: 10px;
    padding: 10px 80px;
    width: 100%;
    box-sizing: border-box;
    margin-top: 8px;
  }
</style>
