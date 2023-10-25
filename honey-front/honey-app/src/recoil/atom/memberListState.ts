import { UserType } from "@customtype/dataTypes";
import { atom } from "recoil";

const dummyMember: UserType[] = [
  {
    userId: 1,
    nickName: "동현",
  },
  {
    userId: 2,
    nickName: "유섭",
  },
  {
    userId: 3,
    nickName: "성규",
  },
  {
    userId: 4,
    nickName: "연수",
  },
  {
    userId: 5,
    nickName: "재웅",
  },
  {
    userId: 6,
    nickName: "현철",
  },
  {
    userId: 7,
    nickName: "동현",
  },
  {
    userId: 8,
    nickName: "유섭",
  },
  {
    userId: 9,
    nickName: "성규",
  },
  {
    userId: 10,
    nickName: "연수",
  },
  {
    userId: 11,
    nickName: "재웅",
  },
  {
    userId: 12,
    nickName: "현철",
  },
];

const memberListState = atom<UserType[]>({
  key: "memberListAtoms",
  default: dummyMember,
});

export default memberListState;
