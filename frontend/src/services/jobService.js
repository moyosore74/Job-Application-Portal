import http from "./http";
import { mockApi } from "./mockApi";

const useMocks = import.meta.env.VITE_USE_MOCKS === "true";

export function fetchJobs() {
  if (useMocks) {
    return mockApi.fetchJobs();
  }

  return http.get("/api/jobs").then((response) => response.data);
}

export function searchJobs(value) {
  if (useMocks) {
    return mockApi.searchJobs(value);
  }

  return http.get(`/api/jobs/search?value=${encodeURIComponent(value)}`).then((response) => {
    const result = response.data;
    return Array.isArray(result) ? result : [result];
  });
}

export function fetchJobById(jobId) {
  if (useMocks) {
    return mockApi.fetchJobById(jobId);
  }

  return http.get(`/api/jobs/search?value=${encodeURIComponent(jobId)}`).then((response) => response.data);
}

export function createJob(payload) {
  if (useMocks) {
    return mockApi.createJob(payload);
  }

  return http.post("/api/jobs", payload).then((response) => response.data);
}

export function fetchEmployerJobs() {
  if (useMocks) {
    return mockApi.fetchEmployerJobs();
  }

  return http.get("/api/jobs/company/me").then((response) => response.data);
}
