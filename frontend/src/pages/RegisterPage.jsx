import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { registerUser } from "../services/authService";

const initialForm = {
  fullName: "",
  email: "",
  password: "",
  role: "APPLICANT",
  companyId: "",
  companyName: ""
};

export default function RegisterPage() {
  const navigate = useNavigate();
  const [form, setForm] = useState(initialForm);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const isEmployer = form.role === "EMPLOYER";

  const handleSubmit = async (event) => {
    event.preventDefault();
    setSubmitting(true);
    setError("");
    setSuccess("");

    try {
      const payload = {
        fullName: form.fullName,
        email: form.email,
        password: form.password,
        role: form.role
      };

      if (isEmployer) {
        payload.companyId = form.companyId;
        payload.companyName = form.companyName;
      }

      await registerUser(payload);
      setSuccess("Registration successful. You can sign in now.");
      setForm(initialForm);
      setTimeout(() => navigate("/login"), 1000);
    } catch (requestError) {
      setError(requestError.response?.data?.message || requestError.message || "Registration failed.");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="mx-auto flex min-h-[calc(100vh-90px)] max-w-7xl items-center px-4 py-10 sm:px-6 lg:px-8">
      <form className="panel mx-auto w-full max-w-3xl p-8" onSubmit={handleSubmit}>
        <p className="text-sm font-semibold uppercase tracking-[0.25em] text-brand-700">Create account</p>
        <h1 className="mt-3 text-4xl font-black text-slate-950">Join the portal</h1>
        <p className="mt-2 text-sm text-slate-500">Register as an applicant or employer to begin.</p>

        <div className="mt-8 grid gap-4 sm:grid-cols-2">
          <input
            className="input sm:col-span-2"
            placeholder="Full name"
            value={form.fullName}
            onChange={(event) => setForm((current) => ({ ...current, fullName: event.target.value }))}
          />
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
          <select
            className="input sm:col-span-2"
            value={form.role}
            onChange={(event) => setForm((current) => ({ ...current, role: event.target.value }))}
          >
            <option value="APPLICANT">Applicant</option>
            <option value="EMPLOYER">Employer</option>
          </select>

          {isEmployer ? (
            <>
              <input
                className="input"
                placeholder="Company ID"
                value={form.companyId}
                onChange={(event) => setForm((current) => ({ ...current, companyId: event.target.value }))}
              />
              <input
                className="input"
                placeholder="Company name"
                value={form.companyName}
                onChange={(event) => setForm((current) => ({ ...current, companyName: event.target.value }))}
              />
            </>
          ) : null}
        </div>

        {error ? <div className="mt-4 rounded-2xl bg-rose-50 p-4 text-sm text-rose-700">{error}</div> : null}
        {success ? <div className="mt-4 rounded-2xl bg-emerald-50 p-4 text-sm text-emerald-700">{success}</div> : null}

        <button type="submit" className="btn-primary mt-6 w-full" disabled={submitting}>
          {submitting ? "Creating account..." : "Register"}
        </button>

        <p className="mt-6 text-sm text-slate-600">
          Already have an account?{" "}
          <Link to="/login" className="font-semibold text-brand-700">
            Sign in
          </Link>
        </p>
      </form>
    </div>
  );
}
