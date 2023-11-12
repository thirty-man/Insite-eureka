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
import { useSelector } from "react-redux";
import { RootState } from "@reducer";

function UrlFlowStatistics() {
  const [data, setData] = useState<UserRefDtoType[]>([]);
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
        const response = await getRefData(parseStartDateTime, parseEndDateTime);
        if (!response.referrerFlowDtos) setData([]);
        else setData(response.referrerFlowDtos);
      } catch (error) {
        // console.error(error); // 에러 처리
      }
    };

    fetchData();
  }, [endDateTime, startDateTime]);

  return data && data.length > 0 ? (
    <Border>
      <StyledTable>
        <TableHeader>
          <tr>
            <th>순위</th>
            <th>유입 URL</th>
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

export default UrlFlowStatistics;
