import axios from "axios";

export const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || (import.meta.env.DEV ? "" : "http://localhost:8080");

let unauthorizedHandler = null;
let forbiddenHandler = null;

export function setUnauthorizedHandler(handler) {
  unauthorizedHandler = handler;
}

export function setForbiddenHandler(handler) {
  forbiddenHandler = handler;
}

const http = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json"
  }
});

http.interceptors.request.use((config) => {
  const token = localStorage.getItem("job_portal_token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

http.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401 && unauthorizedHandler) {
      unauthorizedHandler();
    }

    if (error.response?.status === 403 && forbiddenHandler) {
      const message =
        error.response?.data?.message || "You do not have permission to perform this action.";
      forbiddenHandler(message);
    }

    return Promise.reject(error);
  }
);

export default http;
