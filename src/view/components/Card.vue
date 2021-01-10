<template>
  <div>
    <slot></slot>
  </div>
</template>

<script lang="ts">
  import { Component, Vue } from "vue-property-decorator";

  @Component
  export default class Card extends Vue {}
</script>

<style scoped>
  div {
    display: flex;
    flex-direction: column;

    padding: 8px 16px;

    border-radius: 16px;
    background: #ffffff;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
  }
</style>
