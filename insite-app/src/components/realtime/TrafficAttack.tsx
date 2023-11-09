// import API from "@api/Api";
import { getAbnormality } from "@api/realtimeApi";
import { goodpanda, badpanda } from "@assets/images";
import { ImageBox } from "@components/common";
import TextBox from "@components/common/TextBox";
import { AbnormalType } from "@customtypes/dataTypes";
import { useState, useEffect } from "react";
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
  font-size: 15px;
  padding: 0.5rem;
`;

const TableBody = styled.tbody`
  overflow: auto;
  max-height: 200px;
`;

function TrafficAttack() {
  const [data, setData] = useState<AbnormalType[]>([]);
  const [isLoading, setIsLoding] = useState<boolean>(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getAbnormality(); // await를 사용하여 Promise를 기다립니다.
        if (!response.abnormalDtoList) setData([]);
        else setData(response.abnormalDtoList);

        setIsLoding(false);
      } catch (error) {
        // console.error(error); // 에러 처리
      }
    };

    fetchData();
    const intervalId = setInterval(fetchData, 5000);
    return () => clearInterval(intervalId);
  }, []);

  // eslint-disable-next-line no-nested-ternary
  return isLoading ? (
    <div>데이터가 없습니다</div>
  ) : data.length === 0 ? (
    <>
      <ImageBox width="70%" height="70%" src={goodpanda} alt="굿판다" />
      <div>안전합니다</div>
    </>
  ) : (
    <>
      <ImageBox width="50%" height="50%" src={badpanda} alt="배드판다" />
      <TextBox width="90%" height="40%">
        <Border>
          <StyledTable>
            <TableHeader>
              <TableRow>
                <th>공격자</th>
                <th>대상 URL</th>
              </TableRow>
            </TableHeader>
            <TableBody>
              {data.map((item) => (
                <TableRow key={item.id}>
                  <TableCell>{item.cookieId}</TableCell>
                  <TableCell>{item.currentUrl}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </StyledTable>
        </Border>
      </TextBox>
    </>
  );
}

export default TrafficAttack;
