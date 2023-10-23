import { ImageButton } from "@components/common/button";
import Pot from "@components/pot";
import { PotType } from "@customtype/dataTypes";
import potListState from "@recoil/atom/potListState";
import potGroupSelector from "@recoil/selector";
import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";

function Cupboard() {
  const totalPotList = useRecoilValue<PotType[]>(potListState);
  const totalPotCnt: number = totalPotList.length;
  const potList = useRecoilValue<PotType[][]>(potGroupSelector);
  const chunkSize = 3;
  const [currentPage, setCurrentPage] = useState<number>(0);
  const [currentPotList, setCurrentPotList] = useState<PotType[]>(potList[0]);
  const pagination = [...Array(Math.ceil(currentPotList.length / chunkSize))];
  const backArrow = "./src/assets/images/leftArrow.png";
  const maxCupboardIndex = potList.length - 1;

  function potClick(pot: PotType) {
    console.log(pot.content);
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
        </div>
        <div className="flex justify-start w-[20%] rotate-180">
          <ImageButton
            image={backArrow}
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
