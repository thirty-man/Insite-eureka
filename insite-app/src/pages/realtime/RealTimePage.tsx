import RealTimeUserDonutChart from "@components/realtime/RealTimeUserDonutChart";
import styled from "styled-components";
import { DefaultBox, TextBox, TitleBox } from "@components/common";
import {
  ButtonStatistics,
  PageUsageStatistics,
  TrafficAttack,
  UrlFlowStatstics,
} from "@components/realtime";

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

function RealTimePage() {
  return (
    <>
      <FirstCol>
        <DefaultBox width="60rem" height="25rem">
          <TitleBox text="최근 30분 내 사용자들이 서비스 내 이용한 페이지를 나타냅니다. 전체 서비스 중 어떤 페이지가 많이 사용됐는지 확인할 수 있습니다. 현재 유저들이 어떤 페이지를 사용하고 있는지 파악할 수 있습니다.">
            실시간 사용자
          </TitleBox>
          <ContentDiv>
            <RealTimeUserDonutChart />
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="62rem" height="25rem">
          <TitleBox text="텍스트">실시간 페이지 이용 통계</TitleBox>
          <TextBox width="90%" height="80%">
            <PageUsageStatistics />
          </TextBox>
        </DefaultBox>
      </FirstCol>
      <SecondCol>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="텍스트">유입 경로 통계</TitleBox>
          <TextBox width="90%" height="80%">
            <UrlFlowStatstics />
          </TextBox>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="텍스트">버튼 통계</TitleBox>
          <TextBox width="90%" height="80%">
            <ButtonStatistics />
          </TextBox>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="텍스트">트래픽 공격 감지</TitleBox>
          <ContentDiv>
            <TrafficAttack />
          </ContentDiv>
        </DefaultBox>
      </SecondCol>
    </>
  );
}

export default RealTimePage;
