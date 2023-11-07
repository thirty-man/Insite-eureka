import styled from "styled-components";
import { useState, useEffect } from "react";
import { getButtonCount } from "@api/realtimeApi";
import { ButtonCountDtoType } from "@customtypes/dataTypes";

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
  padding: 4px;
`;

const TableBody = styled.tbody`
  overflow: auto;
  max-height: 200px;
`;

function ButtonStatistics() {
  const [data, setData] = useState<ButtonCountDtoType[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getButtonCount(); // await를 사용하여 Promise를 기다립니다.
        setData(response.countPerUserDtoList);
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
            <th>버튼</th>
            <th>누른 횟수</th>
            <th>차지 비율</th>
          </TableRow>
        </TableHeader>
        <TableBody>
          {data.map((item) => (
            <TableRow key={item.id}>
              <TableCell>{item.name}</TableCell>
              <TableCell>{item.count}</TableCell>
              <TableCell>{item.countPerUser}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </StyledTable>
    </Border>
  ) : (
    <div>데이터가 없습니다.</div>
  );
}

export default ButtonStatistics;
