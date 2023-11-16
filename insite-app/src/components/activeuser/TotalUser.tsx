import { getActiveUserCounts, getTotalUsers } from "@api/accumulApi";
import { RootState } from "@reducer";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import styled from "styled-components";

const OutDiv = styled.div`
  display: flex;
  flex-direction: column;
`;

const CountBox = styled.div`
  display: flex;
  color: #bf9cef;
  font-size: 3rem;
  font-weight: bold;
  justify-content: end;
  align-items: end;
  flex-shrink: 1; /* 추가된 부분 */
`;

const Myeong = styled.p`
  display: flex;
  font-size: 1rem;
  text-align: end;
  justify-content: end;
  color: white;
`;

function TotalUser() {
  const [activeData, setActiveData] = useState<number>(-1);
  const [normalData, setNormalData] = useState<number>(-1);
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
        const response = await getActiveUserCounts(
          parseStartDateTime,
          parseEndDateTime,
        );
        if (!response.activeUserCount) setActiveData(-1);
        else setActiveData(response.activeUserCount);
      } catch (error) {
        // console.error(error); // 에러 처리
      }
    };

    const getData = async () => {
      try {
        const response = await getTotalUsers(
          parseStartDateTime,
          parseEndDateTime,
        );
        if (!response.total) setNormalData(-1);
        else setNormalData(response.total);
      } catch (error) {
        // console.error(error); // 에러 처리
      }
    };

    fetchData();
    getData();
  }, [startDateTime, endDateTime]);

  return activeData !== -1 ? (
    <OutDiv>
      <div>사용자</div>
      <CountBox>
        {normalData.toLocaleString()}
        <Myeong>명</Myeong>
        <br />
      </CountBox>
      <div>활동 사용자</div>
      <CountBox>
        {activeData.toLocaleString()}
        <Myeong>명</Myeong>
      </CountBox>
    </OutDiv>
  ) : (
    <div>데이터가 없습니다</div>
  );
}

export default TotalUser;
