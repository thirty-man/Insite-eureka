import {
  BounceCount,
  EnterPage,
  EntryExitPage,
  ExitPage,
  PageMovingStatistics,
  UrlFlowStatistics,
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

const InvisiableDiv = styled.div`
  width: 30rem;
  height: 25rem;
  margin: 1%;
  padding: 0;
  border-radius: 15px;
`;

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
          <TitleBox width="" height="10%">
            유입 경로 통계
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <UrlFlowStatistics />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%">
            페이지 종료율
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <ExitPage />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%">
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
        <InvisiableDiv />
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%">
            입구 페이지
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <EnterPage />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%">
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
          <TitleBox width="100%" height="10%">
            페이지 이동 통계
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
