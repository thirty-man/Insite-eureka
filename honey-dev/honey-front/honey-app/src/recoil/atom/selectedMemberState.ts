import { UserType } from "@customtype/dataTypes";
import { atom } from "recoil";

const selectedMemberState = atom<UserType>({
  key: "selectedMemberAtom",
  default: {
    id: -1,
    name: "정보가 없습니다.",
  },
});

export default selectedMemberState;
