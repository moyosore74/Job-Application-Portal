import { useEffect, useState } from "react";
import DashboardLayout from "../components/DashboardLayout";
import StatusBadge from "../components/StatusBadge";
import { fetchApplicantApplications } from "../services/applicationService";
import { formatDate } from "../utils/formatters";

const links = [
  { to: "/applicant/dashboard", label: "My applications" },
  { to: "/jobs", label: "Browse jobs" }
];

export default function ApplicantDashboardPage() {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchApplicantApplications()
      .then(setApplications)
      .finally(() => setLoading(false));
  }, []);

  return (
    <DashboardLayout title="Applicant" subtitle="Track your progress" links={links}>
      <section className="panel p-6">
        <div className="mb-6">
          <p className="text-sm font-semibold uppercase tracking-[0.25em] text-brand-700">Applications</p>
          <h2 className="mt-2 text-2xl font-bold text-slate-950">Jobs you have applied to</h2>
        </div>

        {loading ? (
          <p className="text-slate-500">Loading your applications...</p>
        ) : (
          <div className="space-y-4">
            {applications.map((application) => (
              <div
                key={application.applicationId}
                className="rounded-3xl border border-slate-200 bg-slate-50 p-5"
              >
                <div className="flex flex-wrap items-start justify-between gap-4">
                  <div>
                    <h3 className="text-lg font-bold text-slate-900">{application.jobTitle}</h3>
                    <p className="text-sm text-slate-600">{application.companyName}</p>
                  </div>
                  <StatusBadge status={application.status} />
                </div>
                <div className="mt-4 grid gap-2 text-sm text-slate-600 sm:grid-cols-2">
                  <p><span className="font-semibold text-slate-900">Applied:</span> {formatDate(application.applicationDate)}</p>
                  <p><span className="font-semibold text-slate-900">Phone:</span> {application.phoneNumber}</p>
                </div>
              </div>
            ))}
          </div>
        )}
      </section>
    </DashboardLayout>
  );
}
