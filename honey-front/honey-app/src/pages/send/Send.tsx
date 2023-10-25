import { nextArrow, prevArrow } from "@assets/images";
import pots from "@assets/images/pots";
import { TextButton } from "@components/common/button";
import { Modal } from "@components/common/modal";
import { UserType } from "@customtype/dataTypes";
import { AccessError } from "@pages/error";
import { selectedMemberState } from "@recoil/atom";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";

function Send() {
  const selectedMember = useRecoilValue<UserType>(selectedMemberState);
  const navi = useNavigate();
  const basicType = "sm:text-[30px] text-[20px] rounded-xl p-1 m-2";
  const [openPotModal, setOpenPotModal] = useState<boolean>(false);
  const [selectedPotIdx, setSelectedPotIdx] = useState(1);

  function send() {
    // 전송 api요청
  }
  function goToBack() {
    navi(-1);
  }

  // if (selectedMember === undefined) {
  //   return <AccessError />;
  // }
  // {selectedMember.name}

  const nextPot = () => {
    setSelectedPotIdx((prev) => (prev + 1) % pots.length);
  };

  const prevPot = () => {
    setSelectedPotIdx((prev) => (prev - 1 + pots.length) % pots.length);
  };

  //랜덤 꿀단지일경우(potIdx==0)=> send할 때, if문으로 Math.random함수로 인덱스 정해주면 될 듯

  return (
    <>
      <div className="flex h-full justify-center items-center">
        <div className="flex justify-center items-center h-[80%] w-full bg-cover bg-writePaper bg-size">
          <div className="flex flex-col justify-center items-center h-[90%] w-full">
            <div className="flex h-[11%] justify-center items-center sm:text-[30px] text-[20px]">
              <p className="text-emerald-800 ">
                유섭{/* {selectedMember.name} */}
              </p>
              님에게 보내는 편지
            </div>
            <textarea className="h-[78%] w-[80%] bg-inherit sm:text-[20px] text-[15px] p-10" />
            <div className="h-[10%] w-[40%] flex justify-between items-center">
              <TextButton
                text="전송"
                color=""
                className={basicType}
                onClick={() => setOpenPotModal(true)}
              />
              <TextButton
                text="취소"
                color=""
                className={basicType}
                onClick={() => {
                  goToBack();
                }}
              />
            </div>
          </div>
        </div>
      </div>
      {openPotModal && (
        <Modal
          className="fixed w-[360px] h-[360px] bottom-[50%] left-[50%] rounded-[8px] bg-[#BAB2B2] -translate-x-[180px] translate-y-[120px] z-[150]"
          overlay
          openModal={openPotModal}
        >
          <div className="w-full h-full flex flex-col items-center justify-evenly">
            <div className="w-full h-[80%]">
              <div className="pt-[15px] text-[36px]">꿀단지 선택</div>
              <div className="flex flex-row items-center justify-center">
                <div className="pt-[20px] flex flex-row items-center justify-around">
                  <img
                    src={prevArrow}
                    className="w-[30px] h-[40px]"
                    onClick={prevPot}
                  />
                  <img
                    src={pots[selectedPotIdx]}
                    className="w-[150px] h-[150px]"
                    alt="Selected pot"
                  />
                  <img
                    src={nextArrow}
                    className="w-[30px] h-[40px]"
                    onClick={nextPot}
                  />
                </div>
              </div>
            </div>
            <div className="w-full h-[10%] pb-[100px] ">
              <div className="w-full h-[40px] flex flex-row items-center justify-center">
                <div className="w-[80%] h-full flex flex-row items-center justify-around">
                  <button
                    type="button"
                    className="w-[100px] h-[35px] rounded-[60px] bg-cg-4 flex items-center justify-center"
                    onClick={send}
                  >
                    보내기
                  </button>
                  <button
                    type="button"
                    className="w-[100px] h-[35px] rounded-[60px] bg-cg-4 flex items-center justify-center"
                    onClick={() => setOpenPotModal(false)}
                  >
                    닫기
                  </button>
                </div>
              </div>
            </div>
          </div>
        </Modal>
      )}
    </>
  );
}

export default Send;
