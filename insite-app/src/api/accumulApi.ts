import { accumulAPI } from "./Api";

const applicationToken = "295c293c-f903-49c8-986d-92d2efe6ccdb";

const getRefData = async (startDateTime: Date, endDateTime: Date) => {
  try {
    const response = await accumulAPI.post("/flow/referrer", {
      applicationToken,
      startDateTime,
      endDateTime,
    });
    return response.data;
  } catch (error) {
    // eslint-disable-next-line no-console
    console.error(error); // 에러 처리
  }

  return [];
};

const getExitData = async (startDateTime: Date, endDateTime: Date) => {
  try {
    const response = await accumulAPI.post("/flow/exit", {
      applicationToken,
      startDateTime,
      endDateTime,
    });
    return response.data;
  } catch (error) {
    // eslint-disable-next-line no-console
    console.error(error); // 에러 처리
  }

  return [];
};

const getButtonDetail = async (
  name: string,
  startDateTime: Date,
  endDateTime: Date,
) => {
  try {
    const response = await accumulAPI.post("/buttons/click-counts", {
      applicationToken,
      buttonName: name,
      startDateTime,
      endDateTime,
    });
    return response.data;
  } catch (error) {
    // eslint-disable-next-line no-console
    console.error(error); // 에러 처리
  }

  return [];
};

const getBounceCount = async (startDateTime: Date, endDateTime: Date) => {
  try {
    const response = await accumulAPI.post("/flow/bounce", {
      applicationToken,
      startDateTime,
      endDateTime,
    });
    return response.data;
  } catch (error) {
    // eslint-disable-next-line no-console
    console.error(error); // 에러 처리
  }

  return [];
};

export { getRefData, getExitData, getButtonDetail, getBounceCount };
