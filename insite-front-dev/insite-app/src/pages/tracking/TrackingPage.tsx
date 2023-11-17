import {
  BounceCount,
  EnterPage,
  EntryExitPage,
  ExitPage,
  PageMovingStatistics,
  UrlFlowStatistics,
  TotalUser,
} from "@components/tracking";
import { DefaultBox, TextBox, TitleBox } from "@components/common";
import styled from "styled-components";

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

// const InvisiableDiv = styled.div`
//   width: 30rem;
//   height: 25rem;
//   margin: 1%;
//   padding: 0;
//   border-radius: 15px;
// `;

const SecondCol = styled.div`
  display: flex;
  width: 100%;
  top: 0;
  right: 0;
  background-color: ${(props) => props.theme.colors.b2};
`;

const ThirdCol = styled.div`
  display: flex;
  width: 100%;
  top: 0;
  right: 0;
  background-color: ${(props) => props.theme.colors.b2};
`;

function TrackingPage() {
  return (
    <>
      <FirstCol>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="설정된 기간 내 유입 경로 통계를 제공합니다. 기간 내 모든 유입 데이터를 근거로 데이터를 생성하여 사용자의 설정에 따라 효과적인 유입 경로 통계를 제공합니다.">
            유입 경로 통계
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <UrlFlowStatistics />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="설정된 기간 내 페이지 종료율을 제공합니다. 페이지 사용자의 활동이 종료된 마지막 페이지에 대한 데이터로써 서비스를 종료한 페이지를 내림차순으로 제공하여 서비스 개선에 적용할 수 있습니다.">
            페이지 종료율
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <ExitPage />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="설정된 기간 내 바운스 횟수를 제공합니다. 사용자가 특정한 활동없이 페이지에 유입되자 마자 서비스를 종료했을 경우를 판단한 정보를 제공하여 서비스개선에 적용할 수 있습니다.">
            바운스 횟수
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <BounceCount />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
      </FirstCol>
      <SecondCol>
        {/* <InvisibleDiv /> */}
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="기간 내 유입된 모든 새로운 사용자 수와 활동 사용자 수 정보를 제공합니다. 사용자수는 Cookie Id기반이며, 활동 시간에 따라 각각의 Cookie Id는 ActivityId를 부여 받아 각각의 활동 정보가 기록됩니다.">
            총 유입 사용자 수
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <TotalUser />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox
            text="기간 내 입구 페이지 정보를 제공합니다. 서비스로의 진입 페이지 정보로 어떤 페이지에서 해당 서비스를 시작했는지 확인할 수 있습니다.
"
          >
            입구 페이지
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <EnterPage />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="기간 내 최근 30분을 제외한 데이터를 기반으로 출구 페이지 정보를 제공합니다. 서비스로의 출구 페이지 정보로 어떤 페이지에서 해당 서비스로부터 나갔는지 확인할 수 있습니다.">
            출구 페이지(30분)
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <EntryExitPage />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
      </SecondCol>
      <ThirdCol>
        <DefaultBox width="96rem" height="25rem">
          <TitleBox text="기간 내 직전 페이지에 대한 정보를 제공합니다. 각 페이지 별 직전 페이지에 대한 정보를 그래프로 제공하여 어떤 페이지에서 해당 페이지로 넘어왔는지 확인할 수 있습니다.">
            페이지 별 직전 페이지 통계
          </TitleBox>
          <ContentDiv>
            <PageMovingStatistics />
          </ContentDiv>
        </DefaultBox>
      </ThirdCol>
    </>
  );
}

export default TrackingPage;
