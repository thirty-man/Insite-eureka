import { useEffect, useState } from "react";
import Highcharts from "highcharts";
import HighchartsReact from "highcharts-react-official";
import theme from "@assets/styles/colors";
import styled from "styled-components";
import { IconClock } from "@assets/icons";

const ChartContainer = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  gap: 20px;
`;

const ChartDataContainer = styled.div`
  display: grid;
  width: 40%;
  height: 50%;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: repeat(2, 1fr);
  gap: 10px;
`;

const ChartData = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  width: 90%;
  height: 100%;
`;
const IconImg = styled.img`
  width: 50%;
  height: 50%;
`;
const ActiveUserWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
`;

const ActiveUserTitle = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
`;

const ActiveUserValue = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
`;

let maxDataValue = 0;
let tickInterval = 0;

function ActiveUserPerTime() {
  const [data, setData] = useState<number[]>([0]);

  useEffect(() => {
    const fetchData = () => {
      const newData = {
        nightActiveUserCount: 14,
        morningActiveUserCount: 8,
        afternoonActiveUserCount: 12,
        eveningActiveUserCount: 4,
      };
      const seriesData = [
        newData.nightActiveUserCount,
        newData.morningActiveUserCount,
        newData.afternoonActiveUserCount,
        newData.eveningActiveUserCount,
      ];

      maxDataValue = Math.max(
        seriesData[0],
        seriesData[1],
        seriesData[2],
        seriesData[3],
      );

      tickInterval = maxDataValue / 5;

      try {
        // const response = await getUserCount();
        // const userCountDto = response.userCountDtoList;
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
    title: {
      text: null,
    },
    chart: {
      type: "bar",
      backgroundColor: theme.colors.b2,
      width: 450, // 차트의 너비 설정
      height: 320, // 차트의 높이 설정
      borderRadius: 15,
    },
    plotOptions: {
      series: {
        showInLegend: false,
        color: "#ae4bff",
      },
    },
    xAxis: {
      categories: [
        "밤 활동 사용자",
        "아침 활동 사용자",
        "오후 활동 사용자",
        "저녁 활동 사용자",
      ],
      title: {
        text: null,
      },
      labels: {
        style: {
          color: "white",
        },
      },
    },
    yAxis: {
      max: maxDataValue,
      tickInterval,
      title: {
        text: null,
      },
      labels: {
        style: {
          color: "white",
        },
      },
    },
    series: [
      {
        name: "활동 사용자 수",
        data,
      },
    ],
  };

  return data.length > 0 ? (
    <ChartContainer>
      <HighchartsReact highcharts={Highcharts} options={options} />
      <ChartDataContainer>
        <ChartData>
          <IconImg src={IconClock} alt="Icon Clock" />
          <ActiveUserWrapper>
            <ActiveUserTitle>밤 활동 사용자</ActiveUserTitle>
            <ActiveUserValue>{data[0]}</ActiveUserValue>
          </ActiveUserWrapper>
        </ChartData>
        <ChartData>
          <IconImg src={IconClock} alt="Icon Clock" />
          <ActiveUserWrapper>
            <ActiveUserTitle>아침 활동 사용자</ActiveUserTitle>
            <ActiveUserValue>{data[1]}</ActiveUserValue>
          </ActiveUserWrapper>
        </ChartData>
        <ChartData>
          <IconImg src={IconClock} alt="Icon Clock" />
          <ActiveUserWrapper>
            <ActiveUserTitle>오후 활동 사용자</ActiveUserTitle>
            <ActiveUserValue>{data[2]}</ActiveUserValue>
          </ActiveUserWrapper>
        </ChartData>
        <ChartData>
          <IconImg src={IconClock} alt="Icon Clock" />
          <ActiveUserWrapper>
            <ActiveUserTitle>저녁 활동 사용자</ActiveUserTitle>
            <ActiveUserValue>{data[3]}</ActiveUserValue>
          </ActiveUserWrapper>
        </ChartData>
      </ChartDataContainer>
    </ChartContainer>
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default ActiveUserPerTime;
