import { Link } from "react-router-dom";
import { formatCurrency, formatDate } from "../utils/formatters";

export default function JobCard({ job, action }) {
  return (
    <article className="panel flex h-full flex-col p-6">
      <div className="mb-5 flex items-start justify-between gap-4">
        <div>
          <p className="text-xs font-semibold uppercase tracking-[0.25em] text-brand-700">{job.companyName}</p>
          <h3 className="mt-2 text-xl font-bold text-slate-900">{job.jobTitle}</h3>
        </div>
        <span className="rounded-full bg-slate-100 px-3 py-1 text-xs font-semibold text-slate-700">
          {job.jobType}
        </span>
      </div>

      <div className="grid gap-3 text-sm text-slate-600 sm:grid-cols-2">
        <p>
          <span className="font-semibold text-slate-900">Location:</span> {job.location}
        </p>
        <p>
          <span className="font-semibold text-slate-900">Salary:</span> {formatCurrency(job.salary)}
        </p>
        <p>
          <span className="font-semibold text-slate-900">Experience:</span> {job.experienceLevel}
        </p>
        <p>
          <span className="font-semibold text-slate-900">Posted:</span> {formatDate(job.uploadedDate)}
        </p>
      </div>

      <p className="mt-5 flex-1 text-sm leading-6 text-slate-600">{job.description}</p>

      <div className="mt-6 flex flex-wrap gap-3">
        <Link to={`/jobs/${job.jobId}`} className="btn-primary">
          View details
        </Link>
        {action}
      </div>
    </article>
  );
}
