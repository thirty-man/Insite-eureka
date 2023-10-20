import { RoomType } from "@customtype/dataTypes";
import { atom } from "recoil";

const dummyRoom1: RoomType[] = [
  {
    roomId: 1,
    roomName: "동이 방",
    owner: "동현김",
    password: null,
  },
  {
    roomId: 2,
    roomName: "현이 안방",
    owner: "김동현1",
    password: 12345,
  },
  {
    roomId: 3,
    roomName: "현현이 옆방dddddddddddddddd",
    owner: "현동김2",
    password: 54321,
  },
  {
    roomId: 4,
    roomName: "현현이 옆방44",
    owner: "현동김3",
    password: null,
  },
  {
    roomId: 5,
    roomName: "현현이 옆방44",
    owner: "현동김3",
    password: null,
  },
];

const roomListState = atom<RoomType[]>({
  key: "roomAtoms",
  default: dummyRoom1,
});

export default roomListState;
