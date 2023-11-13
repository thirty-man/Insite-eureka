import { useEffect, useState } from "react";
import { AbnormalDtoListType } from "@customtypes/dataTypes";
import { useSelector } from "react-redux";
import { RootState } from "@reducer";
import {
  Border,
  StyledTable,
  TableBody,
  TableCell,
  TableHeader,
  TableRow,
} from "@assets/styles/tableStyles";

function AbnormalStatistics() {
  const [data, setData] = useState<AbnormalDtoListType[]>([]);

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
        id: 0,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T04:22:12.716",
        currentUrl: "현재주소/search",
        language: "미국",
        requestCnt: 30,
        osId: "안드로이드",
      },
      {
        id: 1,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T04:22:17.051",
        currentUrl: "현재주소/search",
        language: "미국",
        requestCnt: 30,
        osId: "안드로이드",
      },
      {
        id: 2,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T04:22:27.573",
        currentUrl: "현재주소/search",
        language: "미국",
        requestCnt: 30,
        osId: "안드로이드",
      },
      {
        id: 3,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T04:22:31.703",
        currentUrl: "현재주소/search",
        language: "미국",
        requestCnt: 30,
        osId: "안드로이드",
      },
      {
        id: 4,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T04:22:36.671",
        currentUrl: "현재주소/search",
        language: "미국",
        requestCnt: 30,
        osId: "안드로이드",
      },
      {
        id: 5,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T04:22:47.768",
        currentUrl: "현재주소/search",
        language: "미국",
        requestCnt: 30,
        osId: "안드로이드",
      },
      {
        id: 6,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T04:23:25.746",
        currentUrl: "현재주소/search",
        language: "미국",
        requestCnt: 30,
        osId: "안드로이드",
      },
      {
        id: 7,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T04:23:29.835",
        currentUrl: "현재주소/search",
        language: "미국",
        requestCnt: 30,
        osId: "안드로이드",
      },
      {
        id: 8,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T04:23:34.088",
        currentUrl: "현재주소/search",
        language: "미국",
        requestCnt: 30,
        osId: "안드로이드",
      },
      {
        id: 9,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T04:23:36.684",
        currentUrl: "현재주소/search",
        language: "미국",
        requestCnt: 30,
        osId: "안드로이드",
      },
      {
        id: 10,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T04:58:52.374",
        currentUrl: "현재주소/search",
        language: "미국",
        requestCnt: 19,
        osId: "안드로이드",
      },
      {
        id: 11,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T04:59:02.466",
        currentUrl: "현재주소/search",
        language: "미국",
        requestCnt: 20,
        osId: "안드로이드",
      },
      {
        id: 12,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T05:08:51.023",
        currentUrl: "현재주소/login",
        language: "미국",
        requestCnt: 20,
        osId: "안드로이드",
      },
      {
        id: 13,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T05:10:43.812",
        currentUrl: "현재주소/search/유재석",
        language: "미국",
        requestCnt: 20,
        osId: "안드로이드",
      },
      {
        id: 14,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T05:15:12.383",
        currentUrl: "현재주소/search/아이유",
        language: "미국",
        requestCnt: 20,
        osId: "안드로이드",
      },
      {
        id: 15,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T05:15:26.369",
        currentUrl: "현재주소/search/왕셉셉이",
        language: "미국",
        requestCnt: 20,
        osId: "안드로이드",
      },
      {
        id: 16,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T05:15:40.557",
        currentUrl: "현재주소/search/김성규",
        language: "미국",
        requestCnt: 20,
        osId: "안드로이드",
      },
      {
        id: 17,
        cookieId: "123-23asd-789-456",
        date: "2023-11-08T19:50:36.735",
        currentUrl: "현재주소/search/김성규",
        language: "미국",
        requestCnt: 20,
        osId: "안드로이드",
      },
    ];

    setData(newData);
  }, [endDate, startDate]);

  // 카테고리 배열 생성

  // 차트 구성

  return data.length > 0 ? (
    <Border>
      <StyledTable>
        <TableHeader>
          <tr>
            <th>번호</th>
            <th>시간</th>
            <th>Cookie ID</th>
            <th>접근 주소</th>
            <th>접근 시도</th>
            <th>운영체제</th>
          </tr>
        </TableHeader>
        <TableBody>
          {data.map((item) => (
            <TableRow key={item.id}>
              <TableCell>{item.id + 1}</TableCell>
              <TableCell>{item.date}</TableCell>
              <TableCell>{item.cookieId}</TableCell>
              <TableCell>{item.currentUrl}</TableCell>
              <TableCell>{item.requestCnt}</TableCell>
              <TableCell>{item.osId}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </StyledTable>
    </Border>
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default AbnormalStatistics;
