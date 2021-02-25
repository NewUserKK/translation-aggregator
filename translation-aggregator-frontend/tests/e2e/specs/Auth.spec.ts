import { axiosInstance } from "@/axios";

const usernameInput = () => cy.get("#login-form__username-input");
const passwordInput = () => cy.get("#login-form__password-input");
const registerButton = () => cy.get("#login-form__register-button");
const loginButton = () => cy.get("#login-form__login-button");
const registrationMessage = () => cy.get("#login-form__registration-message");
const errorMessage = () => cy.get("#login-form__error-message");

const testUsername = "test";
const testPassword = "qwerty";

function registerUser(username: string, password: string) {
  cy.intercept("/api/auth/register").as("register");
  usernameInput().type(username);
  passwordInput().type(password);
  registerButton().click();
  cy.wait("@register");
}

function authorizeUser(username: string, password: string) {
  cy.intercept("/api/auth/login").as("login");
  usernameInput().type(username);
  passwordInput().type(password);
  loginButton().click();
  cy.wait("@login");
}

beforeEach(async () => {
  await axiosInstance.post("auth/clearUsers");
});

describe("Authorization", () => {
  it("registration and auth flow should work", () => {
    cy.visit("#login");

    registerUser(testUsername, testPassword);
    registrationMessage();

    usernameInput().then(el => expect(el.text()).equal(""));
    passwordInput().then(el => expect(el.text()).equal(""));

    authorizeUser(testUsername, testPassword);

    cy.hash().should("eq", "#/main");
  });

  it("should not register user with the same name", () => {
    cy.visit("#login");

    registerUser(testUsername, testPassword);
    registerUser(testUsername, testPassword);
    errorMessage();
  });

  it("should not authorize user with incorrect password", () => {
    cy.visit("#login");

    registerUser(testUsername, testPassword);
    authorizeUser(testUsername, testPassword + "sdf");
    errorMessage();
  });

  it("should not authorize unregistered user", () => {
    cy.visit("#login");
    authorizeUser(testUsername, testPassword);
    errorMessage();
  });
});

describe("Authorization redirects", () => {
  it("should not visit protected page when not authorized", () => {
    cy.intercept("/api/history").as("getHistory");
    cy.visit("#history");
    cy.wait("@getHistory");
    cy.hash().should("eq", "#/login");
  });

  it("should visit protected page when authorized", () => {
    cy.visit("#login");
    registerUser(testUsername, testPassword);
    authorizeUser(testUsername, testPassword);

    cy.intercept("/api/history").as("getHistory");
    cy.get("#header-profile__history-button").click();
    cy.wait("@getHistory");
    cy.hash().should("eq", "#/history");
  });
});
