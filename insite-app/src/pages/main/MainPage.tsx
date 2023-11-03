import DonutChart from "@components/chart/DonutChart";
import { BackgroundDiv, DefaultBox } from "@components/common";
import Header from "@components/common/header/Header";
import axios from "axios";
import { useEffect } from "react";

function MainPage() {
  const { VITE_API_URI } = import.meta.env;
  const token = sessionStorage.getItem("Authorization");

  const fetchData = () => {
    const url = `${VITE_API_URI}/realtime-data/user-counts`;

    const config = {
      headers: { Authorization: token, "Content-Type": "application/json" },
    };

    axios
      .post(url, null, config)
      .then((response) => {
        console.log("응답 데이터: ", response.data);
        // 여기에서 응답 데이터를 처리하거나 상태에 저장할 수 있습니다.
      })
      .catch((error) => {
        console.error("에러: ", error);
        // 에러 처리를 수행합니다.
      });
  };

  useEffect(() => {
    fetchData(); // 컴포넌트가 로드될 때 데이터를 가져오도록 호출
  }, []);

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
