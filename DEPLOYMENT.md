# Deployment Notes

Environment variables:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION_MS`
- `PORT`
- `JPA_DDL_AUTO`
- `JPA_SHOW_SQL`
- `CORS_ALLOWED_ORIGINS`

Recommended production values:

- Set a long random `JWT_SECRET`.
- Set `JPA_DDL_AUTO=validate` after your schema is stable.
- Set `JPA_SHOW_SQL=false`.
- Set `CORS_ALLOWED_ORIGINS` to your deployed frontend origin(s).

Pre-deploy checklist:

1. Configure a production MySQL database.
2. Apply the same schema with the new unique application constraint.
3. Set the environment variables above in your hosting platform.
4. Run the test suite in a network-enabled environment.
5. Import the updated Postman collection and environment for smoke tests.

Notes:

- IDs now use random prefixed values such as `USER...`, `JOB...`, and `APP...` to avoid collisions under concurrent traffic.
- Error responses now return JSON bodies for validation, conflict, unauthorized, and forbidden cases.
- CORS is enabled through configuration rather than hardcoded origins.
