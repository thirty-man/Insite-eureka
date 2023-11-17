import BackgroundDiv from "@components/common/BackgroundDiv";
import Header from "@components/common/header/Header";
import SideBar from "@components/common/sidebar/SideBar";
import { Outlet } from "react-router-dom";
import styled from "styled-components";

const HorizontalContainer = styled.div`
  display: flex;
  flex-direction: row;
  width: 100%;
  height: 100%;
`;
const SideBarContainer = styled.div`
  top: 0;
  left: 0;
  width: 20%;
  height: 100%;
  background-color: black;
  z-index: 10;
`;
const MainContainer = styled.div`
  width: 80%;
  height: 94%;
`;
const OutletContainer = styled.div`
  overflow-y: auto;
  width: 100%;
  height: 90%;
  color: white;
`;

function App() {
  return (
    <BackgroundDiv>
      <HorizontalContainer>
        <SideBarContainer>
          <SideBar />
        </SideBarContainer>
        <MainContainer>
          <Header />
          <OutletContainer>
            <Outlet />
          </OutletContainer>
        </MainContainer>
      </HorizontalContainer>
    </BackgroundDiv>
  );
}

export default App;
