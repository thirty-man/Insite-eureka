import { realTimeAPI } from "./Api";

const applicationToken = "295c293c-f903-49c8-986d-92d2efe6ccdb";

const getUserCount = async () => {
  try {
    const response = await realTimeAPI.post("/realtime-data/user-counts", {
      applicationToken,
    });
    return response.data;
  } catch (error) {
    // console.error(error); // 에러 처리
  }

  return [];
};

const getAbnormality = async () => {
  try {
    const response = await realTimeAPI.post("/realtime-data/abnormality", {
      applicationToken,
    });
    return response.data;
  } catch (error) {
    // console.error(error); // 에러 처리
  }

  return [];
};

const getRefData = async () => {
  try {
    const response = await realTimeAPI.post("/realtime-data/referrer", {
      applicationToken,
    });
    return response.data;
  } catch (error) {
    // console.error(error); // 에러 처리
  }

  return [];
};

const getButtonCount = async () => {
  try {
    const response = await realTimeAPI.post(
      "/realtime-buttons/click-counts-per-user",
      {
        applicationToken,
      },
    );
    return response.data;
  } catch (error) {
    // console.error(error); // 에러 처리
  }

  return [];
};

export { getAbnormality, getRefData, getButtonCount, getUserCount };
