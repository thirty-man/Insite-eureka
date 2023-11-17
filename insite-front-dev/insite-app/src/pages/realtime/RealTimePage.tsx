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
          <TitleBox text="최근 30분 내 사용자들이 서비스 내 이용한 페이지에 대한 정보를 나타냅니다. 많이 사용된 페이지 순서대로 확인할 수 있고, 해당 페이지의 렌더링 시간 또한 파악할 수 있습니다.">
            실시간 페이지 이용 통계
          </TitleBox>
          <TextBox width="90%" height="80%">
            <PageUsageStatistics />
          </TextBox>
        </DefaultBox>
      </FirstCol>
      <SecondCol>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="최근 30분 내 유입 경로 통계를 제공합니다. 어떤 플랫폼에서 해당 서비스로 유입되었는지 판단할 수 있는 정보를 제공합니다. 주로 유입되는 경로를 파악해 서비스를 홍보할 때 이용할 수 있습니다.">
            유입 경로 통계
          </TitleBox>
          <TextBox width="90%" height="80%">
            <UrlFlowStatstics />
          </TextBox>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="최근 30분 내 버튼 통계를 제공합니다. 서비스 내 버튼 클릭 횟수와 평균 클릭 횟수 정보를 제공합니다. 이를 통해 서비스 내 버튼 활용도를 파악해 서비스 개선에 적용할 수 있습니다.">
            버튼 통계
          </TitleBox>
          <TextBox width="90%" height="80%">
            <ButtonStatistics />
          </TextBox>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="최근 30분 내 트래픽 공격을 감지합니다. 기준 시간 내 특정 쿠키 ID가 서비스에 대량으로 요청을 보낼 경우를 판단합니다. 이를 통해 서비스로의 공격을 판단하여 서비스에 대한 보안 대책을 강구할 수 있습니다.">
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
