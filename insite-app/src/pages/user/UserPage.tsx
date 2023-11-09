import { DefaultBox, TextBox, TitleBox } from "@components/common";
import styled from "styled-components";

const FirstCol = styled.div`
  display: flex;
  justify-content: start;
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

const RowDiv = styled.div`
  margin: 1%;
  display: flex;
  padding: 0;
  border-radius: 15px;
`;

const SecondCol = styled.div`
  display: flex;
  top: 0;
  right: 0;
  background-color: ${(props) => props.theme.colors.b2};
`;

const InnerDiv = styled.div`
  width: 50rem;
`;

function UserPage() {
  return (
    <RowDiv>
      <InnerDiv>
        <FirstCol>
          <DefaultBox width="25rem" height="25rem">
            <TitleBox width="" height="10%">
              사용자 수 조회
            </TitleBox>
            <ContentDiv>
              <TextBox width="90%" height="90%">
                꺾은선 그래프
              </TextBox>
            </ContentDiv>
          </DefaultBox>
          <DefaultBox width="25rem" height="25rem">
            <TitleBox width="" height="10%">
              페이지 조회 수
            </TitleBox>
            <ContentDiv>
              <TextBox width="90%" height="90%">
                표
              </TextBox>
            </ContentDiv>
          </DefaultBox>
        </FirstCol>
        <SecondCol>
          <DefaultBox width="51rem" height="25rem">
            <TitleBox width="" height="10%">
              시간 별 활동 사용자 수
            </TitleBox>
            <ContentDiv>표 + 아이콘</ContentDiv>
          </DefaultBox>
        </SecondCol>
      </InnerDiv>
      <InnerDiv>
        <DefaultBox width="25rem" height="51rem">
          <TitleBox width="" height="10%">
            시간 별 활동 사용자 수
          </TitleBox>
          <ContentDiv>표 + 아이콘</ContentDiv>
        </DefaultBox>
      </InnerDiv>
    </RowDiv>
  );
}

export default UserPage;
