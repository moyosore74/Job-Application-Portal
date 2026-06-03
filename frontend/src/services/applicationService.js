import http from "./http";
import { mockApi } from "./mockApi";

const useMocks = import.meta.env.VITE_USE_MOCKS === "true";

export function applyToJob(payload) {
  if (useMocks) {
    return mockApi.applyToJob(payload);
  }

  return http.post("/api/applications", payload).then((response) => response.data);
}

export function fetchApplicantApplications() {
  if (useMocks) {
    return mockApi.fetchApplicantApplications();
  }

  return http.get("/api/applications/applicant/me").then((response) => response.data);
}

export function fetchEmployerApplications() {
  if (useMocks) {
    return mockApi.fetchEmployerApplications();
  }

  return http.get("/api/applications/company/me").then((response) => response.data);
}

export function updateEmployerApplicationStatus(applicationId, status) {
  if (useMocks) {
    return mockApi.updateApplicationStatus(applicationId, status);
  }

  return http
    .patch(`/api/applications/company/me/${applicationId}/status`, { status })
    .then((response) => response.data);
}

export function fetchAllApplications() {
  if (useMocks) {
    return mockApi.fetchAllApplications();
  }

  return http.get("/api/applications").then((response) => response.data);
}

export function updateAdminApplicationStatus(applicationId, status) {
  if (useMocks) {
    return mockApi.updateApplicationStatus(applicationId, status);
  }

  return http.patch(`/api/applications/${applicationId}/status`, { status }).then((response) => response.data);
}
