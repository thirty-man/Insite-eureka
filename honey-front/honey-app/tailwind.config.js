/** @type {import('tailwindcss').Config} */
export default {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      width: {
        640: "640px",
      },
      colors: {
        cg: {
          DEFAULT: "#EEE6C4",
          1: "#FFC971",
          2: "#FFB627",
          3: "#FF9505",
          4: "#E2711D",
          5: "#CC5803",
        },
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
