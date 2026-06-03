import { createContext, useContext, useEffect, useMemo, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { setForbiddenHandler, setUnauthorizedHandler } from "../services/http";
import { getDashboardPath } from "../utils/auth";

const AuthContext = createContext(null);

const TOKEN_KEY = "job_portal_token";
const USER_KEY = "job_portal_user";

function readStoredUser() {
  const raw = localStorage.getItem(USER_KEY);
  if (!raw) {
    return null;
  }

  try {
    return JSON.parse(raw);
  } catch {
    localStorage.removeItem(USER_KEY);
    return null;
  }
}

export function AuthProvider({ children }) {
  const navigate = useNavigate();
  const location = useLocation();
  const [token, setToken] = useState(() => localStorage.getItem(TOKEN_KEY));
  const [user, setUser] = useState(readStoredUser);
  const [permissionError, setPermissionError] = useState("");

  useEffect(() => {
    setUnauthorizedHandler(() => {
      localStorage.removeItem(TOKEN_KEY);
      localStorage.removeItem(USER_KEY);
      setToken(null);
      setUser(null);
      navigate("/login", {
        replace: true,
        state: {
          message: "Your session expired. Please sign in again.",
          from: location.pathname
        }
      });
    });

    setForbiddenHandler((message) => {
      setPermissionError(message || "You do not have permission to access that page.");
      navigate("/unauthorized", { state: { message } });
    });
  }, [location.pathname, navigate]);

  const login = (authResponse) => {
    localStorage.setItem(TOKEN_KEY, authResponse.token);
    localStorage.setItem(USER_KEY, JSON.stringify(authResponse.user));
    setToken(authResponse.token);
    setUser(authResponse.user);
    setPermissionError("");
    navigate(getDashboardPath(authResponse.user.role), { replace: true });
  };

  const logout = () => {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    setToken(null);
    setUser(null);
    setPermissionError("");
    navigate("/", { replace: true });
  };

  const value = useMemo(
    () => ({
      token,
      user,
      isAuthenticated: Boolean(token && user),
      permissionError,
      setPermissionError,
      login,
      logout
    }),
    [permissionError, token, user]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth must be used inside AuthProvider");
  }

  return context;
}
