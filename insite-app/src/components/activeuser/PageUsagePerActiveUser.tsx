import { useEffect, useState } from "react";
import {
  Border,
  StyledTable,
  TableBody,
  TableCell,
  TableHeader,
  TableRow,
} from "@assets/styles/tableStyles";
import { ViewCountsPerActiveUserDtoType } from "@customtypes/dataTypes";
import { useSelector } from "react-redux";
import { RootState } from "@reducer";

function PageUsagePerActiveUser() {
  const [data, setData] = useState<ViewCountsPerActiveUserDtoType[]>([]);

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
        count: 6,
        ratio: 15,
      },
      {
        id: 2,
        currentUrl: "https://www.example2.com",
        count: 6,
        ratio: 14,
      },
      {
        id: 3,
        currentUrl: "https://www.example3.com",
        count: 10,
        ratio: 38,
      },
      {
        id: 4,
        currentUrl: "https://www.example4.com",
        count: 4,
        ratio: 75,
      },
      {
        id: 5,
        currentUrl: "https://www.example5.com",
        count: 1,
        ratio: 80,
      },
      {
        id: 6,
        currentUrl: "https://www.example6.com",
        count: 16,
        ratio: 15,
      },
      {
        id: 7,
        currentUrl: "https://www.example7.com",
        count: 12,
        ratio: 3,
      },
      {
        id: 8,
        currentUrl: "https://www.example8.com",
        count: 8,
        ratio: 53,
      },
      {
        id: 9,
        currentUrl: "https://www.example9.com",
        count: 8,
        ratio: 99,
      },
      {
        id: 10,
        currentUrl: "https://www.example10.com",
        count: 4,
        ratio: 22,
      },
      {
        id: 11,
        currentUrl: "https://www.example11.com",
        count: 16,
        ratio: 100,
      },
    ];

    setData(newData);
  }, [endDate, startDate]);

  return data.length > 0 ? (
    <Border>
      <StyledTable>
        <TableHeader>
          <tr>
            <th>순위</th>
            <th>URL</th>
            <th>조회 수</th>
            <th>비율(%)</th>
          </tr>
        </TableHeader>
        <TableBody>
          {data.map((item) => (
            <TableRow key={item.id}>
              <TableCell>{item.id}</TableCell>
              <TableCell>{item.currentUrl}</TableCell>
              <TableCell>{item.count}</TableCell>
              <TableCell>{item.ratio}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </StyledTable>
    </Border>
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default PageUsagePerActiveUser;
