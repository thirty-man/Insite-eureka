/** @type {import('tailwindcss').Config} */
export default {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        cg1: "#FFC971",
        cg2: "#FFB627",
        cg3: "#FF9505",
        cg4: "#E2711D",
        cg5: "#CC5803",
      },
      fontFamily: {
        ggul: ["GoryeongStrawberry"],
      },
      backgroundImage: {
        backgroundImg: "url('@assets/images/background.png')",
      },
    },
  },
  plugins: [],
};
