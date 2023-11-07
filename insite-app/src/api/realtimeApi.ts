import API from "./Api";

const getUserCount = async () => {
  try {
    const response = await API.post("/realtime-data/user-counts", {
      token: "a951dd18-d5b5-4c15-a3ba-062198c45807",
    });
    return response.data;
  } catch (error) {
    // eslint-disable-next-line no-console
    console.error(error); // 에러 처리
  }

  return null;
};

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

const getRefData = async () => {
  try {
    const response = await API.post("/realtime-data/referrer", {
      token: "a951dd18-d5b5-4c15-a3ba-062198c45807",
    });
    return response.data;
  } catch (error) {
    // eslint-disable-next-line no-console
    console.error(error); // 에러 처리
  }

  return null;
};

const getButtonCount = async () => {
  try {
    const response = await API.post("/realtime-buttons/click-counts-per-user", {
      token: "a951dd18-d5b5-4c15-a3ba-062198c45807",
    });
    return response.data;
  } catch (error) {
    // eslint-disable-next-line no-console
    console.error(error); // 에러 처리
  }

  return null;
};

export { getAbnormality, getRefData, getButtonCount, getUserCount };
