import styled from "styled-components";
import { useState } from "react";

const Border = styled.div`
  display: flex;
  overflow: auto;
  justify-content: center;
  width: 80%;
  height: 80%;
`;

const StyledTable = styled.table`
  width: 100%;
  border-collapse: collapse;
  justify-content: center;
  text-align: center;
`;

const TableHeader = styled.thead`
  font-size: 20px;
  margin-bottom: 15px;
  color: ${(props) => props.theme.colors.a1};
  font-weight: bold;
`;

const TableRow = styled.tr`
  /* &:nth-child(even) {
    background-color: #f2f2f2;
  /* } */
`;

const TableCell = styled.td`
  padding: 8px;
`;

const TableBody = styled.tbody`
  overflow: auto;
  max-height: 200px;
`;

function PageUsageStatistics() {
  const [data, setData] = useState([
    { rank: 1, url: "example.com", user: "User1", renderTime: "2.5s" },
    { rank: 2, url: "another.com", user: "User2", renderTime: "3.2s" },
    { rank: 3, url: "example.org", user: "User3", renderTime: "1.8s" },
    { rank: 4, url: "example.org", user: "User3", renderTime: "1.8s" },
    { rank: 5, url: "example.org", user: "User3", renderTime: "1.8s" },
    { rank: 6, url: "example.org", user: "User3", renderTime: "1.8s" },
    { rank: 7, url: "example.org", user: "User3", renderTime: "1.8s" },
    { rank: 8, url: "example.org", user: "User3", renderTime: "1.8s" },
    { rank: 9, url: "example.org", user: "User3", renderTime: "1.8s" },
    { rank: 10, url: "example.org", user: "User3", renderTime: "1.8s" },
    { rank: 11, url: "example.org", user: "User3", renderTime: "1.8s" },
  ]);

  return (
    <Border>
      <StyledTable>
        <TableHeader>
          <TableRow>
            <th>순위</th>
            <th>URL</th>
            <th>사용자</th>
            <th>랜더링 시간</th>
          </TableRow>
        </TableHeader>
        <TableBody>
          {data.map((item) => (
            <TableRow key={item.rank}>
              <TableCell>{item.rank}</TableCell>
              <TableCell>{item.url}</TableCell>
              <TableCell>{item.user}</TableCell>
              <TableCell>{item.renderTime}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </StyledTable>
    </Border>
  );
}

export default PageUsageStatistics;
