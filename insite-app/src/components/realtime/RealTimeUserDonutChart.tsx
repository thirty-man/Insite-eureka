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
          fullName: item.currentPage,
          name:
            item.currentPage.length <= 10
              ? item.currentPage
              : `${item.currentPage.slice(0, 10)}...`,
          y: Number(Math.round(item.percentage * 100).toFixed(2)),
          dataLabels: {
            enabled: true,
            format: `{point.name}<br> 조회수: ${item.viewCount}`,
            style: {
              fontSize: "15px",
              textOutline: "2px 2px white",
            },
          },
        }));
        if (!userCountDto) setData([]);
        else setData(seriesData);
      } catch (error) {
        // console.error(error); // 에러 처리
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
      width: 400, // 차트의 너비 설정
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
    tooltip: {
      style: {
        fontSize: "14px",
      },
      padding: 15,
      formatter(): string {
        // 포맷을 원하는 대로 조정합니다.
        return `{point.fullName}<br>조회수: {point.viewCount}`;
      },
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
