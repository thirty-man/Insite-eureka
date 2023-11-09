import { getExitData } from "@api/accumulApi";
import {
  Border,
  StyledTable,
  TableBody,
  TableCell,
  TableHeader,
  TableRow,
} from "@assets/styles/tableStyles";
import { PageExitType } from "@customtypes/dataTypes";
import { useEffect, useState } from "react";

function ExitPage() {
  const [data, setData] = useState<PageExitType[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getExitData();
        setData(response.exitFlowDtoList);
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
            <th>Url</th>
            <th>나간 횟수</th>
            <th>비율</th>
          </tr>
        </TableHeader>
        <TableBody>
          {data.map((item, index) => (
            <TableRow key={item.id}>
              <TableCell>{index + 1}</TableCell>
              <TableCell>{item.currentUrl}</TableCell>
              <TableCell>{item.exitCount}</TableCell>
              <TableCell>{item.ratio * 100} %</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </StyledTable>
    </Border>
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default ExitPage;
