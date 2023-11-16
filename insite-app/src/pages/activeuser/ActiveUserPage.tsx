import {
  ActiveUserPerTime,
  ActiveUserPerUserbyUrl,
  ActiveUserStatistics,
  AverageActiveTimeByUrl,
  OsActiveUser,
  PageUsagePerActiveUser,
  TotalUser,
} from "@components/activeuser";
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
          <TitleBox text="기간 내 활동 사용자 수 조회 정보를 제공합니다. 각 페이지 별 활동 사용자 수를 내림차순으로 제공함으로써 활동 사용자들이 어떤 페이지를 많이 사용하는지 파악할 수 있습니다.">
            활동 사용자 수 조회
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <ActiveUserStatistics />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="기간 내 활동 사용자 별 평균 체류 시간 정보를 제공합니다. 활동 사용자들이 어떤 페이지에서 오래 머무르는지 파악해 서비스 개선에 적용할 수 있습니다.">
            평균 체류 시간
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <AverageActiveTimeByUrl />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="기간 내 사용자 별 페이지에 따른 평균 활동 횟수를 제공합니다. 어떤 페이지에 사용자가 자주 방문하는지 파악하여 서비스 개선에 적용할 수 있습니다.">
            사용자당 활동 횟수
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <ActiveUserPerUserbyUrl />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
      </FirstCol>
      <SecondCol>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="기간 내 전체 사용자 수와 활동 사용자 수를 제공합니다. Cookie Id기반의 사용자 수와 활동 시간에 따라 부여된 Activity Id로 나뉜 활동 사용자 수를 파악할 수 있습니다.">
            총 사용자 수
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <TotalUser />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="기간 내 어떤 운영체제를 통해 해당 서비스에 접근했는지 파악할 수 있습니다. 서비스를 제공하는 플랫폼 확장 계획에 적용할 수 있습니다.">
            OS 별 활동 사용자 수
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <OsActiveUser />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
        <DefaultBox width="30rem" height="25rem">
          <TitleBox text="기간 내 페이지 별 전체 활동 횟수와 활동 사용자 별 평균 활동 횟수를 제공합니다. 활동 사용자가 자주 사용하는 페이지를 파악할 수 있어 서비스 개선에 적용할 수 있습니다.">
            활동당 페이지 조회수
          </TitleBox>
          <ContentDiv>
            <TextBox width="90%" height="90%">
              <PageUsagePerActiveUser />
            </TextBox>
          </ContentDiv>
        </DefaultBox>
      </SecondCol>
      <ThirdCol>
        <DefaultBox width="102rem" height="25rem">
          <TitleBox text="기간 내 시간대 별 활동 사용자 수를 제공합니다. 시간대에 따라 서비스를 이용하는 활동 사용자 수를 파악할 수 있어 패치 시간 설정 등에 적용할 수 있습니다.">
            시간 별 활동 사용자 수
          </TitleBox>
          <ContentDiv>
            <ActiveUserPerTime />
          </ContentDiv>
        </DefaultBox>
      </ThirdCol>
    </>
  );
}

export default ActiveUserPage;
