import { getEntryExitData } from "@api/accumulApi";
import {
  Border,
  StyledTable,
  TableBody,
  TableCell,
  TableHeader,
  TableRow,
} from "@assets/styles/tableStyles";
import { EntryExitDtoType } from "@customtypes/dataTypes";
import { RootState } from "@reducer";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";

function BeforeUrl() {
  const [data, setData] = useState<EntryExitDtoType[]>([]);
  const startDateTime = useSelector(
    (state: RootState) => state.dateSelectionInfo.start,
  );

  const endDateTime = useSelector(
    (state: RootState) => state.dateSelectionInfo.end,
  );

  useEffect(() => {
    const parseStartDateTime = new Date(startDateTime);
    const parseEndDateTime = new Date(endDateTime);
    const fetchData = async () => {
      try {
        const response = await getEntryExitData(
          parseStartDateTime,
          parseEndDateTime,
        );
        if (!response.exitFlowDtoList) setData([]);
        else setData(response.exitFlowDtoList);
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
            <th>나간 URL</th>
            <th>나간 횟수</th>
            <th>비율</th>
          </tr>
        </TableHeader>
        <TableBody>
          {data.map((item, index) => (
            <TableRow key={item.id}>
              <TableCell>{index + 1}</TableCell>
              <TableCell>{item.exitPage}</TableCell>
              <TableCell>{item.exitCount}</TableCell>
              <TableCell>{+item.exitRate.toFixed(4) * 100}%</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </StyledTable>
    </Border>
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default BeforeUrl;
