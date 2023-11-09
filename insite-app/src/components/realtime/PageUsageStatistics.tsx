import { useState, useEffect } from "react";
import { UserCountDtoType } from "@customtypes/dataTypes";
import { getUserCount } from "@api/realtimeApi";
import {
  Border,
  StyledTable,
  TableBody,
  TableCell,
  TableHeader,
  TableRow,
} from "@assets/styles/tableStyles";

function PageUsageStatistics() {
  const [data, setData] = useState<UserCountDtoType[]>([]);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getUserCount();
        if (!response.userCountDtoList) setData([]);
        else setData(response.userCountDtoList);
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
            <th>사용자 수</th>
            <th>랜더링 시간</th>
          </tr>
        </TableHeader>
        <TableBody>
          {data.map((item, index) => (
            <TableRow key={item.id}>
              <TableCell>{index + 1}</TableCell>
              <TableCell>{item.currentPage}</TableCell>
              <TableCell>{item.count}</TableCell>
              <TableCell>{item.responseTime}s</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </StyledTable>
    </Border>
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default PageUsageStatistics;
