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
    // console.error(error); // 에러 처리
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
    // console.error(error); // 에러 처리
  }

  return [];
};

const getButtonDetailData = async (
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
    // console.error(error); // 에러 처리
  }

  return [];
};

const getBounceCountData = async (startDateTime: Date, endDateTime: Date) => {
  try {
    const response = await accumulAPI.post("/flow/bounce", {
      applicationToken,
      startDateTime,
      endDateTime,
    });
    return response.data;
  } catch (error) {
    // console.error(error); // 에러 처리
  }

  return [];
};

const getEnterCountData = async (startDateTime: Date, endDateTime: Date) => {
  try {
    const response = await accumulAPI.post("/flow/entry-enter", {
      applicationToken,
      startDateTime,
      endDateTime,
    });
    return response.data;
  } catch (error) {
    // console.error(error); // 에러 처리
  }

  return [];
};

const getEntryExitData = async (startDateTime: Date, endDateTime: Date) => {
  try {
    const response = await accumulAPI.post("/flow/entry-exit", {
      applicationToken,
      startDateTime,
      endDateTime,
    });
    return response.data;
  } catch (error) {
    // console.error(error); // 에러 처리
  }

  return [];
};

export {
  getRefData,
  getExitData,
  getButtonDetailData,
  getBounceCountData,
  getEnterCountData,
  getEntryExitData,
};
