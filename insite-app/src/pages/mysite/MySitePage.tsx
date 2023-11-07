import { BackgroundDiv } from "@components/common";
import MainHeader from "@components/common/header/MainHeader";
import styled from "styled-components";
import { ItemTypes } from "@customtypes/dataTypes";
import SiteList from "@components/common/dropdown/SiteList";
import TextBox from "@components/common/TextBox";
const MainContainer = styled.div`

  width: 80%;
  height: 94%;
`;
const OutletContainer = styled.div`
overflow-y: auto;
width: 100%;
height: 90%;
display: grid;
grid-template-columns: 1fr 1fr 1fr; // 3개의 열을 생성
grid-gap: 1%; // 각 열 사이에 간격 설정
color: white;
`;
const MySitePageStyle = styled.div`
    margin:5%;
    min-height: 300px; /* 최소 높이를 설정, 스크롤을 위한 여유 공간 확보 */
    padding: 20px; /* 내용과 테두리 간 여백 */
    box-sizing: border-box; /* 내용 포함하여 높이 및 너비 설정 */

`;


function MySitePage() {
  return <BackgroundDiv>
    <MainContainer>
          <MainHeader />
          <OutletContainer>
            {SiteList.map((item: ItemTypes) =>(
            <MySitePageStyle key={item.id}>
                <TextBox width="100%" height="100%" children={item.name}></TextBox>
                
              </MySitePageStyle> ))}       
              <MySitePageStyle><TextBox width="100%" height="100%" children="add"></TextBox></MySitePageStyle>
          </OutletContainer>
        </MainContainer>
  </BackgroundDiv>
}

export default MySitePage;
