import { getButtonDetailData } from "@api/accumulApi";
import { ButtonType } from "@customtypes/dataTypes";
import { RootState } from "@reducer";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import Highcharts from "highcharts";
import HighchartsReact from "highcharts-react-official";

function ClickCount() {
  const selectedButton = useSelector(
    (state: RootState) => state.SelectedItemInfo.selectedButton,
  );
  const [data, setData] = useState<ButtonType[]>([]);
  const startDateTime = useSelector(
    (state: RootState) => state.DateSelectionInfo.start,
  );

  const endDateTime = useSelector(
    (state: RootState) => state.DateSelectionInfo.end,
  );

  useEffect(() => {
    const parseStartDateTime = new Date(startDateTime);
    const parseEndDateTime = new Date(endDateTime);
    const getDetailData = async () => {
      try {
        const response = await getButtonDetailData(
          selectedButton,
          parseStartDateTime,
          parseEndDateTime,
        );
        if (!response.clickCountsDtoList) setData([]);
        else setData(response.clickCountsDtoList);
      } catch (error) {
        // eslint-disable-next-line no-console
        // console.error(error); // 에러 처리
      }
    };
    getDetailData();
  }, [endDateTime, selectedButton, startDateTime]);

  const formattedData = data.map((item) => [
    new Date(item.date).getTime(),
    item.counts,
  ]);

  const options = {
    credits: {
      enabled: false, // 워터마크 제거
    },
    accessibility: {
      enabled: false,
    },
    rangeSelector: {
      selected: 1,
    },
    xAxis: {
      title: "",
      type: "datetime",
      labels: {
        style: {
          color: "white",
        },
        format: "{value:%y-%m-%d}", // 날짜 형식 지정
      },
    },
    yAxis: {
      title: "",
      min: 0,
      tickInterval: 30, // 레이블 간격 설정
      labels: {
        style: {
          color: "white",
        },
        format: "{value}회", // 레이블 형식 지정
      },
    },
    chart: {
      marginTop: 30,
      borderRadius: 15,
      title: "",
      backgroundColor: "black",
      width: 1100,
      height: 290,
    },
    title: {
      text: "",
    },
    legend: {
      enabled: false,
    },
    series: [
      {
        name: "Counts",
        data: formattedData,
        tooltip: {
          valueDecimals: 0,
        },
      },
    ],
  };

  return data.length <= 0 ? (
    <div>데이터가 없습니다.</div>
  ) : (
    <HighchartsReact highcharts={Highcharts} options={options} />
  );
}

export default ClickCount;
