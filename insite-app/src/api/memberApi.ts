import { memberAPI } from "./Api";

const getButtonList = async () => {
  try {
    const response = await memberAPI.post("/buttons/list", {
      applicationToken: "295c293c-f903-49c8-986d-92d2efe6ccdb",
    });
    return response.data;
  } catch (error) {
    // console.error(error); // 에러 처리
  }

  return [];
};

export default getButtonList;
