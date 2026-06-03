import { Link, NavLink } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";
import { getDashboardPath } from "../utils/auth";

export default function Navbar() {
  const { isAuthenticated, logout, user } = useAuth();

  return (
    <header className="sticky top-0 z-40 border-b border-slate-200/80 bg-white/90 backdrop-blur">
      <div className="mx-auto flex max-w-7xl items-center justify-between px-4 py-4 sm:px-6 lg:px-8">
        <Link to="/" className="text-lg font-black tracking-tight text-slate-900">
          Job<span className="text-brand-700">Portal</span>
        </Link>

        <nav className="hidden items-center gap-6 md:flex">
          <NavLink to="/" className="text-sm font-medium text-slate-600 hover:text-brand-700">
            Home
          </NavLink>
          <NavLink to="/jobs" className="text-sm font-medium text-slate-600 hover:text-brand-700">
            Jobs
          </NavLink>
        </nav>

        <div className="flex items-center gap-3">
          {isAuthenticated ? (
            <>
              <Link to={getDashboardPath(user.role)} className="btn-secondary hidden sm:inline-flex">
                Dashboard
              </Link>
              <button type="button" onClick={logout} className="btn-primary">
                Log out
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="btn-secondary">
                Sign in
              </Link>
              <Link to="/register" className="btn-primary">
                Register
              </Link>
            </>
          )}
        </div>
      </div>
    </header>
  );
}
