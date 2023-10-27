import { atom } from "recoil";
import { UserType } from "@customtype/dataTypes";

const loginUserState = atom<UserType>({
  key: "loginUserAtom",
  default: undefined,
});

export default loginUserState;
