import { ApplicationDtoType } from "@customtypes/dataTypes";
import { accumulAPI } from "./Api";

const myApp =
  sessionStorage.getItem("myApp") ||
  `{"applicationId":0,"name":"사이트를 선택해주세요.","applicationUrl":"사이트를 선택해주세요", "applicationToken":"사이트를 선택해주세요"}`;

const data: ApplicationDtoType = JSON.parse(myApp);
const { applicationToken } = data;

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

const getUrlFlowData = async (
  startDateTime: Date,
  endDateTime: Date,
  currentUrl: string,
) => {
  try {
    const response = await accumulAPI.post("/flow/urlflow", {
      applicationToken,
      startDateTime,
      endDateTime,
      currentUrl,
    });
    return response.data;
  } catch (error) {
    // console.error(error); // 에러 처리
  }

  return [];
};

const getButtonDistData = async (startDateTime: Date, endDateTime: Date) => {
  try {
    const response = await accumulAPI.post("/buttons/every-button-rate", {
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

const getAllUrl = async (startDateTime: Date, endDateTime: Date) => {
  try {
    const response = await accumulAPI.post("/currenturl/list", {
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

const getButtonLogs = async (
  startDateTime: Date,
  endDateTime: Date,
  buttonName: string,
) => {
  try {
    const response = await accumulAPI.post("/buttons/logs", {
      applicationToken,
      startDateTime,
      endDateTime,
      buttonName,
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
  getUrlFlowData,
  getButtonDistData,
  getAllUrl,
  getButtonLogs,
};
