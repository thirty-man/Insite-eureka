import { useState, useEffect } from "react";
import { UserRefDtoType } from "@customtypes/dataTypes";
import { getRefData } from "@api/accumulApi";
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
        const response = await getRefData();
        setData(response.referrerFlowDtos);
      } catch (error) {
        // eslint-disable-next-line no-console
        console.error(error); // 에러 처리
      }
    };

    fetchData();
  }, []);

  return data && data.length > 0 ? (
    <Border>
      <StyledTable>
        <TableHeader>
          <tr>
            <th>순위</th>
            <th>URL</th>
            <th>명</th>
          </tr>
        </TableHeader>
        <TableBody>
          {data.map((item, index) => (
            <TableRow key={item.id}>
              <TableCell>{index + 1}</TableCell>
              <TableCell>{item.referrer}</TableCell>
              <TableCell>{item.count}</TableCell>
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
