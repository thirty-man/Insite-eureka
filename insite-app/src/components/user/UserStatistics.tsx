import { useEffect, useState } from "react";
import { UserStatisticsDtoType } from "@customtypes/dataTypes";
import { useSelector } from "react-redux";
import { RootState } from "@reducer";
import Highcharts from "highcharts";
import HighchartsReact from "highcharts-react-official";

function UserStatistics() {
  const [data, setData] = useState<UserStatisticsDtoType[]>([]);

  //   useEffect(() => {
  //     const fetchData = async () => {
  //       try {
  //         // const response = await getActiveUserCount(); // await를 사용하여 Promise를 기다립니다.
  //         // setData(response.countPerUserDtoList);
  //       } catch (error) {
  //         // eslint-disable-next-line no-console
  //         console.error(error); // 에러 처리
  //       }
  //     };

  //     fetchData();
  // }

  const startDate = useSelector(
    (state: RootState) => state.dateSelectionInfo.start,
  );
  const endDate = useSelector(
    (state: RootState) => state.dateSelectionInfo.end,
  );
  useEffect(() => {
    const startDateTime = new Date(startDate);
    const endDateTime = new Date(endDate);
    console.log(startDateTime);
    console.log(endDateTime);

    const newData = [
      {
        id: 1,
        currentUrl: "https://www.naver.com",
        userCount: 6,
      },
      {
        id: 2,
        currentUrl: "https://www.example2.com",
        userCount: 6,
      },
      {
        id: 3,
        currentUrl: "https://www.example3.com",
        userCount: 10,
      },
      {
        id: 4,
        currentUrl: "https://www.example4.com",
        userCount: 4,
      },
      {
        id: 5,
        currentUrl: "https://www.example5.com",
        userCount: 1,
      },
    ];

    setData(newData);
  }, [endDate, startDate]);

  // 카테고리 배열 생성
  const categories = data.map((item) => item.currentUrl);

  // 데이터 배열 생성
  const seriesData = data.map((item) => item.userCount);

  // 차트 구성
  const options = {
    credits: {
      enabled: false, // 워터마크 제거
    },
    chart: {
      type: "line",
      backgroundColor: "transparent",
      width: 450, // 차트의 너비 설정
      height: 300, // 차트의 높이 설정
    },
    title: {
      text: null,
    },
    plotOptions: {
      series: {
        showInLegend: false,
      },
    },
    xAxis: {
      categories,
    },
    yAxis: {
      title: {
        text: null,
      },
    },
    series: [
      {
        name: "방문수",
        data: seriesData,
      },
    ],
  };

  return data.length > 0 ? (
    <HighchartsReact highcharts={Highcharts} options={options} />
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default UserStatistics;
