import { Link, useLocation } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

export default function UnauthorizedPage() {
  const location = useLocation();
  const { permissionError, setPermissionError } = useAuth();
  const message = location.state?.message || permissionError || "You do not have permission to access this page.";

  return (
    <div className="flex min-h-screen items-center justify-center bg-slate-50 px-4">
      <div className="panel max-w-lg p-8 text-center">
        <p className="text-sm font-semibold uppercase tracking-[0.25em] text-rose-700">403</p>
        <h1 className="mt-3 text-3xl font-black text-slate-950">Permission denied</h1>
        <p className="mt-4 text-slate-600">{message}</p>
        <div className="mt-6 flex justify-center gap-3">
          <Link to="/" className="btn-secondary" onClick={() => setPermissionError("")}>
            Back home
          </Link>
          <Link to="/login" className="btn-primary" onClick={() => setPermissionError("")}>
            Sign in
          </Link>
        </div>
      </div>
    </div>
  );
}
