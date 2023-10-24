import { UserType } from "@customtype/dataTypes";
import { atom } from "recoil";

const selectedMemberState = atom<UserType>({
  key: "selectedMemberAtom",
  default: undefined,
});

export default selectedMemberState;
