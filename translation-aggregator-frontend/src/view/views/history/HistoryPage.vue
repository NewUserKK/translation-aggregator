<template>
  <div class="container">
    <button class="back-button" @click="$router.back()">Back</button>
    <h1>History</h1>
    <ul>
      <li v-for="query in history" :key="query">- {{ query }}</li>
    </ul>
  </div>
</template>

<script lang="ts">
  import { Component, Vue } from "vue-property-decorator";
  import * as HistoryService from "@/data/history/HistoryService";

  @Component
  export default class HistoryPage extends Vue {
    history: string[] = [];

    mounted() {
      HistoryService.getHistory()
        .then(result => {
          this.history = result
            .matcher<string[]>()
            .selfOnRight()
            .throwOnLeft()
            .match();
        })
        .catch(() => {});
    }
  }
</script>

<style scoped>
  .container {
    padding: 16px;
  }

  .back-button {
    margin-bottom: 24px;
  }

  ul {
    display: flex;
    flex-direction: column;
  }

  li {
    margin-top: 12px;
  }
</style>
