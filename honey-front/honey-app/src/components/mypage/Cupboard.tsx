import { ImageButton } from "@components/common/button";
import Modal from "@components/common/modal";
import Pot from "@components/pot";
import { PotType } from "@customtype/dataTypes";
import potListState from "@recoil/atom/potListState";
import potGroupSelector from "@recoil/selector";
import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";

function Cupboard() {
  const backArrow: string = "./src/assets/images/leftArrow.png";
  const forwardArrow: string = "./src/assets/images/rightArrow.png";

  const totalPotList = useRecoilValue<PotType[]>(potListState);
  const potList = useRecoilValue<PotType[][]>(potGroupSelector);
  const [currentPage, setCurrentPage] = useState<number>(0);
  const [currentPotList, setCurrentPotList] = useState<PotType[]>(potList[0]);
  const [potOpen, setPotOpen] = useState<boolean>(false);
  const [selectedPot, setSelectedPot] = useState<PotType>();

  const totalPotCnt: number = totalPotList.length;
  const chunkSize: number = 3;
  const maxCupboardIndex: number = potList.length - 1;
  const pagination = [...Array(Math.ceil(currentPotList.length / chunkSize))];

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

  return (
    <>
      <div className="flex items-center w-full justify-center sm:h-[550px] h-[350px] bg-cupboard bg-cover bg-size">
        <div className="flex justify-start w-[20%]">
          <ImageButton
            image={backArrow}
            alt="이전 찬장"
            className=""
            onClick={() => goToBack()}
          />
        </div>
        <div className="flex flex-col w-[52%] mb-3 h-[80%] items-center">
          <div className="flex flex-col h-[95%] w-full justify-start">
            {pagination.map((_, groupIndex) => (
              <div
                key={currentPotList[groupIndex].potId}
                className="w-full h-[33%]"
              >
                <div className="flex flex-col justify-end h-[90%] w-full">
                  <div className="flex">
                    {currentPotList
                      .slice(
                        groupIndex * chunkSize,
                        (groupIndex + 1) * chunkSize,
                      )
                      .map((pot) => (
                        <Pot
                          key={pot.potId}
                          potNum={pot.isCheck ? pot.honeyCaseType : "0"}
                          onClick={() => potClick(pot)}
                        />
                      ))}
                  </div>
                  <div className="h-[10%] bg-cg-10" />
                </div>
              </div>
            ))}
          </div>
          {potOpen && (
            <Modal
              className=" w-[300px] h-[400px] -translate-x-[150px] translate-y-[150px] sm:w-[500px] sm:h-[600px] sm:-translate-x-[250px] sm:translate-y-[230px] rounded-[36px] shadow-lg flex items-center justify-center px-[15px] py-[15px] bg-cg-6"
              overlay
              openModal={potOpen}
            >
              <div className="w-full h-full flex items-center justify-center bg-size bg-paper bg-cover">
                <div className="flex flex-col h-[80%] w-full justify-center items-center">
                  <div className="flex items-center h-[15%] sm:text-[30px] text-[20px]">
                    From:{" "}
                    {selectedPot === undefined ? "" : selectedPot.nickname}
                  </div>
                  <div className="flex justify-center h-[80%] w-full">
                    <div className="flex h-[80%] w-[80%] text-[15px] sm:text-[20px] break-words overflow-y-auto justify-center items-center">
                      {selectedPot === undefined
                        ? "항아리가 없어요"
                        : selectedPot.content}
                    </div>
                  </div>
                </div>
              </div>
              <Modal
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
              </Modal>
            </Modal>
          )}
        </div>
        <div className="flex justify-end w-[20%]">
          <ImageButton
            image={forwardArrow}
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
