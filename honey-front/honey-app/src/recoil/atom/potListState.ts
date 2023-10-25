import { PotType } from "@customtype/dataTypes";
import { atom } from "recoil";

const testPot: PotType[] = [
  {
    potId: 1,
    honeyCaseType: "2",
    nickname: "유섭",
    content: "안녕 하이",
    isCheck: true,
  },
  {
    potId: 2,
    honeyCaseType: "1",
    nickname: "동현",
    content: "안녕 ㅎㅇ",
    isCheck: false,
  },
  {
    potId: 3,
    honeyCaseType: "5",
    nickname: "현철",
    content: "안녕 현철",
    isCheck: true,
  },
  {
    potId: 4,
    honeyCaseType: "4",
    nickname: "성규",
    content: "안녕 성규",
    isCheck: true,
  },
  {
    potId: 5,
    honeyCaseType: "6",
    nickname: "연수",
    content:
      "그곳에 니가 있다면 힘든하루 지친 네 마음이 내 품에 안겨 쉴 텐데 지금처럼만 날 사랑해줘 난 너만 변하지 않는다면 그곳에 니가 있다면 힘든하루 지친 네 마음이 내 품에 안겨 쉴 텐데 지금처럼만 날 사랑해줘 난 너만 변하지 않는다면 그곳에 니가 있다면 힘든하루 지친 네 마음이 내 품에 안겨 쉴 텐데 지금처럼만 날 사랑해줘 난 너만 변하지 않는다면",
    isCheck: false,
  },
  {
    potId: 6,
    honeyCaseType: "8",
    nickname: "재웅",
    content: "안녕 재웅",
    isCheck: false,
  },
  {
    potId: 7,
    honeyCaseType: "9",
    nickname: "test",
    content: "안녕 test",
    isCheck: false,
  },
  {
    potId: 8,
    honeyCaseType: "6",
    nickname: "연수",
    content: "안녕 연수",
    isCheck: false,
  },
  {
    potId: 9,
    honeyCaseType: "8",
    nickname: "재웅",
    content: "안녕 재웅",
    isCheck: true,
  },
  {
    potId: 10,
    honeyCaseType: "8",
    nickname: "넘친다",
    content: "안녕 재웅",
    isCheck: false,
  },
];

const potListState = atom<PotType[]>({
  key: "potListAtom",
  default: testPot,
});

export default potListState;
