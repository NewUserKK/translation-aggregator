import axios from "axios";
import router from "@/view/router/routes";

export const BASE_URL =
  process.env.NODE_ENV === "development"
    ? "http://localhost:8081"
    : "https://your-prod-server.net";

export const axiosInstance = axios.create({
  baseURL: BASE_URL
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
