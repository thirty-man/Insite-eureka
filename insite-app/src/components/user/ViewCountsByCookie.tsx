import { useEffect, useState } from "react";
import { CookieIdUrlDtoType } from "@customtypes/dataTypes";
import { useSelector } from "react-redux";
import { RootState } from "@reducer";
import Highcharts from "highcharts";
import HighchartsReact from "highcharts-react-official";

type FormatterContext = {
  point: Highcharts.Point;
  series: Highcharts.Series;
  y: number;
};

function ViewCountsByCookie() {
  const [data, setData] = useState<CookieIdUrlDtoType[]>([]);

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
    (state: RootState) => state.DateSelectionInfo.start,
  );
  const endDate = useSelector(
    (state: RootState) => state.DateSelectionInfo.end,
  );
  useEffect(() => {
    const startDateTime = new Date(startDate);
    const endDateTime = new Date(endDate);
    console.log(startDateTime);
    console.log(endDateTime);

    const newData = [
      {
        id: 1,
        cookieId: "cookie1",
        size: 3,
        viewCountsPerUserDtoList: [
          {
            currentUrl: "/url1",
            count: 2,
            ratio: 0.2,
          },
          {
            currentUrl: "/url2",
            count: 3,
            ratio: 0.3,
          },
          {
            currentUrl: "/url3",
            count: 5,
            ratio: 0.5,
          },
        ],
      },
      {
        id: 2,
        cookieId: "cookie2",
        size: 3,
        viewCountsPerUserDtoList: [
          {
            currentUrl: "/url1",
            count: 2,
            ratio: 0.2,
          },
          {
            currentUrl: "/url2",
            count: 3,
            ratio: 0.3,
          },
          {
            currentUrl: "/url3",
            count: 5,
            ratio: 0.5,
          },
        ],
      },
      {
        id: 3,
        cookieId: "cookie3",
        size: 3,
        viewCountsPerUserDtoList: [
          {
            currentUrl: "/url1",
            count: 2,
            ratio: 0.2,
          },
          {
            currentUrl: "/url2",
            count: 3,
            ratio: 0.3,
          },
          {
            currentUrl: "/url3",
            count: 5,
            ratio: 0.5,
          },
        ],
      },
      {
        id: 4,
        cookieId: "cookie4",
        size: 3,
        viewCountsPerUserDtoList: [
          {
            currentUrl: "/url1",
            count: 2,
            ratio: 0.2,
          },
          {
            currentUrl: "/url2",
            count: 3,
            ratio: 0.3,
          },
          {
            currentUrl: "/url3",
            count: 5,
            ratio: 0.5,
          },
        ],
      },
      {
        id: 5,
        cookieId: "cookie5",
        size: 3,
        viewCountsPerUserDtoList: [
          {
            currentUrl: "/url1",
            count: 2,
            ratio: 0.2,
          },
          {
            currentUrl: "/url2",
            count: 3,
            ratio: 0.3,
          },
          {
            currentUrl: "/url3",
            count: 5,
            ratio: 0.5,
          },
        ],
      },
    ];

    setData(newData);
  }, [endDate, startDate]);

  // 차트 구성
  const options = {
    credits: {
      enabled: false, // 워터마크 제거
    },
    chart: {
      type: "bar",
      backgroundColor: "transparent",
      width: 350, // 차트의 너비 설정
      height: 700, // 차트의 높이 설정
      color: "white",
    },
    title: {
      text: null,
    },
    xAxis: {
      categories: data.map((dat) => dat.cookieId),
      labels: {
        style: {
          color: "white",
          wordWrap: "break-word",
          maxWidth: "75px",
        },
      },
      title: {
        text: null,
      },
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
    series: [
      {
        name: "URL1",
        data: data.map((dat) => {
          const urlData = dat.viewCountsPerUserDtoList.find(
            (urlDat) => urlDat.currentUrl === "/url1",
          );
          return urlData ? urlData.ratio * 100 : 0;
        }),
      },
      {
        name: "URL2",
        data: data.map((dat) => {
          const urlData = dat.viewCountsPerUserDtoList.find(
            (urlDat) => urlDat.currentUrl === "/url2",
          );
          return urlData ? urlData.ratio * 100 : 0;
        }),
      },
      {
        name: "URL3",
        data: data.map((dat) => {
          const urlData = dat.viewCountsPerUserDtoList.find(
            (urlDat) => urlDat.currentUrl === "/url3",
          );
          return urlData ? urlData.ratio * 100 : 0;
        }),
      },
    ],
  };

  return data.length > 0 ? (
    <HighchartsReact highcharts={Highcharts} options={options} />
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default ViewCountsByCookie;
