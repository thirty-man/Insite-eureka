import ActiveUsersStatistics from "@components/activeuser/ActiveUserStatistics";
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

const InvisibleDiv = styled.div`
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

function ActiveUserPage() {
  return (
    <>
      <FirstCol>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%">
            활동 사용자 수 조회
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <ActiveUsersStatistics />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%">
            URL 별 활동 사용자 평균 체류 시간
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              하트비트 데이터
            </TextBox>
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%">
            URL 별 활동 사용자 수 / 사용자 수
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              하트비트 데이터
            </TextBox>
          </ContentDiv>
        </DefaultBox>
      </FirstCol>
      <SecondCol>
        <InvisibleDiv />
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%">
            OS 별 활동 사용자 수
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              도넛 데이터
            </TextBox>
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox width="" height="10%">
            페이지 조회 / 활동 사용자 수
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              표 데이터
            </TextBox>
          </ContentDiv>
        </DefaultBox>
      </SecondCol>
      <ThirdCol>
        <DefaultBox width="102rem" height="25rem">
          <TitleBox width="" height="10%">
            시간 별 활동 사용자 수
          </TitleBox>
          <ContentDiv>표 + 아이콘</ContentDiv>
        </DefaultBox>
      </ThirdCol>
    </>
  );
}

export default ActiveUserPage;
