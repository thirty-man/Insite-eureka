import { RoomType } from "@customtype/dataTypes";
import { atom } from "recoil";

const selectedRoomState = atom<RoomType>({
  key: "selectedRoomAtom",
  default: undefined,
});

export default selectedRoomState;
