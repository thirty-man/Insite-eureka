import { atom } from "recoil";

const roomAtoms = atom<string>({
  key: "roomAtoms",
  default: "",
});

export default roomAtoms;
