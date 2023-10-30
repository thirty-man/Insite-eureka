import { atom } from "recoil";

const participateState = atom<boolean>({
  key: "participateAtom",
  default: false,
});

export default participateState;
