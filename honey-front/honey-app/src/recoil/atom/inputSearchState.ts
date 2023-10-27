import { atom } from "recoil";

const inputSearchState = atom<string>({
  key: "inputSearchAtom",
  default: "",
});

export default inputSearchState;
