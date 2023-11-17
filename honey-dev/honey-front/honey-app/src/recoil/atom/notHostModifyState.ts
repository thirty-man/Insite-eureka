import { atom } from "recoil";

const notHostModifyState = atom<boolean>({
  key: "notHostModifyAtom",
  default: false,
});

export default notHostModifyState;
