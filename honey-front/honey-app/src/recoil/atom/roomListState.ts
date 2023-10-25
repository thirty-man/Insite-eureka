import { RoomType } from "@customtype/dataTypes";
import { atom } from "recoil";

const roomListState = atom<RoomType[]>({
  key: "roomAtoms",
  default: [],
});

export default roomListState;
