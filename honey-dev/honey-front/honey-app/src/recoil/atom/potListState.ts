import { PotType } from "@customtype/dataTypes";
import { atom } from "recoil";

const potListState = atom<PotType[]>({
  key: "potListAtom",
  default: [],
});

export default potListState;
