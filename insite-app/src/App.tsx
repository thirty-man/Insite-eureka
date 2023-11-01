import BackgroundDiv from "@components/common/BackgroundDiv";
import Header from "@components/common/header/Header";
import { Outlet } from "react-router-dom";

function App() {
  return (
    <BackgroundDiv>
      <Header />
      {/* <SIdebar /> */}
      <Outlet />
    </BackgroundDiv>
  );
}

export default App;
