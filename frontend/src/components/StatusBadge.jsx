const palette = {
  PENDING: "bg-amber-100 text-amber-800",
  REVIEWED: "bg-sky-100 text-sky-800",
  ACCEPTED: "bg-emerald-100 text-emerald-800",
  REJECTED: "bg-rose-100 text-rose-800"
};

export default function StatusBadge({ status }) {
  const normalized = String(status || "PENDING").toUpperCase();

  return <span className={`badge ${palette[normalized] || "bg-slate-100 text-slate-700"}`}>{normalized}</span>;
}
