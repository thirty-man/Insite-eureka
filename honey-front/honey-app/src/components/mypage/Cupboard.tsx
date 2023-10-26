import { ImageButton } from "@components/common/button";
import { PotModal } from "@components/common/modal";
import Pot from "@components/pot";
import { PotType } from "@customtype/dataTypes";
import potListState from "@recoil/atom/potListState";
import { potGroupSelector } from "@recoil/selector";
import { useEffect, useState } from "react";
import { useRecoilValue, useRecoilState } from "recoil";
import { leftArrow, rightArrow } from "@assets/images";

const testPot: PotType[] = [
  {
    potId: 1,
    honeyCaseType: "8",
    nickname: "유섭",
    content: "안녕 하이",
    isCheck: true,
  },
  {
    potId: 2,
    honeyCaseType: "9",
    nickname: "동현",
    content: "안녕 ㅎㅇ",
    isCheck: false,
  },
  {
    potId: 3,
    honeyCaseType: "1",
    nickname: "현철",
    content: "안녕 현철",
    isCheck: true,
  },
  {
    potId: 4,
    honeyCaseType: "2",
    nickname: "성규",
    content: "안녕 성규",
    isCheck: true,
  },
  {
    potId: 5,
    honeyCaseType: "3",
    nickname: "연수",
    content:
      "그곳에 니가 있다면 힘든하루 지친 네 마음이 내 품에 안겨 쉴 텐데 지금처럼만 날 사랑해줘 난 너만 변하지 않는다면 그곳에 니가 있다면 힘든하루 지친 네 마음이 내 품에 안겨 쉴 텐데 지금처럼만 날 사랑해줘 난 너만 변하지 않는다면 그곳에 니가 있다면 힘든하루 지친 네 마음이 내 품에 안겨 쉴 텐데 지금처럼만 날 사랑해줘 난 너만 변하지 않는다면",
    isCheck: false,
  },
  {
    potId: 6,
    honeyCaseType: "4",
    nickname: "재웅",
    content: "안녕 재웅",
    isCheck: false,
  },
  {
    potId: 7,
    honeyCaseType: "5",
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
    honeyCaseType: "7",
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

function Cupboard() {
  const [totalPotList, setTotalPotList] =
    useRecoilState<PotType[]>(potListState);
  const potList = useRecoilValue<PotType[][]>(potGroupSelector);
  const [currentPage, setCurrentPage] = useState<number>(0);
  const [currentPotList, setCurrentPotList] = useState<PotType[]>([]);
  const [potOpen, setPotOpen] = useState<boolean>(false);
  const [selectedPot, setSelectedPot] = useState<PotType | null>(null);
  const totalPotCnt: number = totalPotList.length;
  const maxCupboardIndex: number = potList.length - 1;

  function potClick(pot: PotType) {
    setSelectedPot(pot);
    setPotOpen(true);
  }

  function goToBack() {
    setCurrentPage(currentPage - 1);
    if (currentPage <= 0) {
      setCurrentPage(maxCupboardIndex);
    }
  }

  function goToNext() {
    setCurrentPage(currentPage + 1);
    if (currentPage >= maxCupboardIndex) {
      setCurrentPage(0);
    }
  }

  useEffect(() => {
    setTotalPotList(testPot);
    setCurrentPotList(potList[currentPage]);
  }, [currentPage, potList, setTotalPotList]);

  return (
    <>
      <div className="flex items-center w-full justify-center sm:h-[550px] h-[350px] bg-cupboard bg-cover bg-size">
        <div className="flex justify-start w-[10%]">
          <ImageButton
            image={leftArrow}
            alt="이전 찬장"
            className=""
            onClick={() => goToBack()}
          />
        </div>
        <div className="grid grid-cols-3 grid-rows-3 gap-y-16 w-[60%] h-[69%] mb-[5%] items-center">
          {currentPotList &&
            currentPotList
              .filter((pot) => pot)
              .map((pot) => (
                <div key={pot.potId} className="">
                  <Pot
                    key={pot.potId}
                    potNum={pot.isCheck ? pot.honeyCaseType || "0" : "0"}
                    onClick={() => potClick(pot)}
                  />
                </div>
              ))}
          {selectedPot && potOpen && (
            <PotModal
              className="fixed bottom-1/2 left-1/2 z-[99] w-[300px] h-[400px] -translate-x-[150px] translate-y-[150px] sm:w-[500px] sm:h-[600px] sm:-translate-x-[250px] sm:translate-y-[230px] rounded-[36px] shadow-lg flex items-center justify-center px-[15px] py-[15px] bg-cg-6"
              overlay
              openModal={potOpen}
            >
              <div className="w-full h-full flex items-center justify-center bg-size bg-paper bg-cover">
                <div className="flex flex-col h-[80%] w-full justify-center items-center">
                  <div className="flex items-center h-[15%] sm:text-[30px] text-[20px]">
                    From: {selectedPot ? selectedPot.nickname : ""}
                  </div>
                  <div className="flex justify-center h-[80%] w-full">
                    <div className="flex h-[80%] w-[80%] text-[15px] sm:text-[20px] break-words overflow-y-auto justify-center items-center">
                      {selectedPot === null
                        ? "항아리가 없어요"
                        : selectedPot.content}
                    </div>
                  </div>
                </div>
              </div>
              <PotModal
                className="w-[80px] h-[25px] -translate-x-[40px] translate-y-[130px] sm:w-[100px] sm:h-[35px] sm:-translate-x-[50px] sm:translate-y-[210px] rounded-[60px] bg-cg-2 flex items-center justify-center"
                overlay={false}
                openModal
              >
                <button
                  type="button"
                  className="sm:w-[100%] sm:h-[100%] sm:text-[24px] text-[15px] hover:scale-125 hover:bg-cg-3 rounded-[60px]"
                  onClick={() => setPotOpen(false)}
                >
                  닫기
                </button>
              </PotModal>
            </PotModal>
          )}
        </div>
        <div className="flex justify-end w-[10%]">
          <ImageButton
            image={rightArrow}
            alt="다음 찬장"
            className=""
            onClick={() => goToNext()}
          />
        </div>
      </div>
      <div className="sm:text-[20px] text-[10px]">
        내 항아리 수: {totalPotCnt}개
      </div>
    </>
  );
}

export default Cupboard;
