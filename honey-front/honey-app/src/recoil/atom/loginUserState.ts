import { atom } from "recoil";
import { UserType } from "@customtype/dataTypes";

const testUser: UserType = {
  userId: 1,
  name: "동현",
};

const loginUserState = atom<UserType>({
  key: "loginUserAtom",
  default: testUser,
});

export default loginUserState;
