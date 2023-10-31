import { atom } from "recoil";

const completeSendState = atom<boolean>({
  key: "completeSendAtom",
  default: false,
});

export default completeSendState;
