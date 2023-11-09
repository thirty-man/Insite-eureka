import { useState, useEffect } from "react";
import { UserRefDtoType } from "@customtypes/dataTypes";
import { getRefData } from "@api/realtimeApi";
import {
  Border,
  StyledTable,
  TableBody,
  TableCell,
  TableHeader,
  TableRow,
} from "@assets/styles/tableStyles";

function UrlFlowStatstics() {
  const [data, setData] = useState<UserRefDtoType[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getRefData(); // await를 사용하여 Promise를 기다립니다.
        if (!response.referrerDtoList) setData([]);
        else setData(response.referrerDtoList);
      } catch (error) {
        // console.error(error); // 에러 처리
      }
    };

    fetchData();
    const intervalId = setInterval(fetchData, 5000);
    return () => clearInterval(intervalId);
  }, []);

  return data.length > 0 ? (
    <Border>
      <StyledTable>
        <TableHeader>
          <tr>
            <th>순위</th>
            <th>URL</th>
            <th>명</th>
            <th>비율</th>
          </tr>
        </TableHeader>
        <TableBody>
          {data.map((item, index) => (
            <TableRow key={item.id}>
              <TableCell>{index + 1}</TableCell>
              <TableCell>{item.referrer}</TableCell>
              <TableCell>{item.count}</TableCell>
              <TableCell>{+item.percentage.toFixed(4) * 100}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </StyledTable>
    </Border>
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default UrlFlowStatstics;
