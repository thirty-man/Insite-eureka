import { atom } from "recoil";

const modifyState = atom<boolean>({
  key: "modifyAtoms",
  default: false,
});

export default modifyState;
