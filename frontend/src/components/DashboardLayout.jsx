import Sidebar from "./Sidebar";
import { useAuth } from "../hooks/useAuth";

export default function DashboardLayout({ title, subtitle, links, children }) {
  const { user, logout } = useAuth();

  return (
    <div className="min-h-screen bg-slate-50 px-4 py-6 sm:px-6 lg:px-8">
      <div className="mx-auto flex max-w-7xl flex-col gap-6 lg:flex-row">
        <Sidebar title={title} subtitle={subtitle} links={links} />

        <main className="min-w-0 flex-1 space-y-6">
          <section className="panel flex flex-col gap-4 p-6 sm:flex-row sm:items-center sm:justify-between">
            <div>
              <p className="text-sm text-slate-500">Signed in as</p>
              <h1 className="text-2xl font-bold text-slate-900">{user?.fullName}</h1>
              <p className="text-sm text-slate-600">
                {user?.role} {user?.companyName ? `· ${user.companyName}` : ""}
              </p>
            </div>
            <button type="button" onClick={logout} className="btn-secondary">
              Log out
            </button>
          </section>

          {children}
        </main>
      </div>
    </div>
  );
}
