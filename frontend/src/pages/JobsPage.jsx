import { useEffect, useMemo, useState } from "react";
import JobCard from "../components/JobCard";
import { fetchJobs, searchJobs } from "../services/jobService";

export default function JobsPage() {
  const [jobs, setJobs] = useState([]);
  const [query, setQuery] = useState("");
  const [filters, setFilters] = useState({ location: "", jobType: "", experienceLevel: "" });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchJobs()
      .then((data) => setJobs(data))
      .catch(() => setError("Unable to load jobs right now."))
      .finally(() => setLoading(false));
  }, []);

  const visibleJobs = useMemo(() => {
    return jobs.filter((job) => {
      const matchesLocation =
        !filters.location || job.location.toLowerCase().includes(filters.location.toLowerCase());
      const matchesType = !filters.jobType || job.jobType.toLowerCase() === filters.jobType.toLowerCase();
      const matchesExperience =
        !filters.experienceLevel ||
        job.experienceLevel.toLowerCase().includes(filters.experienceLevel.toLowerCase());

      return matchesLocation && matchesType && matchesExperience;
    });
  }, [filters, jobs]);

  const handleSearch = async (event) => {
    event.preventDefault();

    if (!query.trim()) {
      setLoading(true);
      setError("");
      fetchJobs()
        .then((data) => setJobs(data))
        .catch(() => setError("Unable to reload jobs."))
        .finally(() => setLoading(false));
      return;
    }

    setLoading(true);
    setError("");

    try {
      const data = await searchJobs(query.trim());
      setJobs(data);
    } catch {
      setJobs([]);
      setError("No jobs matched your search.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="mx-auto max-w-7xl px-4 py-10 sm:px-6 lg:px-8">
      <div className="mb-8 space-y-3">
        <p className="text-sm font-semibold uppercase tracking-[0.25em] text-brand-700">Explore openings</p>
        <h1 className="text-4xl font-black tracking-tight text-slate-950">Browse roles across the portal</h1>
      </div>

      <section className="panel mb-8 p-4">
        <form className="grid gap-4 lg:grid-cols-[2fr_1fr_1fr_1fr_auto]" onSubmit={handleSearch}>
          <input
            className="input"
            value={query}
            onChange={(event) => setQuery(event.target.value)}
            placeholder="Search by title or job ID"
          />
          <input
            className="input"
            value={filters.location}
            onChange={(event) => setFilters((current) => ({ ...current, location: event.target.value }))}
            placeholder="Location"
          />
          <select
            className="input"
            value={filters.jobType}
            onChange={(event) => setFilters((current) => ({ ...current, jobType: event.target.value }))}
          >
            <option value="">All job types</option>
            <option value="REMOTE">Remote</option>
            <option value="ONSITE">Onsite</option>
            <option value="HYBRID">Hybrid</option>
            <option value="Full-time">Full-time</option>
          </select>
          <input
            className="input"
            value={filters.experienceLevel}
            onChange={(event) => setFilters((current) => ({ ...current, experienceLevel: event.target.value }))}
            placeholder="Experience level"
          />
          <button type="submit" className="btn-primary">
            Search
          </button>
        </form>
      </section>

      {error ? <div className="mb-6 rounded-2xl bg-rose-50 p-4 text-sm text-rose-700">{error}</div> : null}

      {loading ? (
        <div className="panel p-10 text-center text-slate-500">Loading jobs...</div>
      ) : (
        <div className="grid gap-6 lg:grid-cols-2">
          {visibleJobs.map((job) => (
            <JobCard key={job.jobId} job={job} />
          ))}
        </div>
      )}
    </div>
  );
}
