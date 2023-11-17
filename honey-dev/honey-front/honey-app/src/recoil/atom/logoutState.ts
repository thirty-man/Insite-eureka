import { atom } from "recoil";

const logoutState = atom<boolean>({
  key: "logoutStateAtom",
  default: false,
});

export default logoutState;
