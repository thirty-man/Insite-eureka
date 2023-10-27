import { nextArrow, prevArrow } from "@assets/images";
import pots from "@assets/images/pots";
import { ImageButton, TextButton } from "@components/common/button";
import { TextInput } from "@components/common/input";
import { Modal } from "@components/common/modal";
import { RoomType, UserType } from "@customtype/dataTypes";
// import { AccessError } from "@pages/error";
import { selectedMemberState, selectedRoomState } from "@recoil/atom";
import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";

function Send() {
  const selectedMember = useRecoilValue<UserType>(selectedMemberState);
  const navi = useNavigate();
  const basicType = "sm:text-[30px] text-[20px] rounded-xl p-1 m-2";
  const [openPotModal, setOpenPotModal] = useState<boolean>(false);
  const [selectedPotIdx, setSelectedPotIdx] = useState<number>(1);
  const token = sessionStorage.getItem("Authorization");
  const [content, setContent] = useState("");
  const [nickName, setNickName] = useState("");
  const selectedRoom = useRecoilValue<RoomType>(selectedRoomState);

  const messageSendReqDto = {
    member_id_to: selectedMember.id,
    room_id: selectedRoom.id,
    nick_name: nickName,
    content,
    honey_case_type: String(selectedPotIdx),
  };
  console.log(selectedRoom.id);
  console.log(nickName);

  function send() {
    // 전송 api요청
    const config = {
      "Content-Type": "application/json",
      headers: { Authorization: token },
    };

    axios
      .post(`http://localhost:8080/api/v1/messages`, messageSendReqDto, config)
      .then((response) => {
        console.log("response : ", response);
      })
      .catch((error) => {
        if (error.response.data.errorCode === "000") {
          console.error("Error Send:", error.response.data.errorCode);
          // 000에러를 받았을 때 JWT에서 리프래쉬토큰을 다시 호출하는 axios를 작성
          // 그런데 여기서 no를 반환받으면 다시 로그인을 시켜야 한다
        }
        console.error("Error Send:", error.response.data.errorCode);
      });
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

  // 랜덤 꿀단지일경우(potIdx==0)=> send할 때, if문으로 Math.random함수로 인덱스 정해주면 될 듯

  return (
    <>
      <div className="flex h-full justify-center items-center">
        <div className="flex justify-center items-center h-[80%] w-full bg-cover bg-writePaper bg-size">
          <div className="flex flex-col justify-center items-center h-[90%] w-full">
            <div className="flex h-[11%] justify-center items-center sm:text-[30px] text-[20px]">
              <p className="text-emerald-800 ">{selectedMember.name}</p>
              님에게 보내는 편지
            </div>
            <textarea
              className="h-[78%] w-[80%] bg-inherit sm:text-[20px] text-[15px] p-10"
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />
            <div className="flex h-[7%] w-[80%] mb-3 justify-end">
              <TextInput
                holder="보낼 이름을 입력하세요"
                value={nickName}
                className="bg-cg-9"
                readonly={false}
                onChange={(e) => setNickName(e.target.value)}
              />
            </div>
            <div className="h-[10%] w-[40%] flex justify-between items-center">
              <TextButton
                text="전송"
                color="4"
                className={basicType}
                onClick={() => setOpenPotModal(true)}
              />
              <TextButton
                text="취소"
                color="4"
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
                  <ImageButton
                    image={prevArrow}
                    className="w-[30px] h-[40px]"
                    onClick={prevPot}
                    alt="이전"
                  />
                  <img
                    src={pots[selectedPotIdx]}
                    className="w-[150px] h-[150px]"
                    alt="Selected pot"
                  />
                  <ImageButton
                    image={nextArrow}
                    alt="다음"
                    className="w-[30px] h-[40px]"
                    onClick={nextPot}
                  />
                </div>
              </div>
            </div>
            <div className="w-full h-[10%] pb-[100px] ">
              <div className="w-full h-[40px] flex flex-row items-center justify-center">
                <div className="w-[80%] h-full flex flex-row items-center justify-around">
                  <TextButton
                    text="보내기"
                    color="2"
                    className="w-[100px] h-[35px] rounded-[60px] flex items-center justify-center"
                    onClick={() => send()}
                  />
                  <TextButton
                    text="닫기"
                    color="2"
                    className="w-[100px] h-[35px] rounded-[60px] flex items-center justify-center"
                    onClick={() => setOpenPotModal(false)}
                  />
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
