import axios from "axios";
import router from "@/view/router/routes";

const TEST_URL = "http://localhost:8082/api";
const DEV_URL = "http://localhost:8080/api";
const PROD_URL = "http://localhost:8080/api";

let _baseUrl = "";
try {
  if (Cypress.env("TEST_MODE") === "true") {
    _baseUrl = TEST_URL;
  }
} catch (e) {
  // finally
} finally {
  if (_baseUrl === "") {
    _baseUrl =
      process.env.NODE_ENV === "test"
        ? TEST_URL
        : process.env.NODE_ENV === "development"
        ? DEV_URL
        : PROD_URL;
  }
}

export const BASE_URL = _baseUrl;

export const axiosInstance = axios.create({
  baseURL: BASE_URL,
  withCredentials: true
});

axiosInstance.interceptors.response.use(
  response => response,
  async e => {
    if (e.response.status === 401) {
      await router.push({ path: "login" });
    }
    return Promise.reject(e);
  }
);
