import { ImageButton } from "@components/common/button";
import { PotModal } from "@components/common/modal";
import Pot from "@components/pot";
import { PotType, RoomType } from "@customtype/dataTypes";
import potListState from "@recoil/atom/potListState";
import { potGroupSelector } from "@recoil/selector";
import { useEffect, useState } from "react";
import { useRecoilValue, useRecoilState } from "recoil";
import { leftArrow, rightArrow } from "@assets/images";
import { selectedRoomState } from "@recoil/atom";
import axios from "axios";

function Cupboard() {
  const [totalPotList, setTotalPotList] =
    useRecoilState<PotType[]>(potListState);
  const potList = useRecoilValue<PotType[][]>(potGroupSelector);
  const [currentPage, setCurrentPage] = useState<number>(0);
  const [potOpen, setPotOpen] = useState<boolean>(false);
  const [selectedPot, setSelectedPot] = useState<PotType | null>(null);
  const totalPotCnt: number = totalPotList.length;
  const maxCupboardIndex: number = potList.length - 1;
  const [selectedRoom] = useRecoilState<RoomType>(selectedRoomState);
  const token = sessionStorage.getItem("Authorization");

  useEffect(() => {
    if (selectedRoom) {
      const config = {
        headers: {
          Authorization: token,
        },
      };

      axios
        .get(
          `http://localhost:8080/api/v1/rooms/${selectedRoom.id}/message-list`,
          config,
        )
        .then((response) => {
          const { data } = response;
          const getMessageList = data.messageListDtoList;
          setTotalPotList(getMessageList);
          console.log(getMessageList);
        })
        .catch((error) => {
          console.error("Error fetching room list:", error);
        });
    }
  }, [selectedRoom, token, setTotalPotList, currentPage, selectedPot]);

  function potClick(pot: PotType) {
    setSelectedPot(pot);
    const config = {
      headers: {
        Authorization: token,
      },
    };

    axios
      .get(`http://localhost:8080/api/v1/messages/${pot.id}`, config)
      .then((response) => {
        const { data } = response;
        if (!data) {
          alert("개봉일이 안됐어요");
        } else {
          setSelectedPot(data);
          setPotOpen(true);
        }
      })
      .catch((error) => {
        console.error("Error fetching room list:", error);
      });
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
          {potList[currentPage] &&
            potList[currentPage]
              .filter((pot) => pot)
              .map((pot) => (
                <div key={pot.id} className="">
                  <Pot
                    key={pot.id}
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
                    From: {selectedPot ? selectedPot.nickName : ""}
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
