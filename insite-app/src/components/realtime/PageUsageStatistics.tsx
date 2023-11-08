import styled from "styled-components";
import { useState, useEffect } from "react";
import { UserCountDtoType } from "@customtypes/dataTypes";
import { getUserCount } from "@api/realtimeApi";

const Border = styled.div`
  display: flex;
  overflow: auto;
  justify-content: center;
  align-items: start;
  width: 90%;
  height: 80%;
`;

const StyledTable = styled.table`
  width: 100%;
  border-collapse: collapse;
  justify-content: center;
  text-align: center;
`;

const TableHeader = styled.thead`
  font-size: 15px;
  margin-bottom: 15px;
  color: ${(props) => props.theme.colors.a1};
  font-weight: bold;
`;

const TableRow = styled.tr`
  &:nth-child(even) {
    color: coral;
  }
`;

const TableCell = styled.td`
  padding: 8px;
`;

const TableBody = styled.tbody`
  overflow: auto;
  max-height: 200px;
`;

function PageUsageStatistics() {
  const [data, setData] = useState<UserCountDtoType[]>([]);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getUserCount();
        const userCountDto = response.userCountDtoList;
        setData(userCountDto);
      } catch (error) {
        // eslint-disable-next-line no-console
        console.error(error); // 에러 처리
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
          <TableRow>
            <th>순위</th>
            <th>URL</th>
            <th>사용자 수</th>
            <th>랜더링 시간</th>
          </TableRow>
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
