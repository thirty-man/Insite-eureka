import BackgroundDiv from "@components/common/BackgroundDiv";
import Header from "@components/common/header/Header";
import SideBar from "@components/common/sidebar/SideBar";
import { setSelectedMenuId } from "@reducer/SelectedSidebarMenuInfo";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { Outlet, useLocation } from "react-router-dom";
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
  const location = useLocation();
  const dispatch = useDispatch();
  useEffect(() => {
    // 현재 경로 확인
    const currentPath = location.pathname;

    // 클린업 함수
    const cleanup = () => {
      dispatch(setSelectedMenuId(1));
    };

    // /board 하위 경로인지 확인
    const isBoardSubPath =
      currentPath.startsWith("/board/") || currentPath === "/board";

    // /board 하위 경로가 아니면 클린업 함수 실행
    return isBoardSubPath ? undefined : cleanup;
  }, [location, dispatch]);

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
