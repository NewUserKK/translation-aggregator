<template>
  <main>
    <article>
      <p>Put your component here</p>
    </article>
  </main>
</template>

<script lang="ts">
  import { Component, Vue } from "vue-property-decorator";

  @Component({
    components: {}
  })
  export default class ComponentsTestPage extends Vue {}
</script>

<style scoped>
  article {
    padding: 2px;
    border: 1px solid black;
  }
</style>
