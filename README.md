# Job Application Portal

Spring Boot backend for a job application portal with JWT authentication, role-based access control, job posting, and application management.

## Features

- User registration and login
- Roles: `ADMIN`, `EMPLOYER`, `APPLICANT`
- JWT-based authentication
- Job creation, listing, search, and deletion
- Application submission and status updates
- Postman collection for local and deployment smoke tests
- JSON error responses for common failure cases

## Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- Postman

## Local Run

Set environment variables in your IDE or terminal:

```text
DB_URL=jdbc:mysql://localhost:3306/JobApplicationPortal
DB_USERNAME=root
DB_PASSWORD=
JWT_SECRET=your-long-secret
CORS_ALLOWED_ORIGINS=http://localhost:3000
```

Then start `com.jobproj.jobportal.JobPortalApplication`.

Default local port:

```text
http://localhost:8080
```

## API Areas

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/me`
- `GET /api/jobs`
- `GET /api/jobs/{jobId}`
- `GET /api/jobs/search`
- `POST /api/jobs`
- `DELETE /api/jobs/{jobId}`
- `POST /api/applications`
- `PATCH /api/applications/{applicationId}/status`

## Postman

Import:

- `postman/JobPortal.postman_collection.json`
- `postman/JobPortal.local.postman_environment.json`

See [POSTMAN.md](POSTMAN.md) for the request flow.

## Deployment

See [DEPLOYMENT.md](DEPLOYMENT.md) for environment variables and deployment notes.

Current Railway deployment:

```text
https://job-application-portal-production.up.railway.app
```

## Frontend Handoff

Base URL:

```text
https://job-application-portal-production.up.railway.app
```

Use Bearer auth for protected routes:

```text
Authorization: Bearer <token>
```

Role flows:

- `APPLICANT`: register/login, browse jobs, apply to jobs, track application status
- `EMPLOYER`: register/login, create jobs, view company applications, update application status
- `ADMIN`: login, review and manage applications platform-wide

Useful endpoints:

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/me`
- `GET /api/jobs`
- `GET /api/jobs/{jobId}`
- `GET /api/jobs/search?value=...`
- `POST /api/jobs`
- `DELETE /api/jobs/{jobId}`
- `GET /api/jobs/company/me`
- `POST /api/applications`
- `GET /api/applications/applicant/me`
- `GET /api/applications/company/me`
- `PATCH /api/applications/company/me/{applicationId}/status`
- `GET /api/applications`
- `PATCH /api/applications/{applicationId}/status`

### Local Demo Jobs

Demo jobs are available only when the backend runs with the `local` Spring profile. They are intended for frontend development and API connection testing, not production deployment.

Enable them locally:

```text
SPRING_PROFILES_ACTIVE=local
```

Then start the backend and call:

```text
GET http://localhost:8080/api/jobs
```

Known demo records for frontend testing:

- `JOB-DEMO-001` - Frontend Developer at BrightPath Technologies
- `JOB-DEMO-002` - Backend Engineer at BrightPath Technologies
- `JOB-DEMO-003` - Data Analyst at Lagoon Health Systems
- `JOB-DEMO-004` - Product Manager at CivicPay Africa
- `JOB-DEMO-005` - Customer Support Specialist at CivicPay Africa

Search/detail example:

```text
GET http://localhost:8080/api/jobs/search?value=JOB-DEMO-001
```

## Suggested Frontend Pages

- Landing page
- Jobs listing page
- Job details page
- Login page
- Register page
- Applicant dashboard
- Employer dashboard
- Admin dashboard

## Suggested Design Direction

- Use a clean recruiting-platform layout with a light background and one strong accent color
- Keep public pages simple: hero section, job search, job cards, clear auth calls to action
- Use dashboard sidebars for authenticated roles
- Show job and application data with cards or clean tables
- Use status badges for `PENDING`, `REVIEWED`, `ACCEPTED`, and `REJECTED`
- Make the experience responsive from the start for mobile and desktop

## Testing

Integration tests live under:

- `src/test/java`

Run with Maven:

```bash
mvn test
```

## Notes

- IDs are generated as random prefixed values like `USER...`, `JOB...`, and `APP...`.
- Job payloads support `currency` alongside `salary`; if omitted, the API defaults to `NGN`.
- Duplicate applications are blocked at both the service and database-constraint level.
- CORS origins are configurable through environment variables.
