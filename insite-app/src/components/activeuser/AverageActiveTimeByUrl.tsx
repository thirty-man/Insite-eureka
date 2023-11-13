import { useEffect, useState } from "react";
import {
  Border,
  StyledTable,
  TableBody,
  TableCell,
  TableHeader,
  TableRow,
} from "@assets/styles/tableStyles";
import { AverageActiveTimeDtoType } from "@customtypes/dataTypes";
import { useSelector } from "react-redux";
import { RootState } from "@reducer";

// interface AverageActiveTimeProps {
//   id: number;
//   startDateTime: Date;
//   endDateTime: Date;
// }

function AverageActiveTimeByUrl() {
  const [data, setData] = useState<AverageActiveTimeDtoType[]>([]);

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
      { id: 1, currentUrl: "https://www.naver.com", averageActiveTime: 300.12 },
      {
        id: 2,
        currentUrl: "https://www.example2.com",
        averageActiveTime: 243.05,
      },
      {
        id: 3,
        currentUrl: "https://www.example3.com",
        averageActiveTime: 553.185,
      },
      {
        id: 4,
        currentUrl: "https://www.example4.com",
        averageActiveTime: 464.192,
      },
      {
        id: 5,
        currentUrl: "https://www.example5.com",
        averageActiveTime: 407.679,
      },
      {
        id: 6,
        currentUrl: "https://www.example6.com",
        averageActiveTime: 389.537,
      },
      {
        id: 7,
        currentUrl: "https://www.example7.com",
        averageActiveTime: 364.227,
      },
      {
        id: 8,
        currentUrl: "https://www.example8.com",
        averageActiveTime: 430.776,
      },
      {
        id: 9,
        currentUrl: "https://www.example9.com",
        averageActiveTime: 597.312,
      },
      {
        id: 10,
        currentUrl: "https://www.example10.com",
        averageActiveTime: 287.432,
      },
      {
        id: 11,
        currentUrl: "https://www.example11.com",
        averageActiveTime: 386.025,
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
            <th>평균체류시간(s)</th>
          </tr>
        </TableHeader>
        <TableBody>
          {data.map((item) => (
            <TableRow key={item.id}>
              <TableCell>{item.id}</TableCell>
              <TableCell>{item.currentUrl}</TableCell>
              <TableCell>{item.averageActiveTime}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </StyledTable>
    </Border>
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default AverageActiveTimeByUrl;
