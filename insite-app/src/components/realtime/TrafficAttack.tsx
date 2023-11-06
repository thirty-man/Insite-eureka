// import API from "@api/Api";
import getAbnormality from "@api/realtimeApi";
import { goodpanda, badpanda } from "@assets/images";
import { ImageBox } from "@components/common";
import { useState, useEffect } from "react";

function TrafficAttack() {
  const [data, setData] = useState<boolean>(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getAbnormality(); // await를 사용하여 Promise를 기다립니다.
        setData(response.abnormal);
      } catch (error) {
        // eslint-disable-next-line no-console
        console.error(error); // 에러 처리
      }
    };

    fetchData();
  }, []);

  return !data ? (
    <>
      <ImageBox width="70%" height="70%" src={goodpanda} alt="굿판다" />
      <div>안전합니다</div>
    </>
  ) : (
    <>
      <ImageBox width="70%" height="70%" src={badpanda} alt="굿판다" />
      <div>공격 받는 중</div>
    </>
  );
}

export default TrafficAttack;
