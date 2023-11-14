import { useEffect, useState } from "react";
import { CookieIdUrlDtoType, CurrentUrlDtoType } from "@customtypes/dataTypes";
import { useSelector } from "react-redux";
import { RootState } from "@reducer";
import Highcharts from "highcharts";
import HighchartsReact from "highcharts-react-official";
import { getAllUrl, getViewCountsPerUser } from "@api/accumulApi";

type FormatterContext = {
  point: Highcharts.Point;
  series: Highcharts.Series;
  y: number;
};

function ViewCountsByCookie() {
  const [data, setData] = useState<CookieIdUrlDtoType[]>([]);
  const [urlData, setUrlData] = useState<CurrentUrlDtoType[]>([]);

  const startDateTime = useSelector(
    (state: RootState) => state.DateSelectionInfo.start,
  );

  const endDateTime = useSelector(
    (state: RootState) => state.DateSelectionInfo.end,
  );

  useEffect(() => {
    const parseStartDateTime = new Date(startDateTime);
    const parseEndDateTime = new Date(endDateTime);

    const getUrlList = async () => {
      try {
        const response = await getAllUrl(parseStartDateTime, parseEndDateTime);
        if (!response.currentUrlDtoList) setUrlData([]);
        else setUrlData(response.currentUrlDtoList);
      } catch (error) {
        // console.error(error); // 에러 처리
      }
    };

    const fetchData = async () => {
      try {
        const response = await getViewCountsPerUser(
          parseStartDateTime,
          parseEndDateTime,
        );
        if (!response.cookieIdUrlDtoList) setData([]);
        else setData(response.cookieIdUrlDtoList);
      } catch (error) {
        // console.error(error); // 에러 처리
      }
    };

    getUrlList();
    fetchData();
  }, [endDateTime, startDateTime]);

  // url 가져오기
  const uniqueUrls = Array.from(
    new Set(urlData.map((urlDat) => urlDat.currentUrl)),
  );

  // 차트에 넣을 데이터 생성
  const seriesData = uniqueUrls.map((url) => ({
    name: url,
    data: data.map((item) => {
      const newUrlList = item.viewCountsPerUserDtoList.find(
        (urlDat) => urlDat.currentUrl === url,
      );
      return newUrlList ? newUrlList.ratio * 100 : 0;
    }),
  }));

  // 차트 구성
  const options = {
    credits: {
      enabled: false, // 워터마크 제거
    },
    accessibility: {
      enabled: false,
    },
    chart: {
      type: "bar",
      backgroundColor: "transparent",
      width: 350, // 차트의 너비 설정
      height: 700, // 차트의 높이 설정
      color: "white",
    },
    title: "",
    xAxis: {
      categories: data.map((item) => item.cookieId),
      labels: {
        style: {
          color: "white",
          wordWrap: "break-word",
          maxWidth: "75px",
        },
      },
      title: "",
    },
    yAxis: {
      min: 0,
      title: {
        text: "URL",
        style: {
          color: "white",
        },
      },
      labels: {
        style: {
          color: "white",
          overflow: "justify",
        },
      },
    },
    tooltip: {
      headerFormat: '<span style="font-size: 10px">{point.key}</span><br/>',
      pointFormat:
        '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}%</b> of total<br/>',
      shared: true,
    },
    plotOptions: {
      bar: {
        stacking: "percent",
        dataLabels: {
          enabled: true,
          useHTML: true,
          formatter() {
            const context = this as unknown as FormatterContext;
            return `<span style="color: white;">${context.y}%</span>`;
          },
          align: "center",
          verticalAlign: "middle",
          y: -5,
          style: {
            color: "white",
          },
        },
      },
    },
    legend: {
      itemStyle: {
        color: "white",
      },
    },
    series: seriesData,
  };

  return data.length > 0 ? (
    <HighchartsReact highcharts={Highcharts} options={options} />
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default ViewCountsByCookie;
