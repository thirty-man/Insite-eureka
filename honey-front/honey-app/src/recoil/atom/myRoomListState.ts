import { RoomType } from "@customtype/dataTypes";
import { atom } from "recoil";

const dummyMyroom: RoomType[] = [
  {
    roomId: 1,
    roomName: "동현이 방",
    owner: "동현",
    password: null,
  },
  {
    roomId: 2,
    roomName: "동현이 안방",
    owner: "현동이",
    password: null,
  },
  {
    roomId: 3,
    roomName: "동현이 옆방",
    owner: "홍뎐이",
    password: 13579,
  },
  {
    roomId: 6,
    roomName: "동현이 뒷방",
    owner: "홍뎐이",
    password: 13579,
  },
  {
    roomId: 8,
    roomName: "동현이 옆방의뒷방의앞방의옆방이요",
    owner: "홍박사님",
    password: null,
  },
];

const myRoomListState = atom<RoomType[]>({
  key: "myRoomAtoms",
  default: dummyMyroom,
});

export default myRoomListState;
