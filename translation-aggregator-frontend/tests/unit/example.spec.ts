import { expect } from "chai";
import { shallowMount } from "@vue/test-utils";
import MainPage from "@/view/views/main/MainPage.vue";
import * as TranslationService from "@/data/translation/TranslationService";
import { rightOf } from "@/domain/common/Either";
import UrbanTranslationResult from "@/domain/model/translation/UrbanTranslationResult";

import sinon from "sinon";
import RegularTranslationResult from "@/domain/model/translation/RegularTranslationResult";

afterEach(() => {
  sinon.restore();
});

describe("MainPage.vue", () => {
  it("shows translation on input", async () => {
    const word = "word";

    const regularTranslationResult = [new RegularTranslationResult(word)];
    const urbanTranslationResults = [
      new UrbanTranslationResult(word, "def1", "", 0),
      new UrbanTranslationResult(word, "def2", "", 1),
      new UrbanTranslationResult(word, "def3", "", 2)
    ];

    const yandexStub = sinon
      .stub(TranslationService, "translateRegular")
      .returns(Promise.resolve(rightOf(regularTranslationResult)));

    const urbanStub = sinon
      .stub(TranslationService, "translateUrban")
      .returns(Promise.resolve(rightOf(urbanTranslationResults)));

    const wrapper = shallowMount(MainPage);

    const input = wrapper.find("#main-translations__input");
    (input.element as any).value = word;
    await input.trigger("input");

    setTimeout(() => {
      expect(wrapper.findAll(".urban-translations__list-item").length).to.eq(
        urbanTranslationResults.length
      );

      expect(wrapper.get(".main-translations__wrapper p").text()).to.eq(
        regularTranslationResult[0].text
      );
    }, 500);
  });
});
