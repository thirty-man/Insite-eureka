import { useEffect, useState } from "react";
import { AbnormalDtoListType } from "@customtypes/dataTypes";
import { useSelector } from "react-redux";
import { RootState } from "@reducer";
import {
  Border,
  StyledTable,
  TableBody,
  TableCell,
  TableHeader,
  TableRow,
} from "@assets/styles/tableStyles";
import { getAbnormalUserData } from "@api/accumulApi";

function AbnormalStatistics() {
  const [data, setData] = useState<AbnormalDtoListType[]>([]);
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
        const response = await getAbnormalUserData(
          parseStartDateTime,
          parseEndDateTime,
        );
        if (!response.abnormalDtoList) setData([]);
        else setData(response.abnormalDtoList);
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
            <th>번호</th>
            <th>시간</th>
            <th>Cookie ID</th>
            <th>접근 주소</th>
            <th>접근 시도</th>
            <th>운영체제</th>
          </tr>
        </TableHeader>
        <TableBody>
          {data.map((item) => (
            <TableRow key={item.id}>
              <TableCell>{item.id + 1}</TableCell>
              <TableCell>
                <p>{item.date.split("T")[0]}</p>
                <p>{item.date.split("T")[1].split(".")[0]}</p>
              </TableCell>
              <TableCell>{item.cookieId}</TableCell>
              <TableCell>{item.currentUrl}</TableCell>
              <TableCell>{item.requestCnt}</TableCell>
              <TableCell>{item.osId}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </StyledTable>
    </Border>
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default AbnormalStatistics;
