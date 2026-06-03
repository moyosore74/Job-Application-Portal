import { useEffect, useState } from "react";
import DashboardLayout from "../components/DashboardLayout";
import StatusBadge from "../components/StatusBadge";
import { fetchAllApplications, updateAdminApplicationStatus } from "../services/applicationService";
import { formatDate } from "../utils/formatters";

const links = [
  { to: "/admin/dashboard", label: "Application oversight" },
  { to: "/jobs", label: "Public jobs" }
];

export default function AdminDashboardPage() {
  const [applications, setApplications] = useState([]);

  const loadApplications = () => {
    fetchAllApplications().then(setApplications);
  };

  useEffect(() => {
    loadApplications();
  }, []);

  const handleStatusUpdate = async (applicationId, status) => {
    await updateAdminApplicationStatus(applicationId, status);
    loadApplications();
  };

  return (
    <DashboardLayout title="Admin" subtitle="Platform oversight" links={links}>
      <section className="panel p-6">
        <div className="mb-6">
          <p className="text-sm font-semibold uppercase tracking-[0.25em] text-brand-700">All applications</p>
          <h2 className="mt-2 text-2xl font-bold text-slate-950">Review the full hiring pipeline</h2>
        </div>

        <div className="overflow-x-auto">
          <table className="min-w-full text-left text-sm">
            <thead className="text-slate-500">
              <tr>
                <th className="pb-3 pr-4">Applicant</th>
                <th className="pb-3 pr-4">Company</th>
                <th className="pb-3 pr-4">Job</th>
                <th className="pb-3 pr-4">Applied</th>
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
                  <td className="py-4 pr-4 text-slate-700">{application.companyName}</td>
                  <td className="py-4 pr-4 text-slate-700">{application.jobTitle}</td>
                  <td className="py-4 pr-4 text-slate-700">{formatDate(application.applicationDate)}</td>
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
