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
  color: rgba(174, 75, 255, 0.7);
  font-size: 4.5rem;
`;

const Myeong = styled.p`
  font-size: 1rem;
  white-space: nowrap;
`;

function TotalUser() {
  const [activeData, setActiveData] = useState<number>(-1);
  const [normalData, setnormalData] = useState<number>(-1);
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
        if (!response.totalUserCountRes.total) setnormalData(-1);
        else setnormalData(response.activeUserCount.total);
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
        {normalData}
        <Myeong>명</Myeong>
      </CountBox>
      <div>활동 사용자</div>
      <CountBox>
        {activeData}
        <Myeong>명</Myeong>
      </CountBox>
    </OutDiv>
  ) : (
    <div>데이터가 없습니다</div>
  );
}

export default TotalUser;
