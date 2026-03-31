# Postman Setup

This project now includes ready-to-import Postman files in [`postman/`](C:\Users\ogunw\OneDrive\Documents\JOBPROJ\postman).

Files:

- `postman/JobPortal.postman_collection.json`
- `postman/JobPortal.local.postman_environment.json`

How to use them:

1. Start the Spring Boot API on port `8080`.
2. Open Postman and import both files.
3. Select the `JobPortal Local` environment.
4. Run the register requests you need in the `Auth` folder.
5. Run `Login Applicant`, `Login Employer`, and/or `Login Admin`.

What happens automatically:

- Each role login saves its own token:
  - `applicantToken`
  - `employerToken`
  - `adminToken`
- Role folders automatically copy the correct role token into `currentToken`.
- Login requests also save role-specific user IDs and emails.
- Employer login also saves `companyId` and `companyName`.
- `Create Job` saves `jobId`.
- `Apply To Job` saves `applicationId`.
- Each register request now generates fresh values automatically to avoid collisions:
  - applicant email
  - employer email
  - employer company ID/name
  - admin email
- Each successful register request also updates the matching login email variable, so the next login request for that role uses the newly created account automatically.

Suggested test flow:

1. Register Employer
2. Login Employer
3. Create Job
4. Register Applicant
5. Login Applicant
6. Apply To Job
7. Open the `Employer` folder and run employer-only requests
8. Get My Company Applications
9. Update My Company Application Status

Notes:

- Public endpoints are `GET /api/jobs` and `GET /api/jobs/search`.
- Protected endpoints use Bearer auth with `{{currentToken}}`.
- You usually do not have to switch tokens manually after login because the `Applicant`, `Employer`, and `Admin` folders set the correct token automatically.
- After deployment, the main thing you normally change is `baseUrl`.
- Employer registration requires both `companyId` and `companyName`.
- Valid application status values are `PENDING`, `REVIEWED`, `ACCEPTED`, and `REJECTED`.
