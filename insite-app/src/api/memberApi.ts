import { memberAPI } from "./Api";

// const applicationToken = sessionStorage.getItem("ApplicationToken");
const applicationToken = "295c293c-f903-49c8-986d-92d2efe6ccdb";

// 버튼 목록 가져오기
const getButtonList = async () => {
  try {
    const response = await memberAPI.post("/buttons/list", {
      applicationToken,
    });
    return response.data;
  } catch (error) {
    // console.error(error); // 에러 처리
  }

  return [];
};

// 버튼 생성하기
const createButton = async (name: string) => {
  try {
    const response = await memberAPI.post("/buttons", {
      applicationToken,
      name,
    });
    return response.data;
  } catch (error) {
    // console.error(error); // 에러 처리
  }

  return [];
};

// 어플리케이션 정보 받아오기

const getSiteList = async () => {
  try {
    const response = await memberAPI.post("/application/list", {
      applicationToken,
    });
    return response.data;
  } catch (error) {
    // console.error(error); // 에러 처리
  }

  return [];
};

const createStie = async (name: string, applicationUrl: string) => {
  try {
    const response = await memberAPI.post("/application/regist", {
      applicationToken,
      name,
      applicationUrl,
    });
    return response.data;
  } catch (error) {
    // console.error(error); // 에러 처리
  }

  return [];
};

export { getButtonList, createButton, getSiteList, createStie };
