<template>
  <div class="not-found-container">
    <p class="not-found-code">404</p>
    <p class="not-found-description">Page not found :(</p>
  </div>
</template>

<script lang="ts">
  import { Component, Vue } from "vue-property-decorator";

  @Component
  export default class NotFoundPage extends Vue {}
</script>

<style scoped>
  .not-found-container {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .not-found-code {
    font-size: 80px;
  }

  .not-found-description {
    font-size: 20px;
  }
</style>
