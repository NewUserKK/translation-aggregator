import { firefox } from "playwright";

describe("MainPage", () => {
  it("root redirects to main page", async () => {
    const browser = await firefox.launch();
    const page = await (await browser.newContext()).newPage();
    await page.goto("/");
    expect(page.url()).to.be.eq("#/main");
    await browser.close();
  });
});
