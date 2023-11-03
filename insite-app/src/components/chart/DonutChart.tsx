// import useGetRealTimeData from "@api/useGetRealTimeData";
import useGetRealTimeData from "@api/useGetRealTimeData";
import Highcharts from "highcharts";
import HighchartsReact from "highcharts-react-official";

function DonutChart() {
  //   const data = useGetRealTimeData("user-counts");

  const options = {
    credits: {
      enabled: false, // 워터마크 제거
    },
    accessibility: {
      enabled: false,
    },
    chart: {
      type: "pie",
      backgroundColor: "transparent",
      width: 500, // 차트의 너비 설정
      height: 300, // 차트의 높이 설정
    },
    title: {
      text: null,
    },
    plotOptions: {
      pie: {
        innerSize: "50%",
        depth: 45,
      },
    },
    series: [
      {
        name: "Brands",
        data: [
          { name: "Chrome", y: 61.41 },
          { name: "Firefox", y: 11.84 },
          { name: "Internet Explorer", y: 10.85 },
          { name: "Safari", y: 4.67 },
          { name: "Edge", y: 4.18 },
          { name: "Others", y: 7.05 },
        ],
      },
    ],
  };

  return <HighchartsReact highcharts={Highcharts} options={options} />;
}

export default DonutChart;
