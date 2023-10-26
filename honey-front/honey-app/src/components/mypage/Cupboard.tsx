import { ImageButton } from "@components/common/button";
import { PotModal } from "@components/common/modal";
import Pot from "@components/pot";
import { PotType } from "@customtype/dataTypes";
import potListState from "@recoil/atom/potListState";
import { potGroupSelector } from "@recoil/selector";
import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { leftArrow, rightArrow } from "@assets/images";

function Cupboard() {
  // 진짜 전체 유저가 그 방에서 가진 팟리스트
  const totalPotList = useRecoilValue<PotType[]>(potListState);
  console.log(totalPotList, "1");
  // 전체를 페이지별 * 9개씩 나눠놓은 리스트
  const potList = useRecoilValue<PotType[][]>(potGroupSelector);
  console.log(potList, "2");
  // 현재 페이지(이건 페이지네이션)
  const [currentPage, setCurrentPage] = useState<number>(0);
  console.log(currentPage, "3");
  // 현재 페이지에있는 팟을 가져오는것
  const [currentPotList, setCurrentPotList] = useState<PotType[]>([]);
  console.log(currentPotList, "4");
  // 모달오픈클로즈
  const [potOpen, setPotOpen] = useState<boolean>(false);
  console.log(potOpen, "5");
  // 선택하면 여기에 선택한 팟 정보를 넘겨서 모달로 띄움
  const [selectedPot, setSelectedPot] = useState<PotType | null>(null);
  console.log(selectedPot, "6");

  const totalPotCnt: number = totalPotList.length;
  console.log(totalPotCnt, "7");
  const chunkSize: number = 3;
  const maxCupboardIndex: number = potList.length - 1;
  console.log(maxCupboardIndex, "8");
  const [pagination, setPagination] = useState<PotType[] | []>([
    ...Array(Math.ceil(currentPotList.length / chunkSize)),
  ]);
  console.log(pagination, "10");

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
    setCurrentPotList(potList[currentPage]);
  }, [currentPage, potList]);

  useEffect(() => {
    setPagination(potList[currentPage]);
  }, [currentPage, potList]);

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
          {currentPotList
            .filter((pot) => pot)
            .map((pot) => (
              <div key={pot.potId} className="">
                <Pot
                  key={pot.potId}
                  potNum={pot.isCheck ? pot.honeyCaseType || 0 : 0}
                  onClick={() => potClick(pot)}
                />
              </div>
            ))}

          {/* {pagination.map((_, groupIndex) => (
            <div key={pagination[groupIndex].potId} className="">
              {currentPotList
                .slice(groupIndex * 3, (groupIndex + 1) * 3)
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
            </div>
          ))} */}
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
