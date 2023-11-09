import { TextBox } from "@components/common";
import styled from "styled-components";

const OutDiv = styled.div`
  display: flex;
  justify-content: space-evenly;
  width: 95%;
  height: 90%;
`;

function PageMovingStatistics() {
  // 외부 div(flex)
  // 페이지  URL 불러오기 + 직전 URL 버튼DIv
  // 상세보기 누르면 세부 정보
  const urlDummy = [
    { id: 0, url: "https://www.google.com" },
    { id: 1, url: "https://www.naver.com" },
    { id: 2, url: "https://www.youtube.com" },
    { id: 3, url: "https://www.facebook.com" },
    { id: 4, url: "https://www.twitter.com" },
    { id: 5, url: "https://www.instagram.com" },
    { id: 6, url: "https://www.linkedin.com" },
    { id: 7, url: "https://www.github.com" },
    { id: 8, url: "https://www.stackoverflow.com" },
    { id: 9, url: "https://www.reddit.com" },
  ];

  return (
    <OutDiv>
      <TextBox width="30%" height="100%">
        한개
      </TextBox>
      <TextBox width="65%" height="100%">
        두개
      </TextBox>
    </OutDiv>
  );
}

export default PageMovingStatistics;
