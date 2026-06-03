export function getDashboardPath(role) {
  switch (role) {
    case "APPLICANT":
      return "/applicant/dashboard";
    case "EMPLOYER":
      return "/employer/dashboard";
    case "ADMIN":
      return "/admin/dashboard";
    default:
      return "/";
  }
}
