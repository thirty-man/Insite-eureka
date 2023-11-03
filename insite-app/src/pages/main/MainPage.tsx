import Header from "@components/common/header/Header";
import { useEffect } from "react";
import useAxios from "@api/useAxios";

function MainPage() {
  const initialConfig = {
    method: "get",
    url: "/health/check",
  };

  const { response, error, loading, fetchData } = useAxios(initialConfig);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  console.log(response?.data);

  return (
    <div>
      <Header />
    </div>
  );
}

export default MainPage;
