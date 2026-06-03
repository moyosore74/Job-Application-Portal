import { Navigate, Route, Routes } from "react-router-dom";
import Navbar from "./components/Navbar";
import ProtectedRoute from "./components/ProtectedRoute";
import AdminDashboardPage from "./pages/AdminDashboardPage";
import ApplicantDashboardPage from "./pages/ApplicantDashboardPage";
import EmployerDashboardPage from "./pages/EmployerDashboardPage";
import JobDetailsPage from "./pages/JobDetailsPage";
import JobsPage from "./pages/JobsPage";
import LandingPage from "./pages/LandingPage";
import LoginPage from "./pages/LoginPage";
import NotFoundPage from "./pages/NotFoundPage";
import RegisterPage from "./pages/RegisterPage";
import UnauthorizedPage from "./pages/UnauthorizedPage";

function PublicLayout({ children }) {
  return (
    <div className="min-h-screen bg-slate-50">
      <Navbar />
      {children}
    </div>
  );
}

export default function App() {
  return (
    <Routes>
      <Route
        path="/"
        element={
          <PublicLayout>
            <LandingPage />
          </PublicLayout>
        }
      />
      <Route
        path="/jobs"
        element={
          <PublicLayout>
            <JobsPage />
          </PublicLayout>
        }
      />
      <Route
        path="/jobs/:jobId"
        element={
          <PublicLayout>
            <JobDetailsPage />
          </PublicLayout>
        }
      />
      <Route
        path="/login"
        element={
          <PublicLayout>
            <LoginPage />
          </PublicLayout>
        }
      />
      <Route
        path="/register"
        element={
          <PublicLayout>
            <RegisterPage />
          </PublicLayout>
        }
      />
      <Route path="/unauthorized" element={<UnauthorizedPage />} />

      <Route
        path="/applicant/dashboard"
        element={
          <ProtectedRoute allowedRoles={["APPLICANT"]}>
            <ApplicantDashboardPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/employer/dashboard"
        element={
          <ProtectedRoute allowedRoles={["EMPLOYER"]}>
            <EmployerDashboardPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/admin/dashboard"
        element={
          <ProtectedRoute allowedRoles={["ADMIN"]}>
            <AdminDashboardPage />
          </ProtectedRoute>
        }
      />

      <Route path="/home" element={<Navigate to="/" replace />} />
      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  );
}
