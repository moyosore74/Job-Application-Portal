import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import JobCard from "../components/JobCard";
import { fetchJobs } from "../services/jobService";

export default function LandingPage() {
  const [jobs, setJobs] = useState([]);

  useEffect(() => {
    fetchJobs()
      .then((data) => setJobs(data.slice(0, 3)))
      .catch(() => setJobs([]));
  }, []);

  return (
    <div className="pb-20">
      <section className="mx-auto grid max-w-7xl gap-10 px-4 py-16 sm:px-6 lg:grid-cols-[1.1fr_0.9fr] lg:px-8 lg:py-24">
        <div className="space-y-8">
          <span className="inline-flex rounded-full bg-brand-100 px-4 py-2 text-sm font-semibold text-brand-800">
            Find your next opportunity
          </span>
          <div className="space-y-5">
            <h1 className="max-w-3xl text-5xl font-black tracking-tight text-slate-950 sm:text-6xl">
              Discover jobs, hire faster, and manage applications in one polished workspace.
            </h1>
            <p className="max-w-2xl text-lg leading-8 text-slate-600">
              A modern hiring hub for applicants, employers, and administrators. Browse openings, track progress,
              and keep recruiting workflows crisp and organized.
            </p>
          </div>

          <div className="panel flex flex-col gap-4 p-4 sm:flex-row">
            <input className="input flex-1" placeholder="Search roles, companies, or job IDs" />
            <Link to="/jobs" className="btn-primary">
              Browse jobs
            </Link>
          </div>

          <div className="flex flex-wrap gap-3">
            <Link to="/jobs" className="btn-primary">
              Browse Jobs
            </Link>
            <Link to="/login" className="btn-secondary">
              Sign In
            </Link>
            <Link to="/register" className="btn-secondary">
              Register
            </Link>
          </div>
        </div>

        <div className="panel relative overflow-hidden p-8">
          <div className="absolute inset-0 bg-gradient-to-br from-brand-50 via-white to-emerald-50" />
          <div className="relative grid gap-6">
            <div className="rounded-3xl bg-slate-950 p-6 text-white">
              <p className="text-sm uppercase tracking-[0.25em] text-slate-300">Live hiring pulse</p>
              <div className="mt-4 grid grid-cols-2 gap-4">
                <div>
                  <p className="text-3xl font-black">3 roles</p>
                  <p className="text-sm text-slate-300">Featured now</p>
                </div>
                <div>
                  <p className="text-3xl font-black">3 roles</p>
                  <p className="text-sm text-slate-300">Public previews</p>
                </div>
              </div>
            </div>
            <div className="rounded-3xl border border-slate-200 bg-white p-6">
              <p className="text-sm font-semibold text-slate-500">Built for every role</p>
              <ul className="mt-4 space-y-3 text-sm text-slate-700">
                <li>Applicants can browse jobs and track outcomes.</li>
                <li>Employers can publish openings and review candidates.</li>
                <li>Admins can oversee the entire application pipeline.</li>
              </ul>
            </div>
          </div>
        </div>
      </section>

      <section className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
        <div className="mb-8 flex items-end justify-between">
          <div>
            <p className="text-sm font-semibold uppercase tracking-[0.25em] text-brand-700">Featured openings</p>
            <h2 className="mt-2 text-3xl font-bold text-slate-950">Fresh opportunities worth a closer look</h2>
          </div>
          <Link to="/jobs" className="text-sm font-semibold text-brand-700">
            See all jobs
          </Link>
        </div>

        <div className="grid gap-6 lg:grid-cols-3">
          {jobs.map((job) => (
            <JobCard key={job.jobId} job={job} />
          ))}
        </div>
      </section>
    </div>
  );
}
