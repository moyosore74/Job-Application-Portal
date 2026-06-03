import { NavLink } from "react-router-dom";

export default function Sidebar({ title, subtitle, links }) {
  return (
    <aside className="panel h-fit w-full p-4 lg:sticky lg:top-6 lg:w-72">
      <div className="mb-6">
        <p className="text-xs font-semibold uppercase tracking-[0.3em] text-brand-700">{title}</p>
        <h2 className="mt-2 text-2xl font-bold text-slate-900">{subtitle}</h2>
      </div>

      <nav className="space-y-2">
        {links.map((link) => (
          <NavLink
            key={link.to}
            to={link.to}
            className={({ isActive }) =>
              `flex rounded-2xl px-4 py-3 text-sm font-medium transition ${
                isActive
                  ? "bg-brand-700 text-white"
                  : "text-slate-600 hover:bg-slate-100 hover:text-slate-900"
              }`
            }
          >
            {link.label}
          </NavLink>
        ))}
      </nav>
    </aside>
  );
}
