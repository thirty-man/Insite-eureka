import { RoomType } from "@customtype/dataTypes";
import { atom } from "recoil";

const selectedRoomState = atom<RoomType | null>({
  key: "selectedRoomAtom",
  default: null,
});

export default selectedRoomState;
