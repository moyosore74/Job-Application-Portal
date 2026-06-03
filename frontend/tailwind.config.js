/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,jsx}"],
  theme: {
    extend: {
      colors: {
        brand: {
          50: "#f0f7ff",
          100: "#dcecff",
          200: "#bedcff",
          300: "#8fc4ff",
          400: "#58a4ff",
          500: "#2d80f6",
          600: "#145fd7",
          700: "#114caf",
          800: "#133f8e",
          900: "#163875"
        }
      },
      boxShadow: {
        panel: "0 20px 45px -25px rgba(15, 23, 42, 0.25)"
      }
    }
  },
  plugins: []
};
