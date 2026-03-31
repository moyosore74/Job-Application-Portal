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
- `GET /api/jobs`
- `GET /api/jobs/search`
- `POST /api/jobs`
- `POST /api/applications`
- `PATCH /api/applications/{applicationId}/status`

## Postman

Import:

- `postman/JobPortal.postman_collection.json`
- `postman/JobPortal.local.postman_environment.json`

See [POSTMAN.md](C:\Users\ogunw\OneDrive\Documents\JOBPROJ\POSTMAN.md) for the request flow.

## Deployment

See [DEPLOYMENT.md](C:\Users\ogunw\OneDrive\Documents\JOBPROJ\DEPLOYMENT.md) for environment variables and deployment notes.

## Testing

Integration tests live under:

- `src/test/java`

Run with Maven:

```bash
mvn test
```

## Notes

- IDs are generated as random prefixed values like `USER...`, `JOB...`, and `APP...`.
- Duplicate applications are blocked at both the service and database-constraint level.
- CORS origins are configurable through environment variables.
