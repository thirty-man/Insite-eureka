import { useEffect, useState } from "react";
import Highcharts from "highcharts";
import { ChartDtoType, OSActiveUserDtoType } from "@customtypes/dataTypes";
import HighchartsReact from "highcharts-react-official";

// interface AverageActiveTimeProps {
//   id: number;
//   startDateTime: Date;
//   endDateTime: Date;
// }

function OsActiveUser() {
  const [data, setData] = useState<ChartDtoType[]>([]);

  useEffect(() => {
    const fetchData = () => {
      const newData = [
        { id: 1, os: "Windows", count: 20, ratio: 0.66666666 },
        { id: 2, os: "MAC", count: 10, ratio: 0.33333333 },
      ];

      try {
        // const response = await getUserCount();
        // const userCountDto = response.userCountDtoList;
        const seriesData = newData.map((item: OSActiveUserDtoType) => ({
          name: item.os,
          y: Math.round(item.ratio * 100),
          dataLabels: {
            enabled: true,
            format: `{point.name}:<br> 횟수: ${item.count}`,
            style: {
              fontSize: "15px",
              textOutline: "2px 2px white",
            },
          },
        }));
        if (!newData) setData([]);
        else setData(seriesData);
      } catch (error) {
        // console.error(error); // 에러 처리
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
      width: 350, // 차트의 너비 설정
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

export default OsActiveUser;
