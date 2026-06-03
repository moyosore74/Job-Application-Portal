const STORE_KEY = "job_portal_mock_store";
const LATENCY_MS = 250;

const seed = {
  users: [
    {
      userId: "USER_ADMIN_DEMO",
      fullName: "Admin Demo",
      email: "admin@test.com",
      password: "Password123!",
      role: "ADMIN"
    },
    {
      userId: "USER_EMPLOYER_DEMO",
      fullName: "Maya Employer",
      email: "employer@test.com",
      password: "Password123!",
      role: "EMPLOYER",
      companyId: "COMP_BLUEWAVE",
      companyName: "BlueWave Digital"
    },
    {
      userId: "USER_APPLICANT_DEMO",
      fullName: "Tobi Applicant",
      email: "applicant@test.com",
      password: "Password123!",
      role: "APPLICANT"
    }
  ],
  jobs: [
    {
      jobId: "JOB_FRONTEND_01",
      companyId: "COMP_BLUEWAVE",
      companyName: "BlueWave Digital",
      jobTitle: "Frontend React Developer",
      description:
        "Build responsive dashboards, polish job listing experiences, and work closely with product teams to improve hiring workflows.",
      location: "Lagos",
      jobType: "REMOTE",
      requiredSkills: "React, Tailwind CSS, Axios, REST APIs",
      experienceLevel: "Intermediate",
      salary: 450000,
      requiresInternet: true,
      workHours: "9 AM - 5 PM",
      officeAddress: "",
      providesHousing: false,
      uploadedDate: "2026-05-30"
    },
    {
      jobId: "JOB_BACKEND_02",
      companyId: "COMP_NEXTHIRE",
      companyName: "NextHire Labs",
      jobTitle: "Spring Boot Backend Engineer",
      description:
        "Own API endpoints, improve authentication flows, and maintain database-backed services for a growing recruitment platform.",
      location: "Abuja",
      jobType: "ONSITE",
      requiredSkills: "Java, Spring Boot, MySQL, JWT",
      experienceLevel: "Senior",
      salary: 700000,
      requiresInternet: false,
      workHours: "",
      officeAddress: "12 Aminu Kano Crescent, Abuja",
      providesHousing: true,
      uploadedDate: "2026-05-28"
    },
    {
      jobId: "JOB_PRODUCT_03",
      companyId: "COMP_BLUEWAVE",
      companyName: "BlueWave Digital",
      jobTitle: "Product Designer",
      description:
        "Design clean applicant and employer experiences, prototype dashboard flows, and refine the design system.",
      location: "Remote",
      jobType: "REMOTE",
      requiredSkills: "Figma, UX Research, Design Systems",
      experienceLevel: "Mid-level",
      salary: 520000,
      requiresInternet: true,
      workHours: "Flexible",
      officeAddress: "",
      providesHousing: false,
      uploadedDate: "2026-05-27"
    },
    {
      jobId: "JOB_DATA_04",
      companyId: "COMP_METRICS",
      companyName: "MetricsWorks",
      jobTitle: "Data Analyst",
      description:
        "Track application conversion, prepare hiring reports, and turn recruitment data into clear operational insight.",
      location: "Ibadan",
      jobType: "HYBRID",
      requiredSkills: "SQL, Excel, Power BI, Communication",
      experienceLevel: "Entry-level",
      salary: 300000,
      requiresInternet: true,
      workHours: "10 AM - 4 PM",
      officeAddress: "Ring Road, Ibadan",
      providesHousing: false,
      uploadedDate: "2026-05-26"
    }
  ],
  applications: [
    {
      applicationId: "APP_DEMO_01",
      applicantUserId: "USER_APPLICANT_DEMO",
      applicantName: "Tobi Applicant",
      applicantEmail: "applicant@test.com",
      jobId: "JOB_FRONTEND_01",
      jobTitle: "Frontend React Developer",
      companyId: "COMP_BLUEWAVE",
      companyName: "BlueWave Digital",
      phoneNumber: "08012345678",
      applicationDate: "2026-06-01",
      status: "PENDING"
    },
    {
      applicationId: "APP_DEMO_02",
      applicantUserId: "USER_APPLICANT_DEMO",
      applicantName: "Tobi Applicant",
      applicantEmail: "applicant@test.com",
      jobId: "JOB_PRODUCT_03",
      jobTitle: "Product Designer",
      companyId: "COMP_BLUEWAVE",
      companyName: "BlueWave Digital",
      phoneNumber: "08012345678",
      applicationDate: "2026-05-31",
      status: "REVIEWED"
    }
  ]
};

function delay(value) {
  return new Promise((resolve) => {
    setTimeout(() => resolve(value), LATENCY_MS);
  });
}

function loadStore() {
  const raw = localStorage.getItem(STORE_KEY);
  if (!raw) {
    localStorage.setItem(STORE_KEY, JSON.stringify(seed));
    return structuredClone(seed);
  }

  try {
    return JSON.parse(raw);
  } catch {
    localStorage.setItem(STORE_KEY, JSON.stringify(seed));
    return structuredClone(seed);
  }
}

function saveStore(store) {
  localStorage.setItem(STORE_KEY, JSON.stringify(store));
}

function publicUser(user) {
  const { password, ...safeUser } = user;
  return safeUser;
}

function createToken(user) {
  return `mock-token-${user.role.toLowerCase()}-${user.userId}`;
}

function getCurrentUser() {
  const raw = localStorage.getItem("job_portal_user");
  return raw ? JSON.parse(raw) : null;
}

function nextId(prefix) {
  return `${prefix}_${Math.random().toString(36).slice(2, 10).toUpperCase()}`;
}

function normalize(value) {
  return String(value || "").trim().toLowerCase();
}

export const mockApi = {
  async registerUser(payload) {
    const store = loadStore();
    const email = normalize(payload.email);

    if (store.users.some((user) => normalize(user.email) === email)) {
      throw new Error("Email already exists");
    }

    if (payload.role === "EMPLOYER" && (!payload.companyId || !payload.companyName)) {
      throw new Error("Employer registration requires companyId and companyName");
    }

    const user = {
      userId: nextId("USER"),
      fullName: payload.fullName,
      email: payload.email.trim(),
      password: payload.password,
      role: payload.role,
      companyId: payload.companyId || undefined,
      companyName: payload.companyName || undefined
    };

    store.users.push(user);
    saveStore(store);
    return delay(publicUser(user));
  },

  async loginUser(payload) {
    const store = loadStore();
    const user = store.users.find(
      (candidate) => normalize(candidate.email) === normalize(payload.email) && candidate.password === payload.password
    );

    if (!user) {
      throw new Error("Invalid email or password");
    }

    return delay({
      token: createToken(user),
      user: publicUser(user)
    });
  },

  async fetchJobs() {
    const store = loadStore();
    return delay([...store.jobs].sort((a, b) => new Date(b.uploadedDate) - new Date(a.uploadedDate)));
  },

  async searchJobs(value) {
    const store = loadStore();
    const query = normalize(value);
    const found = store.jobs.filter(
      (job) => normalize(job.jobId).includes(query) || normalize(job.jobTitle).includes(query)
    );

    if (!found.length) {
      throw new Error("Job not found");
    }

    return delay(found);
  },

  async fetchJobById(jobId) {
    const store = loadStore();
    const job = store.jobs.find((item) => normalize(item.jobId) === normalize(jobId));

    if (!job) {
      throw new Error("Job not found");
    }

    return delay(job);
  },

  async createJob(payload) {
    const store = loadStore();
    const user = getCurrentUser();

    if (!user || !["EMPLOYER", "ADMIN"].includes(user.role)) {
      throw new Error("Employer or admin access required");
    }

    const companyId = user.role === "EMPLOYER" ? user.companyId : payload.companyId;
    const companyName =
      user.role === "EMPLOYER"
        ? user.companyName
        : store.users.find((candidate) => candidate.companyId === companyId)?.companyName || "Demo Company";

    const job = {
      ...payload,
      jobId: nextId("JOB"),
      companyId,
      companyName,
      salary: Number(payload.salary),
      uploadedDate: new Date().toISOString().slice(0, 10)
    };

    store.jobs.unshift(job);
    saveStore(store);
    return delay(job);
  },

  async fetchEmployerJobs() {
    const store = loadStore();
    const user = getCurrentUser();
    return delay(store.jobs.filter((job) => job.companyId === user?.companyId));
  },

  async applyToJob(payload) {
    const store = loadStore();
    const user = getCurrentUser();

    if (!user || user.role !== "APPLICANT") {
      throw new Error("Applicant access required");
    }

    const job = store.jobs.find((item) => item.jobId === payload.jobId);
    if (!job) {
      throw new Error("Job not found");
    }

    const duplicate = store.applications.some(
      (application) => application.applicantUserId === user.userId && application.jobId === job.jobId
    );

    if (duplicate) {
      throw new Error("You have already applied for this job");
    }

    const application = {
      applicationId: nextId("APP"),
      applicantUserId: user.userId,
      applicantName: user.fullName,
      applicantEmail: user.email,
      jobId: job.jobId,
      jobTitle: job.jobTitle,
      companyId: job.companyId,
      companyName: job.companyName,
      phoneNumber: payload.phoneNumber,
      applicationDate: new Date().toISOString().slice(0, 10),
      status: "PENDING"
    };

    store.applications.unshift(application);
    saveStore(store);
    return delay(application);
  },

  async fetchApplicantApplications() {
    const store = loadStore();
    const user = getCurrentUser();
    return delay(store.applications.filter((application) => application.applicantUserId === user?.userId));
  },

  async fetchEmployerApplications() {
    const store = loadStore();
    const user = getCurrentUser();
    return delay(store.applications.filter((application) => application.companyId === user?.companyId));
  },

  async fetchAllApplications() {
    const store = loadStore();
    return delay(store.applications);
  },

  async updateApplicationStatus(applicationId, status) {
    const store = loadStore();
    const application = store.applications.find((item) => item.applicationId === applicationId);

    if (!application) {
      throw new Error("Application not found");
    }

    application.status = status;
    saveStore(store);
    return delay(application);
  }
};
