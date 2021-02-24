<template>
  <div class="container">
    <header class="header">
      <h1 class="header__title no-text-selection">Traggregator</h1>
      <HeaderProfileBadge
        class="header__badge"
        @signInPress="$router.push({ path: '/login' })"
      />
    </header>

    <main class="main">
      <section class="main-translations">
        <div class="main-translations__wrapper">
          <label for="main-translations__input"></label>
          <textarea
            id="main-translations__input"
            class="main-translations__input"
            v-model="inputWord"
            placeholder="Type text to translate"
            @input="debouncedTranslate"
          />
        </div>

        <div class="main-translations__wrapper">
          <div class="main-translations-result__wrapper">
            <p class="main-translations-result__text">
              {{ regularTranslationResult.text }}
            </p>
          </div>
        </div>
      </section>

      <section class="urban-translations">
        <h1 class="urban-translations__header">
          <a href="https://urbandictionary.com/">UrbanDictionary</a>
        </h1>
        <ul class="urban-translations__list">
          <li
            class="urban-translations__list-item"
            v-for="urbanResult of urbanTranslationResults"
            :key="urbanResult.defId"
          >
            <Card>
              <p class="urban-translations__list-item__word">
                Word: {{ urbanResult.word }}
              </p>
              <p class="urban-translations__list-item__definition">
                {{ urbanResult.definition }}
              </p>
              <p class="urban-translations__list-item__example">
                Example:<br />
                <span>{{ urbanResult.example }}</span>
              </p>
            </Card>
          </li>
        </ul>
      </section>
    </main>
  </div>
</template>

<script lang="ts">
  import { Component, Vue } from "vue-property-decorator";
  import HeaderProfileBadge from "@/view/views/main/HeaderProfileBadge.vue";
  import UrbanTranslationResult from "@/domain/model/translation/UrbanTranslationResult";
  import { debounce } from "ts-debounce";
  import Card from "@/view/components/Card.vue";
  import {
    TranslationAction,
    TranslationMutation,
    TranslationState
  } from "@/domain/store/translation/TranslationStore";
  import RegularTranslationResult from "@/domain/model/translation/RegularTranslationResult";

  @Component({
    components: { HeaderProfileBadge, Card }
  })
  export default class MainPage extends Vue {
    translationState: TranslationState = this.$store.state.TranslationStore;

    get inputWord(): string {
      return this.translationState.inputWord;
    }

    set inputWord(value: string) {
      this.$store.commit(TranslationMutation.SET_INPUT_WORD, value);
    }

    get regularTranslationResult(): RegularTranslationResult {
      console.log(this.translationState);
      const results = this.translationState.regularTranslationResults
        .matcher<RegularTranslationResult[]>()
        .selfOnRight()
        .onLeft(e => [new RegularTranslationResult(e.message)])
        .match();

      console.log(results);

      if (results.length == 0) {
        return new RegularTranslationResult("");
      }

      return results[0];
    }

    get urbanTranslationResults(): UrbanTranslationResult[] {
      return this.translationState.urbanTranslationResults
        .matcher<UrbanTranslationResult[]>()
        .selfOnRight()
        .onLeft(() => [])
        .match();
    }

    debouncedTranslate = debounce(this.translateText, 300);
    async translateText(event: InputEvent) {
      const query: string = (event.target as any).value;
      await this.$store.dispatch(TranslationAction.TRANSLATE_WORD, query);
    }
  }
</script>

<style scoped>
  .container {
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    width: 100%;
    min-height: 100%;
    background: #eff8fa;
  }

  .header {
    display: flex;
    flex-direction: row;
    align-items: center;

    padding: 12px 28px;
    background: #1989fa;
    color: #ffffff;
  }

  .header__title {
    font-size: 32px;
  }

  .header__title::first-letter {
    font-weight: bold;
    font-size: 44px;
    letter-spacing: -4px;
    color: #b2fcff;
  }

  .header__badge {
    margin-left: auto;
  }

  .main {
    flex-grow: 1;

    width: 100%;

    padding: 24px;
  }

  .main-translations {
    display: flex;
    flex-direction: row;
    justify-content: space-between;

    width: 100%;
  }

  .main-translations__wrapper {
    flex-grow: 1;

    width: 100%;
    height: 170px;

    margin-left: 24px;

    background: white;
    border-radius: 16px;
    box-shadow: 4px 2px 12px rgba(0, 0, 0, 0.1);
  }

  .main-translations__wrapper:first-child {
    margin-left: 0px;
  }

  .main-translations__input {
    width: 100%;
    height: 100%;

    padding: 20px;

    border: 0px;
    border-radius: 40px;

    font-size: 32px;

    resize: none;
  }

  .main-translations-result__wrapper {
    width: 100%;
    height: 100%;

    padding: 20px;
    border: 0px;
    border-radius: 40px;
    resize: none;
  }

  .main-translations-result__text {
    font-size: 32px;
  }

  .urban-translations {
    margin-top: 36px;
  }

  .urban-translations__header {
    font-size: 32px;
    border-bottom: 2px solid black;
  }

  .urban-translations__header a {
    color: black;
    text-decoration: none;
  }

  .urban-translations__header a:hover {
    color: #1989fa;
  }

  .urban-translations__list-item {
    margin: 16px 0;
  }

  .urban-translations__list-item__word {
    margin-bottom: 12px;
  }

  .urban-translations__list-item__definition {
  }

  .urban-translations__list-item__example {
    margin-top: 12px;
  }

  .urban-translations__list-item__example span {
    font-style: italic;
  }

  .footer {
    display: flex;
    flex-direction: row;
    justify-content: center;

    width: 100%;
    padding: 2px 20px;
    background: white;
  }

  @media (min-width: 1366px) {
    .urban-translations {
      width: 50%;
    }
  }
</style>
