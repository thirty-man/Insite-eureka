import { DefaultBox, TextBox, TitleBox } from "@components/common";
import AbnormalStatistics from "@components/user/AbnormalStatistics";
import PageUsagePerUser from "@components/user/PageUsageUser";
import UserStatistics from "@components/user/UserStatistics";
import ViewCountsByCookie from "@components/user/ViewCountsByCookie";
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

const OverDiv = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  overflow: auto;
`;

function UserPage() {
  return (
    <RowDiv>
      <InnerDiv>
        <FirstCol>
          <DefaultBox width="25rem" height="25rem">
            <TitleBox
              text="기간 내 각 페이지 별 기록된 사용자의 수를 그래프를 통해 확인할 수 있습니다.
어떤 페이지에서 가장 많은 사용자가 존재하는지 파악해
서비스 개선에 적용할 수 있습니다.
"
            >
              사용자 수 조회
            </TitleBox>
            <ContentDiv>
              <TextBox width="90%" height="90%">
                <UserStatistics />
              </TextBox>
            </ContentDiv>
          </DefaultBox>
          <DefaultBox width="25rem" height="25rem">
            <TitleBox
              text="기간 내 서비스 페이지에 대한 조회수를 내림차순으로 제공합니다.
어떤 페이지에서 가장 많은 사용자가 존재하는지 파악해
서비스 개선에 적용할 수 있습니다.
"
            >
              페이지 조회 수
            </TitleBox>
            <ContentDiv>
              <TextBox width="90%" height="90%">
                <PageUsagePerUser />
              </TextBox>
            </ContentDiv>
          </DefaultBox>
        </FirstCol>
        <SecondCol>
          <DefaultBox width="51rem" height="25rem">
            <TitleBox
              text="기간 내 비정상적 사용자 접근 내역 데이터를 제공합니다.
기준 시간 내 특정 쿠키 ID가 서비스에 대량으로 요청을 보낼 경우를 판단합니다.
이를 통해 서비스로의 공격을 판단하여 서비스에 대한 보안 대책을 강구할 수 있습니다.
"
            >
              비정상적 사용자 접근 내역
            </TitleBox>
            <ContentDiv>
              <TextBox width="90%" height="90%">
                <AbnormalStatistics />
              </TextBox>
            </ContentDiv>
          </DefaultBox>
        </SecondCol>
      </InnerDiv>
      <InnerDiv>
        <DefaultBox width="25rem" height="51rem">
          <TitleBox
            text="기간 내 사용자 별 페이지 조회수를 제공합니다.
서비스 전체 사용자수와 함께 사용자 별 페이지 조회수를 파악할 수 있습니다.
사용자 개개인이 어떤 페이지를 사용하는지 구체적으로 파악할 수 있습니다.
"
          >
            사용자 별 페이지 조회 수
          </TitleBox>
          <ContentDiv>
            <OverDiv>
              <ViewCountsByCookie />
            </OverDiv>
          </ContentDiv>
        </DefaultBox>
      </InnerDiv>
    </RowDiv>
  );
}

export default UserPage;
