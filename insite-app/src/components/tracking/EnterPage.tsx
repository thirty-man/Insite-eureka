import { getEnterCountData } from "@api/accumulApi";
import {
  Border,
  StyledTable,
  TableBody,
  TableCell,
  TableHeader,
  TableRow,
} from "@assets/styles/tableStyles";
import { PageEnterDtoType } from "@customtypes/dataTypes";
import { RootState } from "@reducer";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";

function EnterPage() {
  const [data, setData] = useState<PageEnterDtoType[]>([]);
  const startDateTime = useSelector(
    (state: RootState) => state.DateSelectionInfo.start,
  );

  const endDateTime = useSelector(
    (state: RootState) => state.DateSelectionInfo.end,
  );

  useEffect(() => {
    const parseStartDateTime = new Date(startDateTime);
    const parseEndDateTime = new Date(endDateTime);
    const fetchData = async () => {
      try {
        const response = await getEnterCountData(
          parseStartDateTime,
          parseEndDateTime,
        );
        if (!response.enterFlowDtoList) setData([]);
        else setData(response.enterFlowDtoList);
      } catch (error) {
        // console.error(error); // 에러 처리
      }
    };

    fetchData();
  }, [startDateTime, endDateTime]);

  return data && data.length > 0 ? (
    <Border>
      <StyledTable>
        <TableHeader>
          <tr>
            <th>순위</th>
            <th>진입 URL</th>
            <th>진입 횟수</th>
            <th>비율</th>
          </tr>
        </TableHeader>
        <TableBody>
          {data.map((item, index) => (
            <TableRow key={item.id}>
              <TableCell>{index + 1}</TableCell>
              <TableCell>{item.enterPage}</TableCell>
              <TableCell>{item.enterCount}</TableCell>
              <TableCell>{+item.enterRate.toFixed(4) * 100} %</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </StyledTable>
    </Border>
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default EnterPage;
