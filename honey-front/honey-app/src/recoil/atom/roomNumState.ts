import { atom } from "recoil";

const roomNumState = atom<number>({
  key: "roomNumAtom",
  default: undefined,
});

export default roomNumState;
