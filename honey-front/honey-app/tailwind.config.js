/** @type {import('tailwindcss').Config} */
export default {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      width: {
        640: "640px",
        960: "960px",
      },
      height: {
        640: "640px",
        960: "960px",
      },
      colors: {
        cg: {
          DEFAULT: "#EEE6C4",
          1: "#FFC971",
          2: "#FFB627",
          3: "#FF9505",
          4: "#E2711D",
          5: "#CC5803",
          6: "#A66127",
          7: "#521E07",
        },
      },
      fontFamily: {
        ggul: ["GoryeongStrawberry"],
      },
      backgroundImage: {
        backgroundImg: "url('@assets/images/background.png')",
        board: "url('@assets/images/board.png')",
      },
    },
  },
  plugins: [],
};
