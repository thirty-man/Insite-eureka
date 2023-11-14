import { ApplicationDtoType } from "@customtypes/dataTypes";
import { realTimeAPI } from "./Api";

const myApp =
  sessionStorage.getItem("myApp") ||
  `{"applicationId":0,"name":"사이트를 선택해주세요.","applicationUrl":"사이트를 선택해주세요", "applicationToken":"사이트를 선택해주세요", "createTime" : "사이트를 선택해주세요"}`;

const data: ApplicationDtoType = JSON.parse(myApp);
const { applicationToken } = data;

const getUserCount = async () => {
  try {
    const response = await realTimeAPI.post("/realtime-data/user-counts", {
      applicationToken,
    });
    return response.data;
  } catch (error) {
    console.error("memberApi - getUserCount err", error); // 에러 처리
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
    console.error("memberApi - getAbnormality err", error); // 에러 처리
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
    console.error("memberApi - getRefData err", error); // 에러 처리
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
    console.error("memberApi - getButtonCount err", error); // 에러 처리
  }

  return [];
};

export { getAbnormality, getRefData, getButtonCount, getUserCount };
