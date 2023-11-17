import { RoomType } from "@customtype/dataTypes";
import { atom } from "recoil";

const mypageSelectedRoom = atom<RoomType>({
  key: "mypageSelectedRoomAtom",
  default: undefined,
});

export default mypageSelectedRoom;
