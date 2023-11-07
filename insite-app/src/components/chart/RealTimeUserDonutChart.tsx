import { getUserCount } from "@api/realtimeApi";
import { ChartDtoType, UserCountDtoType } from "@customtypes/dataTypes";
import Highcharts from "highcharts";
import HighchartsReact from "highcharts-react-official";
import { useEffect, useState } from "react";

function RealTimeUserDonutChart() {
  const [data, setData] = useState<ChartDtoType[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getUserCount();
        const userCountDto = response.userCountDtoList;
        const seriesData = userCountDto.map((item: UserCountDtoType) => ({
          name: item.currentPage,
          y: Math.round(item.percentage * 100),
          dataLabels: {
            enabled: true,
            format: `{point.name}:<br> 횟수: ${item.count}`,
            style: {
              fontSize: "15px",
              textOutline: "2px 2px white",
            },
          },
        }));

        setData(seriesData);
      } catch (error) {
        // eslint-disable-next-line no-console
        console.error(error); // 에러 처리
      }
    };

    fetchData();
    const intervalId = setInterval(fetchData, 5000);
    return () => clearInterval(intervalId);
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
      width: 480, // 차트의 너비 설정
      height: 350, // 차트의 높이 설정
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
    tooltip: {
      style: {
        fontSize: "18px",
      },
      padding: 15,
    },
    series: [
      {
        name: "사용량(%)",
        data,
      },
    ],
  };

  return data.length > 0 ? (
    <HighchartsReact highcharts={Highcharts} options={options} />
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default RealTimeUserDonutChart;
