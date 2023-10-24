/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
    "./node_modules/react-datepicker/dist/**/*.js",
  ],
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
          8: "#6F401A",
          9: "#788E52",
          10: "#E0A959",
        },
      },
      fontFamily: {
        ggul: ["GoryeongStrawberry"],
      },
      backgroundImage: {
        backgroundImg: "url('@assets/images/background.png')",
        board: "url('@assets/images/board.png')",
        cupboard: "url(@assets/images/open_cupboard.png)",
        paper: "url(@assets/images/paper.png)",
      },
    },
  },
  plugins: [],
};
