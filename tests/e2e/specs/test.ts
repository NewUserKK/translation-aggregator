// https://docs.cypress.io/api/introduction/api.html

import * as TranslationService from "@/data/translation/TranslationService";
import { rightOf } from "@/domain/common/Either";
import UrbanTranslationResult from "@/domain/model/translation/UrbanTranslationResult";

describe("MainPage", () => {
  it("root redirects to main page", () => {
    cy.visit("/");
    cy.location("pathname").should("eq", "/main");
  });

  it("root redirects to main page", () => {
    const word = "word";

    const yandexTranslationResult = word;
    const urbanTranslationResults = [
      new UrbanTranslationResult(word, "def1", "", 0),
      new UrbanTranslationResult(word, "def2", "", 1),
      new UrbanTranslationResult(word, "def3", "", 2)
    ];

    const yandexStub = cy
      .stub(TranslationService, "translateYandex")
      .returns(Promise.resolve(rightOf(yandexTranslationResult)));

    const urbanStub = cy
      .stub(TranslationService, "translateUrban")
      .returns(Promise.resolve(rightOf(urbanTranslationResults)));

    cy.visit("/main");

    cy.get("#main-translations__input").type(word);

    cy.get(".translations__wrapper p").should("have.value", word);
  });
});
