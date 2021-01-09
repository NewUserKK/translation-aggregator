<template>
  <div v-if="error" class="error-container">
    <p class="error-title">Error while authenticating</p>
    <p v-if="error.description">Description: {{ error.description }}</p>
  </div>
</template>

<script lang="ts">
  import { Component, Vue } from "vue-property-decorator";

  class AuthenticationError {
    constructor(public readonly description: string = "") {}
  }

  @Component
  export default class AuthenticatedPage extends Vue {
    error: AuthenticationError | null = null;

    created() {
      const query = this.$route.query;
      if (query.error) {
        this.error = new AuthenticationError(
          (query.error_description as string) ?? ""
        );
      }
    }

    async mounted() {
      if (!this.error) {
        this.loadAll();
        await this.$router.replace({ path: "main" });
      }
    }

    loadAll() {
      // make necessary initialization in stores
    }
  }
</script>

<style scoped>
  .error-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    padding: 16px;
  }

  p {
    margin: 8px;

    text-align: center;
    overflow: scroll;
  }

  .error-title {
    font-size: 32px;
    font-weight: bold;
  }
</style>
