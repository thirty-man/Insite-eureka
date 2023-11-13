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
import { getViewCountsPerActiveUser } from "@api/accumulApi";

// 페이지 조회 / 활동 사용자 수
function PageUsagePerActiveUser() {
  const [data, setData] = useState<ViewCountsPerActiveUserDtoType[]>([]);

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
        const response = await getViewCountsPerActiveUser(
          parseStartDateTime,
          parseEndDateTime,
        );
        if (!response.viewCountsPerActiveUserDtoList) setData([]);
        else setData(response.viewCountsPerActiveUserDtoList);
      } catch (error) {
        // console.error(error); // 에러 처리
      }
    };

    fetchData();
  }, [endDateTime, startDateTime]);

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
