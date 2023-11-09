import { ExitPage, UrlFlowStatstics } from "@components/tracking";
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
              <UrlFlowStatstics />
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
              <UrlFlowStatstics />
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
              <UrlFlowStatstics />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%">
            출구 페이지
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <UrlFlowStatstics />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
      </SecondCol>
      <ThirdCol>
        <DefaultBox width="102rem" height="25rem">
          <TitleBox width="" height="10%">
            페이지 이동 통계
          </TitleBox>
          <ContentDiv>안녕</ContentDiv>
        </DefaultBox>
      </ThirdCol>
    </>
  );
}

export default TrackingPage;
