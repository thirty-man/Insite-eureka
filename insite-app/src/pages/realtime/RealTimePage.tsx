import RealTimeUserDonutChart from "@components/chart/RealTimeUserDonutChart";
import styled from "styled-components";
import { DefaultBox } from "@components/common";
import TextBox from "@components/common/TextBox";
import TitleBox from "@components/common/TitleBox";
import PageUsageStatistics from "@components/realtime/PageUsageStatistics";
import TrafficAttack from "@components/realtime/TrafficAttack";
import { RootState } from "@reducer";
import { useSelector } from "react-redux";
import { CalendarButton } from "@components/common/button";


const FirstCol = styled.div`
  display: flex;
  width: 100%;
  top: 0;
  right: 0;
  background-color: ${(props) => props.theme.colors.b2};
`;

const ContentDiv = styled.div`
  display: flex;
  flex-direction: column;
  font-size: 20px;
  align-items: center;

  width: 100%;
  height: 90%;
`;

const SecondCol = styled.div`
  display: flex;
  width: 100%;
  top: 0;
  right: 0;
  background-color: ${(props) => props.theme.colors.b2};
`;

const CalendarContainer = styled.div`
  top: 0;
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: flex-end;
`;
const CalendarWrapper = styled.div`
  width: 30%;
  height: 10%;
  margin-right: 20px;
  cursor: pointer;
`;

function RealTimePage() {
  const realtimeStartDate = useSelector(
    (state: RootState) => state.DateSelectionInfo.realtimeDate.start,
  );

    const formatDateString = (dateString: string): string => {
    const parts = dateString.split("-");
    const year = parseInt(parts[0], 10);
    const month = parseInt(parts[1], 10);
    const day = parseInt(parts[2], 10);
    return `${year}년 ${month}월 ${day}일`;
  };

  const formattedDate = formatDateString(realtimeStartDate);
  return (
    <>
    <CalendarContainer>
      <CalendarWrapper>
        <CalendarButton
          width="100%"
          height="100%"
          startDate={formattedDate}
          endDate={formattedDate}
        />
      </CalendarWrapper>
    </CalendarContainer>
      <FirstCol>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%" fontSize="30px">
            실시간 이용자
          </TitleBox>
          <ContentDiv>
            <RealTimeUserDonutChart />
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="62rem" height="25rem">
          <TitleBox width="" height="10%" fontSize="30px">
            페이지 이용 통계
          </TitleBox>
          <TextBox width="90%" height="70%">
            <PageUsageStatistics />
          </TextBox>
        </DefaultBox>
      </FirstCol>
      <SecondCol>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%" fontSize="30px">
            유입 경로 통계
          </TitleBox>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%" fontSize="30px">
            버튼 통계
          </TitleBox>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%" fontSize="30px">
            트래픽 공격 감지
          </TitleBox>
          <ContentDiv>
            <TrafficAttack />
          </ContentDiv>
        </DefaultBox>
      </SecondCol>
    </>
  );
}

export default RealTimePage;
