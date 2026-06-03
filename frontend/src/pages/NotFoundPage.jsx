import { Link } from "react-router-dom";

export default function NotFoundPage() {
  return (
    <div className="flex min-h-screen items-center justify-center bg-slate-50 px-4">
      <div className="panel max-w-lg p-8 text-center">
        <p className="text-sm font-semibold uppercase tracking-[0.25em] text-brand-700">404</p>
        <h1 className="mt-3 text-3xl font-black text-slate-950">Page not found</h1>
        <p className="mt-4 text-slate-600">The page you tried to open does not exist or has moved.</p>
        <Link to="/" className="btn-primary mt-6">
          Go home
        </Link>
      </div>
    </div>
  );
}
