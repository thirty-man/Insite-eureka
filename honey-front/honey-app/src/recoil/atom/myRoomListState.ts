import { RoomType } from "@customtype/dataTypes";
import { atom } from "recoil";

const myRoomListState = atom<RoomType[]>({
  key: "myRoomAtoms",
  default: [],
});

export default myRoomListState;
