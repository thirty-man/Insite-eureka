import styled from "styled-components";

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
  position: sticky;
  top: 0;
  font-size: 15px;
  margin-bottom: 15px;
  color: ${(props) => props.theme.colors.a1};
  font-weight: bold;
  background-color: ${(props) => props.theme.colors.b3}; /* 배경색 설정 */
  z-index: 1; /* 다른 내용 위에 나타나도록 설정 */
`;

const TableRow = styled.tr`
  &:nth-child(even) {
    color: coral;
  }
`;

const TableCell = styled.td`
  max-width: 7rem;
  word-wrap: break-word;
  padding: 4px;
`;

const TableBody = styled.tbody`
  overflow: auto;
  font-size: 15px;
  max-height: 200px;
`;

export { Border, StyledTable, TableHeader, TableRow, TableCell, TableBody };
