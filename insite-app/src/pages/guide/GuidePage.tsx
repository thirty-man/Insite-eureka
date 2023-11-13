import TextBox from "@components/common/TextBox";
import styled from "styled-components";
import { DefaultBox } from "@components/common";

const GuidePageStyle = styled.div`
  .parent {
    display: flex;
  }
  .child {
    margin: auto auto;
  }
  .textMargin {
    margin-bottom: 2%;
  }
  .text {
    overflow: scroll;
  }
  .box {
    width: 100%;
    height: 100%;
    margin: 5%;
  }
`;

function GuidePage() {
  return (
    <GuidePageStyle>
      <div className="parent">
        <div className="child">
          <DefaultBox width="1000px" height="700px">
            <h1 className="textMargin">인사이트 이용 가이드 ⯑</h1>
            <TextBox width="80%" height="80%">
              <div className="box text">
                <br />
                <h2>주의사항:</h2>
                <ul>
                  <li>사항 1: 내용 1</li>
                  <li>사항 2: 내용 2</li>
                  <li>사항 3: 내용 3</li>
                </ul>
                <br />
                <h2>이용 약관</h2>
                <br />
                <p>Team Thirty 웹사이트 정보 추적 서비스 이용 약관</p>
                <br />
                <p>
                  1. 약관의 적용 이 이용 약관(이하 &ldquo;약관&ldquo;)은 Team
                  Thirty (이하 &ldquo;팀&ldquo; 또는 &ldquo;우리&ldquo;)와 Team
                  Thirty의 웹사이트 또는 애플리케이션을 이용하는 사용자(이하
                  &ldquo;사용자&ldquo; 또는 &ldquo;당신&ldquo;) 간의 관계를
                  규정합니다.
                </p>
                <br />
                <p>
                  2. 서비스의 내용 팀은 웹사이트 정보 추적 서비스를 제공합니다.
                  이 서비스는 사용자의 활동을 추적하고 통계 정보를 제공하는 것을
                  목적으로 합니다.
                </p>
                <br />
                <p>
                  3. 이용자의 권리와 의무 (a) 서비스 이용 시 개인정보 제공에
                  동의해야 합니다. (b) 추적된 정보는 통계 및 분석 목적으로만
                  사용되며, 상업적 목적으로 제3자에게 판매되지 않습니다.
                </p>
                <br />
                <p>
                  4. 개인 정보 보호 팀은 사용자의 개인 정보를 보호하기 위해
                  노력합니다. 자세한 내용은 개인 정보 보호 정책을 참조하세요.
                </p>
                <br />
                <p>
                  5. 서비스의 변경과 중단 팀은 사전 통지 없이 서비스를
                  변경하거나 중단할 권리가 있습니다.
                </p>
                <br />
                <p>
                  6. 소유권 이용자가 제공하는 콘텐츠를 제외하고, 서비스에 관한
                  모든 권리, 소유권 및 이익은 팀에게 있습니다.
                </p>
                <br />
                <p>
                  7. 면책 조항 팀은 서비스 이용으로 인한 어떠한 손해에 대해서도
                  책임을 지지 않습니다.
                </p>
                <br />
                <p>
                  8. 약관의 변경 팀은 약관을 수시로 변경할 수 있으며, 변경
                  사항은 웹사이트 또는 애플리케이션을 통해 통보됩니다.
                </p>{" "}
                <br />{" "}
                <p>
                  {" "}
                  9. 준거법과 재판관할 이 약관은 [적용 국가/지역] 법률에 따라
                  해석되며, 모든 분쟁은 [재판 권한을 가진 재판소]에서
                  해결됩니다.
                </p>
                2
              </div>
            </TextBox>
          </DefaultBox>
        </div>
      </div>
    </GuidePageStyle>
  );
}

export default GuidePage;
