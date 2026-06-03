# Frontend

React + Vite frontend for the Job Application Portal.

## Stack

- React
- Vite
- Tailwind CSS
- Axios
- React Router

## Setup

```bash
cd frontend
npm install
npm run dev
```

Default dev URL:

```text
http://localhost:3000
```

## Backend API

The frontend is preconfigured for a local Spring Boot backend:

```text
http://localhost:8080
```

Run the backend first, then run the frontend. During development, Vite proxies `/api` requests to `http://localhost:8080`.

## Mock Mode

Mock data is still available if the backend is offline. To enable it, create a `.env` file in this folder:

```text
VITE_USE_MOCKS=true
```

Seed accounts:

```text
Applicant: applicant@test.com / Password123!
Employer: employer@test.com / Password123!
Admin: admin@test.com / Password123!
```

To point the frontend at a different backend, set:

```text
VITE_API_BASE_URL=https://your-backend-url
```

## App Structure

```text
frontend/
  src/
    components/
    hooks/
    pages/
    services/
    utils/
```
