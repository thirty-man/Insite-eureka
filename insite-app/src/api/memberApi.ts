import { ApplicationDtoType } from "@customtypes/dataTypes";
import { memberAPI } from "./Api";

const myApp =
  sessionStorage.getItem("myApp") ||
  `{"applicationId":0,"name":"사이트를 선택해주세요.","applicationUrl":"사이트를 선택해주세요", "applicationToken":"사이트를 선택해주세요"}`;

const data: ApplicationDtoType = JSON.parse(myApp);
const { applicationToken } = data;

// 버튼 목록 가져오기
const getButtonList = async () => {
  try {
    const response = await memberAPI.post("/buttons/list", {
      applicationToken,
    });
    return response.data;
  } catch (error) {
    console.error("memberApi - getButtonList err", error); // 에러 처리
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
    return response;
  } catch (error) {
    console.error("memberApi - createButton err", error); // 에러 처리
  }

  return [];
};

// 어플리케이션 정보 받아오기

const getSiteList = async () => {
  try {
    const response = await memberAPI.get("/application/list");
    return response.data;
  } catch (error) {
    console.error("memberApi - getSiteList err", error); // 에러 처리
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
    return response;
  } catch (error) {
    console.error("memberApi - createStie err", error); // 에러 처리
  }

  return [];
};

export { getButtonList, createButton, getSiteList, createStie };
