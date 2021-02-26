import UrbanTranslationResult from "@/domain/model/translation/UrbanTranslationResult";
import RegularTranslationResult from "@/domain/model/translation/RegularTranslationResult";
import { firefox } from "playwright";

const DEBOUNCE_TIME_MILLIS = 350;

describe("MainPage", () => {
  it("root redirects to main page", async () => {
    const browser = await firefox.launch();
    const page = await (await browser.newContext()).newPage()
    await page.goto("/");
    expect(page.url()).to.be.eq("#/main")
    await browser.close();
  });

  // it("translation request works correctly", () => {
  //   const word = "word";
  //
  //   const regularTranslationResults = [new RegularTranslationResult(word)];
  //   const urbanTranslationResults = [
  //     new UrbanTranslationResult(word, "def1", "", 0),
  //     new UrbanTranslationResult(word, "def2", "", 1),
  //     new UrbanTranslationResult(word, "def3", "", 2)
  //   ];
  //
  //   cy.intercept("/api/translate/regular", regularTranslationResults);
  //   cy.intercept("/api/translate/urban", urbanTranslationResults);
  //
  //   cy.visit("#main");
  //   cy.get("#main-translations__input").type(word);
  //   cy.get(".main-translations-result__text").contains(/[a-zA-Zа-яА-Я]+/);
  // });
  //
  // it("empty input implies empty result", () => {
  //   cy.visit("#main");
  //
  //   cy.get("#main-translations__input").clear();
  //   cy.wait(DEBOUNCE_TIME_MILLIS);
  //   cy.get(".main-translations-result__text").then(el =>
  //     expect(el.text().trim()).equal("")
  //   );
  // });
  //
  // it("result should be cleared after clearing input", () => {
  //   cy.visit("#main");
  //
  //   cy.intercept(`/api/translate/*`).as("getTranslations");
  //
  //   cy.get("#main-translations__input").type("word");
  //   cy.wait(DEBOUNCE_TIME_MILLIS);
  //   cy.wait("@getTranslations");
  //
  //   cy.get("#main-translations__input").clear();
  //   cy.wait(DEBOUNCE_TIME_MILLIS);
  //   cy.wait("@getTranslations");
  //
  //   cy.get(".main-translations-result__text").then(el =>
  //     expect(el.text().trim()).equal("")
  //   );
  // });
  //
  // it("title of urban results should point to UrbanDictionary website", () => {
  //   cy.visit("#main");
  //
  //   cy.get(".urban-translations__header a")
  //     .should("have.attr", "href")
  //     .and("eq", "https://urbandictionary.com/");
  // });
});
