import UrbanTranslationResult from "@/domain/model/translation/UrbanTranslationResult";
import { StoreContext } from "@/domain/store/Store";
import { rightOf } from "@/domain/common/Either";
import Result from "@/domain/common/Result";
import * as TranslationService from "@/data/translation/TranslationService";

export class TranslationState {
  constructor(
    public inputWord: string = "",
    public regularTranslationResult: Result<string> = rightOf(""),
    public urbanTranslationResults: Result<UrbanTranslationResult[]> = rightOf(
      []
    )
  ) {}
}

export enum TranslationMutation {
  SET_INPUT_WORD = "setInputWord",
  SET_REGULAR_RESULT = "setRegularResult",
  SET_URBAN_RESULTS = "setUrbanResults"
}

export enum TranslationAction {
  TRANSLATE_WORD = "translateWord"
}

const TranslationStore = {
  state: new TranslationState(),

  mutations: {
    setInputWord(state: TranslationState, word: string) {
      state.inputWord = word;
    },

    setRegularResult(state: TranslationState, result: Result<string>) {
      state.regularTranslationResult = result;
    },

    setUrbanResults(
      state: TranslationState,
      urbanResults: Result<UrbanTranslationResult[]>
    ) {
      state.urbanTranslationResults = urbanResults;
    }
  },

  actions: {
    async translateWord(
      { commit }: StoreContext<TranslationState>,
      word: string
    ) {
      commit(TranslationMutation.SET_INPUT_WORD, word);

      const [yandexResult, urbanResults] = await Promise.all([
        TranslationService.translateYandex(word),
        TranslationService.translateUrban(word)
      ]);

      commit(TranslationMutation.SET_REGULAR_RESULT, yandexResult)
      commit(TranslationMutation.SET_URBAN_RESULTS, urbanResults)
    }
  }
};

export default TranslationStore;
