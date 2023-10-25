import { UserType } from "@customtype/dataTypes";
import { atom } from "recoil";

const memberListState = atom<UserType[]>({
  key: "memberListAtoms",
  default: [],
});

export default memberListState;
