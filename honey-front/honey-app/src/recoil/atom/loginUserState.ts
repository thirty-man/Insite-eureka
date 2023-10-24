import { atom } from "recoil";
import { UserType } from "@customtype/dataTypes";

const testUser: UserType = {
  userId: 100,
  nickName: "테스트",
};

const loginUserState = atom<UserType>({
  key: "loginUserAtom",
  default: testUser,
});

export default loginUserState;
