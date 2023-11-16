import { getActiveUserCounts } from "@api/accumulApi";
import { RootState } from "@reducer";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import styled from "styled-components";

const CountBox = styled.div`
  font-size: 10rem;
`;

function TotalUser() {
  const [data, setData] = useState<number>(-1);
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
        if (!response.activeUserCount) setData(-1);
        else setData(response.activeUserCount);
      } catch (error) {
        // console.error(error); // 에러 처리
      }
    };

    fetchData();
  }, [startDateTime, endDateTime]);

  return data !== -1 ? (
    <CountBox>{data}</CountBox>
  ) : (
    <div>데이터가 없습니다</div>
  );
}

export default TotalUser;
