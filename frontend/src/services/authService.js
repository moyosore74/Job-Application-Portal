import http from "./http";
import { mockApi } from "./mockApi";

const useMocks = import.meta.env.VITE_USE_MOCKS === "true";

export function registerUser(payload) {
  if (useMocks) {
    return mockApi.registerUser(payload);
  }

  return http.post("/api/auth/register", payload).then((response) => response.data);
}

export function loginUser(payload) {
  if (useMocks) {
    return mockApi.loginUser(payload);
  }

  return http.post("/api/auth/login", payload).then((response) => response.data);
}
