import { useEffect, useMemo, useState } from "react";
import DashboardLayout from "../components/DashboardLayout";
import StatusBadge from "../components/StatusBadge";
import { fetchEmployerApplications, updateEmployerApplicationStatus } from "../services/applicationService";
import { createJob, fetchEmployerJobs } from "../services/jobService";
import { formatCurrency, formatDate } from "../utils/formatters";

const links = [
  { to: "/employer/dashboard", label: "Overview" },
  { to: "/jobs", label: "Public jobs" }
];

const initialJobForm = {
  jobTitle: "",
  description: "",
  location: "",
  jobType: "REMOTE",
  requiredSkills: "",
  experienceLevel: "",
  salary: "",
  requiresInternet: true,
  workHours: "",
  officeAddress: "",
  providesHousing: false
};

export default function EmployerDashboardPage() {
  const [jobForm, setJobForm] = useState(initialJobForm);
  const [jobs, setJobs] = useState([]);
  const [applications, setApplications] = useState([]);
  const [message, setMessage] = useState("");

  const loadData = () => {
    fetchEmployerJobs().then(setJobs);
    fetchEmployerApplications().then(setApplications);
  };

  useEffect(() => {
    loadData();
  }, []);

  const isRemote = useMemo(() => jobForm.jobType.toUpperCase().includes("REMOTE"), [jobForm.jobType]);

  const handleCreateJob = async (event) => {
    event.preventDefault();
    setMessage("");

    try {
      await createJob({
        ...jobForm,
        salary: Number(jobForm.salary)
      });
      setJobForm(initialJobForm);
      setMessage("Job created successfully.");
      loadData();
    } catch (error) {
      setMessage(error.response?.data?.message || "Unable to create job.");
    }
  };

  const handleStatusUpdate = async (applicationId, status) => {
    await updateEmployerApplicationStatus(applicationId, status);
    loadData();
  };

  return (
    <DashboardLayout title="Employer" subtitle="Manage your hiring" links={links}>
      <section className="grid gap-6 xl:grid-cols-[1.15fr_0.85fr]">
        <form className="panel p-6" onSubmit={handleCreateJob}>
          <div className="mb-6">
            <p className="text-sm font-semibold uppercase tracking-[0.25em] text-brand-700">Create job</p>
            <h2 className="mt-2 text-2xl font-bold text-slate-950">Post a new opening</h2>
          </div>

          <div className="grid gap-4 md:grid-cols-2">
            <input className="input md:col-span-2" placeholder="Job title" value={jobForm.jobTitle} onChange={(e) => setJobForm((c) => ({ ...c, jobTitle: e.target.value }))} />
            <textarea className="input md:col-span-2 min-h-32" placeholder="Description" value={jobForm.description} onChange={(e) => setJobForm((c) => ({ ...c, description: e.target.value }))} />
            <input className="input" placeholder="Location" value={jobForm.location} onChange={(e) => setJobForm((c) => ({ ...c, location: e.target.value }))} />
            <select className="input" value={jobForm.jobType} onChange={(e) => setJobForm((c) => ({ ...c, jobType: e.target.value }))}>
              <option value="REMOTE">Remote</option>
              <option value="ONSITE">Onsite</option>
            </select>
            <input className="input" placeholder="Required skills" value={jobForm.requiredSkills} onChange={(e) => setJobForm((c) => ({ ...c, requiredSkills: e.target.value }))} />
            <input className="input" placeholder="Experience level" value={jobForm.experienceLevel} onChange={(e) => setJobForm((c) => ({ ...c, experienceLevel: e.target.value }))} />
            <input className="input" type="number" placeholder="Salary" value={jobForm.salary} onChange={(e) => setJobForm((c) => ({ ...c, salary: e.target.value }))} />

            {isRemote ? (
              <>
                <input className="input" placeholder="Work hours" value={jobForm.workHours} onChange={(e) => setJobForm((c) => ({ ...c, workHours: e.target.value }))} />
                <label className="flex items-center gap-3 rounded-2xl border border-slate-200 px-4 py-3 text-sm text-slate-700">
                  <input type="checkbox" checked={jobForm.requiresInternet} onChange={(e) => setJobForm((c) => ({ ...c, requiresInternet: e.target.checked }))} />
                  Requires internet
                </label>
              </>
            ) : (
              <>
                <input className="input" placeholder="Office address" value={jobForm.officeAddress} onChange={(e) => setJobForm((c) => ({ ...c, officeAddress: e.target.value }))} />
                <label className="flex items-center gap-3 rounded-2xl border border-slate-200 px-4 py-3 text-sm text-slate-700">
                  <input type="checkbox" checked={jobForm.providesHousing} onChange={(e) => setJobForm((c) => ({ ...c, providesHousing: e.target.checked }))} />
                  Provides housing
                </label>
              </>
            )}
          </div>

          {message ? <div className="mt-4 rounded-2xl bg-slate-100 p-4 text-sm text-slate-700">{message}</div> : null}

          <button className="btn-primary mt-6">Create job</button>
        </form>

        <section className="panel p-6">
          <div className="mb-6">
            <p className="text-sm font-semibold uppercase tracking-[0.25em] text-brand-700">My jobs</p>
            <h2 className="mt-2 text-2xl font-bold text-slate-950">Published openings</h2>
          </div>
          <div className="space-y-4">
            {jobs.map((job) => (
              <div key={job.jobId} className="rounded-3xl border border-slate-200 p-5">
                <div className="flex items-start justify-between gap-4">
                  <div>
                    <h3 className="font-bold text-slate-900">{job.jobTitle}</h3>
                    <p className="text-sm text-slate-600">{job.location}</p>
                  </div>
                  <span className="rounded-full bg-slate-100 px-3 py-1 text-xs font-semibold text-slate-700">
                    {job.jobType}
                  </span>
                </div>
                <div className="mt-3 grid gap-2 text-sm text-slate-600">
                  <p>{formatCurrency(job.salary)}</p>
                  <p>{formatDate(job.uploadedDate)}</p>
                </div>
              </div>
            ))}
          </div>
        </section>
      </section>

      <section className="panel p-6">
        <div className="mb-6">
          <p className="text-sm font-semibold uppercase tracking-[0.25em] text-brand-700">Applications</p>
          <h2 className="mt-2 text-2xl font-bold text-slate-950">Candidate pipeline</h2>
        </div>

        <div className="overflow-x-auto">
          <table className="min-w-full text-left text-sm">
            <thead className="text-slate-500">
              <tr>
                <th className="pb-3 pr-4">Applicant</th>
                <th className="pb-3 pr-4">Job</th>
                <th className="pb-3 pr-4">Phone</th>
                <th className="pb-3 pr-4">Status</th>
                <th className="pb-3">Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-200">
              {applications.map((application) => (
                <tr key={application.applicationId}>
                  <td className="py-4 pr-4">
                    <p className="font-semibold text-slate-900">{application.applicantName}</p>
                    <p className="text-slate-500">{application.applicantEmail}</p>
                  </td>
                  <td className="py-4 pr-4 text-slate-700">{application.jobTitle}</td>
                  <td className="py-4 pr-4 text-slate-700">{application.phoneNumber}</td>
                  <td className="py-4 pr-4">
                    <StatusBadge status={application.status} />
                  </td>
                  <td className="py-4">
                    <select
                      className="input min-w-36"
                      defaultValue={application.status}
                      onChange={(event) => handleStatusUpdate(application.applicationId, event.target.value)}
                    >
                      <option value="PENDING">PENDING</option>
                      <option value="REVIEWED">REVIEWED</option>
                      <option value="ACCEPTED">ACCEPTED</option>
                      <option value="REJECTED">REJECTED</option>
                    </select>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    </DashboardLayout>
  );
}
