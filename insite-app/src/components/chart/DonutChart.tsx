// import useGetRealTimeData from "@api/useGetRealTimeData";
import API from "@api/Api";
import Highcharts from "highcharts";
import HighchartsReact from "highcharts-react-official";
import { useEffect, useState } from "react";

type UserCountDto = {
  count: number;
  percentage: number;
  currentPage: string;
};

type ChartDto = {
  name: string;
  y: number;
  dataLables: {
    enabled: boolean;
    format: string;
  };
};

function DonutChart() {
  const [data, setData] = useState<ChartDto[]>([]);
  console.log("data: ", data);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await API.post("/realtime-data/user-counts", {
          token: "a951dd18-d5b5-4c15-a3ba-062198c45807",
        });
        const userCountDto = response.data.userCountDtoList;
        console.log(userCountDto);
        const seriesData = userCountDto.map((item: UserCountDto) => ({
          name: item.currentPage,
          y: Math.round(item.percentage * 100),
          dataLabels: {
            enabled: true,
            format: `{point.name}:<br> 횟수: ${item.count}`,
          },
        }));

        setData(seriesData);
      } catch (error) {
        console.error(error); // 에러 처리
      }
    };

    fetchData();
  }, []);

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
        name: "사용량(%)",
        data,
      },
    ],
  };

  return <HighchartsReact highcharts={Highcharts} options={options} />;
}

export default DonutChart;
