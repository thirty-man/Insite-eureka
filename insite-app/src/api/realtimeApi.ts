import API from "./Api";

const getAbnormality = async () => {
  try {
    const response = await API.post("/realtime-data/abnormality", {
      token: "a951dd18-d5b5-4c15-a3ba-062198c45807",
    });
    return response.data;
  } catch (error) {
    // eslint-disable-next-line no-console
    console.error(error); // 에러 처리
  }

  return null;
};

export default getAbnormality;
