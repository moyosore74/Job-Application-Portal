import { useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import StatusBadge from "../components/StatusBadge";
import { useAuth } from "../hooks/useAuth";
import { applyToJob } from "../services/applicationService";
import { fetchJobById } from "../services/jobService";
import { formatCurrency, formatDate } from "../utils/formatters";

export default function JobDetailsPage() {
  const { jobId } = useParams();
  const navigate = useNavigate();
  const location = useLocation();
  const { isAuthenticated, user } = useAuth();
  const [job, setJob] = useState(null);
  const [phoneNumber, setPhoneNumber] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchJobById(jobId)
      .then((data) => setJob(data))
      .catch(() => setError("We could not load this job right now."))
      .finally(() => setLoading(false));
  }, [jobId]);

  const handleApply = async () => {
    if (!isAuthenticated) {
      navigate("/login", { state: { from: location.pathname, message: "Sign in to apply for a job." } });
      return;
    }

    if (user.role !== "APPLICANT") {
      setError("Only applicant accounts can submit applications.");
      return;
    }

    if (!phoneNumber.trim()) {
      setError("Please enter a phone number before applying.");
      return;
    }

    try {
      setError("");
      const response = await applyToJob({ jobId, phoneNumber });
      setMessage(`Application sent successfully for ${response.jobTitle}.`);
      setPhoneNumber("");
    } catch (applyError) {
      setError(applyError.response?.data?.message || "We could not submit your application.");
    }
  };

  if (loading) {
    return <div className="mx-auto max-w-5xl px-4 py-10 sm:px-6 lg:px-8">Loading job details...</div>;
  }

  if (!job) {
    return <div className="mx-auto max-w-5xl px-4 py-10 text-rose-700 sm:px-6 lg:px-8">{error}</div>;
  }

  return (
    <div className="mx-auto max-w-5xl px-4 py-10 sm:px-6 lg:px-8">
      <div className="panel p-8">
        <div className="mb-6 flex flex-wrap items-start justify-between gap-4">
          <div>
            <p className="text-sm font-semibold uppercase tracking-[0.25em] text-brand-700">{job.companyName}</p>
            <h1 className="mt-3 text-4xl font-black tracking-tight text-slate-950">{job.jobTitle}</h1>
          </div>
          <span className="rounded-full bg-brand-50 px-4 py-2 text-sm font-semibold text-brand-800">
            {job.jobType}
          </span>
        </div>

        <div className="grid gap-4 rounded-3xl bg-slate-50 p-5 text-sm sm:grid-cols-2">
          <p><span className="font-semibold text-slate-900">Location:</span> {job.location}</p>
          <p><span className="font-semibold text-slate-900">Salary:</span> {formatCurrency(job.salary)}</p>
          <p><span className="font-semibold text-slate-900">Experience:</span> {job.experienceLevel}</p>
          <p><span className="font-semibold text-slate-900">Posted:</span> {formatDate(job.uploadedDate)}</p>
        </div>

        <div className="mt-8 space-y-6">
          <section>
            <h2 className="text-lg font-bold text-slate-900">Role overview</h2>
            <p className="mt-2 leading-7 text-slate-600">{job.description}</p>
          </section>

          <section>
            <h2 className="text-lg font-bold text-slate-900">Skills needed</h2>
            <p className="mt-2 leading-7 text-slate-600">{job.requiredSkills}</p>
          </section>

          <section className="grid gap-4 sm:grid-cols-2">
            <div className="rounded-3xl border border-slate-200 p-5">
              <h3 className="font-bold text-slate-900">Remote setup</h3>
              <p className="mt-2 text-sm text-slate-600">Requires internet: {String(job.requiresInternet ?? false)}</p>
              <p className="mt-1 text-sm text-slate-600">Work hours: {job.workHours || "Not specified"}</p>
            </div>
            <div className="rounded-3xl border border-slate-200 p-5">
              <h3 className="font-bold text-slate-900">Onsite setup</h3>
              <p className="mt-2 text-sm text-slate-600">Office address: {job.officeAddress || "Not specified"}</p>
              <p className="mt-1 text-sm text-slate-600">
                Provides housing: {job.providesHousing ? "Yes" : "No"}
              </p>
            </div>
          </section>

          <section className="rounded-3xl bg-slate-950 p-6 text-white">
            <div className="flex flex-wrap items-center justify-between gap-4">
              <div>
                <p className="text-sm uppercase tracking-[0.25em] text-slate-300">Application panel</p>
                <p className="mt-2 text-lg font-semibold">Ready to go for this opportunity?</p>
              </div>
              {user?.role === "APPLICANT" ? <StatusBadge status="PENDING" /> : null}
            </div>

            <div className="mt-4 grid gap-4 sm:grid-cols-[1fr_auto]">
              <input
                className="input border-slate-800 bg-white text-slate-900"
                placeholder="Enter phone number"
                value={phoneNumber}
                onChange={(event) => setPhoneNumber(event.target.value)}
              />
              <button type="button" className="btn-primary" onClick={handleApply}>
                Apply now
              </button>
            </div>

            {message ? <p className="mt-4 text-sm text-emerald-300">{message}</p> : null}
            {error ? <p className="mt-4 text-sm text-rose-300">{error}</p> : null}
          </section>
        </div>
      </div>
    </div>
  );
}
