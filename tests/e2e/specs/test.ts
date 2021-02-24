// https://docs.cypress.io/api/introduction/api.html

// import * as TranslationService from "../../../src/data/translation/TranslationService";
// import { rightOf } from "@/domain/common/Either";
// import UrbanTranslationResult from "@/domain/model/translation/UrbanTranslationResult";
// import RegularTranslationResult from "@/domain/model/translation/RegularTranslationResult";

describe("MainPage", () => {
  it("root redirects to main page", () => {
    cy.visit("/");
    cy.hash().should("eq", "#/main");
  });

  it("translation request works correctly", () => {
    const word = "word";

    // const regularTranslationResults = [new RegularTranslationResult(word)];
    // const urbanTranslationResults = [
    //   new UrbanTranslationResult(word, "def1", "", 0),
    //   new UrbanTranslationResult(word, "def2", "", 1),
    //   new UrbanTranslationResult(word, "def3", "", 2)
    // ];

    // cy.route(`${BASE_URL}/translate/regular`, regularTranslationResults)
    // cy.route(`${BASE_URL}/translate/urban`, urbanTranslationResults)

    // cy.stub(TranslationService, "translateRegular").returns(
    //   Promise.resolve(rightOf(regularTranslationResults))
    // );
    //
    // cy.stub(TranslationService, "translateUrban").returns(
    //   Promise.resolve(rightOf(urbanTranslationResults))
    // );

    cy.visit("#main");

    cy.get("#main-translations__input").type(word);
    cy.wait(1000);
    cy.get(".main-translations-result__text").contains(/[a-zA-Zа-яА-Я]+/);
  });

  it("empty input implies empty result", () => {
    cy.visit("#main");

    cy.get("#main-translations__input").clear();
    cy.wait(1000);
    cy.get(".main-translations-result__text").should("have.value", "");
  });

  it("result should be cleared after clearing input", () => {
    cy.visit("#main");

    cy.get("#main-translations__input").type("word");
    cy.wait(1000);

    cy.get("#main-translations__input").clear();
    cy.wait(1000);

    cy.get(".main-translations-result__text").should("have.value", "");
  });

  it("title of urban results should point to UrbanDictionary website", () => {
    cy.visit("#main");

    cy.get(".urban-translations__header a")
      .should("have.attr", "href")
      .and("eq", "https://urbandictionary.com/");
  });
});
