import { useEffect, useState } from "react";
import {
  Border,
  StyledTable,
  TableBody,
  TableCell,
  TableHeader,
  TableRow,
} from "@assets/styles/tableStyles";
import { ActiveUserPerUserDtoType } from "@customtypes/dataTypes";
import { useSelector } from "react-redux";
import { RootState } from "@reducer";

// interface AverageActiveTimeProps {
//   id: number;
//   startDateTime: Date;
//   endDateTime: Date;
// }

function ActiveUserPerUserbyUrl() {
  const [data, setData] = useState<ActiveUserPerUserDtoType[]>([]);

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
        currentUrl: "https://www.google.com",
        activeUserPerUser: 1,
      },
      {
        id: 2,
        currentUrl: "https://www.google.com",
        activeUserPerUser: 6,
      },
      {
        id: 3,
        currentUrl: "https://www.google.com",
        activeUserPerUser: 2,
      },
      {
        id: 4,
        currentUrl: "https://www.google.com",
        activeUserPerUser: 4,
      },
      {
        id: 5,
        currentUrl: "https://www.google.com",
        activeUserPerUser: 3,
      },
      {
        id: 6,
        currentUrl: "https://www.google.com",
        activeUserPerUser: 12,
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
            <th>값</th>
          </tr>
        </TableHeader>
        <TableBody>
          {data.map((item) => (
            <TableRow key={item.id}>
              <TableCell>{item.id}</TableCell>
              <TableCell>{item.currentUrl}</TableCell>
              <TableCell>{item.activeUserPerUser}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </StyledTable>
    </Border>
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default ActiveUserPerUserbyUrl;
