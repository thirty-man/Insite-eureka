import { atom } from "recoil";

const successCreateRoomState = atom<boolean>({
  key: "successCreateRoomState",
  default: false,
});

export default successCreateRoomState;
