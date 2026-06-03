import { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";
import { loginUser } from "../services/authService";

export default function LoginPage() {
  const { login } = useAuth();
  const location = useLocation();
  const [form, setForm] = useState({ email: "", password: "" });
  const [error, setError] = useState(location.state?.message || "");
  const [submitting, setSubmitting] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setSubmitting(true);
    setError("");

    try {
      const response = await loginUser(form);
      login(response);
    } catch (requestError) {
      setError(requestError.response?.data?.message || requestError.message || "Unable to sign you in.");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="mx-auto flex min-h-[calc(100vh-90px)] max-w-7xl items-center px-4 py-10 sm:px-6 lg:px-8">
      <div className="mx-auto grid w-full max-w-5xl gap-8 lg:grid-cols-[0.95fr_1.05fr]">
        <div className="panel bg-slate-950 p-8 text-white">
          <p className="text-sm uppercase tracking-[0.25em] text-slate-300">Welcome back</p>
          <h1 className="mt-3 text-4xl font-black">Sign in and continue your hiring journey.</h1>
          <p className="mt-4 leading-7 text-slate-300">
            Applicants can track roles, employers can manage openings, and admins can monitor the full pipeline.
          </p>
        </div>

        <form className="panel p-8" onSubmit={handleSubmit}>
          <h2 className="text-3xl font-bold text-slate-950">Sign In</h2>
          <p className="mt-2 text-sm text-slate-500">Use the account you registered on the portal.</p>

          <div className="mt-6 space-y-4">
            <input
              className="input"
              type="email"
              placeholder="Email address"
              value={form.email}
              onChange={(event) => setForm((current) => ({ ...current, email: event.target.value }))}
            />
            <input
              className="input"
              type="password"
              placeholder="Password"
              value={form.password}
              onChange={(event) => setForm((current) => ({ ...current, password: event.target.value }))}
            />
          </div>

          {error ? <div className="mt-4 rounded-2xl bg-rose-50 p-4 text-sm text-rose-700">{error}</div> : null}

          <button type="submit" className="btn-primary mt-6 w-full" disabled={submitting}>
            {submitting ? "Signing in..." : "Sign In"}
          </button>

          <p className="mt-6 text-sm text-slate-600">
            New here?{" "}
            <Link to="/register" className="font-semibold text-brand-700">
              Create an account
            </Link>
          </p>
        </form>
      </div>
    </div>
  );
}
