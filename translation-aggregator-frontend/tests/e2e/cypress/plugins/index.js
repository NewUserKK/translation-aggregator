/* eslint-disable arrow-body-style */
// https://docs.cypress.io/guides/guides/plugins-guide.html

// if you need a custom webpack configuration you can uncomment the following import
// and then use the `file:preprocessor` event
// as explained in the cypress docs
// https://docs.cypress.io/api/plugins/preprocessors-api.html#Examples

// eslint-disable-next-line @typescript-eslint/no-var-requires
const webpack = require("@cypress/webpack-preprocessor");
// const browserify = require("@cypress/browserify-preprocessor");

module.exports = (on, config) => {
  // const browserifyOptions = browserify.defaultOptions;
  // browserifyOptions.browserifyOptions.transform[1][1].babelrc = true;
  // browserifyOptions.typescript = require.resolve("typescript");
  // on("file:preprocessor", browserify(browserifyOptions));

  on(
    "file:preprocessor",
    webpack({
      webpackOptions: require("../../../../webpack.config.js"),
      watchOptions: {}
    })
  );

  return Object.assign({}, config, {
    fixturesFolder: "tests/e2e/cypress/fixtures",
    integrationFolder: "tests/e2e/cypress/specs",
    screenshotsFolder: "tests/e2e/cypress/screenshots",
    videosFolder: "tests/e2e/cypress/videos",
    supportFile: "tests/e2e/cypress/support/index.js"
  });
};
