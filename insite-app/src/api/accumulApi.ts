import { accumulAPI } from "./Api";

const token = "295c293c-f903-49c8-986d-92d2efe6ccdb";

const getRefData = async () => {
  try {
    const response = await accumulAPI.post("/flow/referrer", {
      applicationToken: token,
      currentUrl: "login페이지",
    });
    return response.data;
  } catch (error) {
    // eslint-disable-next-line no-console
    console.error(error); // 에러 처리
  }

  return null;
};

const getExitData = async () => {
  try {
    const response = await accumulAPI.post("/flow/exit", {
      applicationToken: token,
    });
    return response.data;
  } catch (error) {
    // eslint-disable-next-line no-console
    console.error(error); // 에러 처리
  }

  return [];
};

const getButtonDetail = async (name: string) => {
  try {
    const response = await accumulAPI.post("/buttons/click-counts", {
      applicationToken: token,
      buttonName: name,
    });
    return response.data;
  } catch (error) {
    // eslint-disable-next-line no-console
    console.error(error); // 에러 처리
  }

  return [];
};

export { getRefData, getExitData, getButtonDetail };
