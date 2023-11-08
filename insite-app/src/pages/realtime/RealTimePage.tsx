import RealTimeUserDonutChart from "@components/chart/RealTimeUserDonutChart";
import styled from "styled-components";
import { DefaultBox } from "@components/common";
import TextBox from "@components/common/TextBox";
import TitleBox from "@components/common/TitleBox";
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
          <TextBox width="90%" height="80%">
            <PageUsageStatistics />
          </TextBox>
        </DefaultBox>
      </FirstCol>
      <SecondCol>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%" fontSize="30px">
            유입 경로 통계
          </TitleBox>
          <TextBox width="90%" height="80%">
            <UrlFlowStatstics />
          </TextBox>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%" fontSize="30px">
            버튼 통계
          </TitleBox>
          <TextBox width="90%" height="80%">
            <ButtonStatistics />
          </TextBox>
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
