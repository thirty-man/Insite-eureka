import API from "@api/Api";
import DonutChart from "@components/chart/DonutChart";
import { BackgroundDiv, DefaultBox } from "@components/common";
import Header from "@components/common/header/Header";

function MainPage() {
  return (
    <BackgroundDiv>
      <Header />
      <DefaultBox width="50%" height="50%">
        <DonutChart />
      </DefaultBox>
    </BackgroundDiv>
  );
}

export default MainPage;
