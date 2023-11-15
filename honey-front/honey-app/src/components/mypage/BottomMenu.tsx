import { ImageButton, TextButton } from "@components/common/button";
import { Alert, PotModal } from "@components/common/modal";
import { RoomType, UserType } from "@customtype/dataTypes";
import useRouter from "@hooks/useRouter";
import {
  memberListState,
  myRoomListState,
  mypageSelectedRoom,
  selectedMemberState,
} from "@recoil/atom";
import { useState } from "react";
import { useRecoilState, useRecoilValue } from "recoil";
import { onebear, sendPot } from "@assets/images";
import axios from "axios";
import { traceButton } from "../../Tracebutton";

function ButtomMenu() {
  const { routeTo } = useRouter();
  const sendPotImg = sendPot;
  const buttonStyle: string =
    "flex rounded-xl sm:h-[17%] h-[15%] m-2 p-3 sm:w-[50%] w-[60%] sm:text-[20px] text-[13px] justify-center items-center";
  const [selectedRoom] = useRecoilState<RoomType>(mypageSelectedRoom);
  const [memberOpen, setMemberOpen] = useState<boolean>(false);
  const [memberList] = useRecoilState<UserType[]>(memberListState);
  const [, setSelectedMember] = useRecoilState<UserType>(selectedMemberState);
  const token = sessionStorage.getItem("Authorization");
  const { VITE_API_URL } = import.meta.env;
  const myRoomList = useRecoilValue<RoomType[]>(myRoomListState);

  const [alertModal, setAlertModal] = useState<boolean>(false);
  const [alertText, setAlertText] = useState<string>("");

  function showMemberList(): void {
    if (myRoomList.length === 0) {
      setAlertText("참여한 방이 없습니다.");
      setAlertModal(true);
      return;
    }
    setMemberOpen(true);
  }

  function modifyRoom(): void {
    if (myRoomList.length > 0) {
      routeTo("/room/modify");
    }
  }

  function roomPaste(): void {
    const link = `http://rollinghoney.com/room/participate/${selectedRoom.id}`;
    const tempInput = document.createElement("input");
    tempInput.value = link;
    document.body.appendChild(tempInput);
    tempInput.select();
    const copySuccessful = document.execCommand("copy");
    document.body.removeChild(tempInput);

    if (copySuccessful) {
      setAlertText("링크가 클립보드에 복사되었습니다.");
      setAlertModal(true);
    } else {
      setAlertText("복사에 실패하였습니다.");
      setAlertModal(true);
    }
  }

  function exitRoom(): void {
    if (myRoomList.length === 0) {
      setAlertText("참여한 방이 없습니다.");
      setAlertModal(true);
      return;
    }
    const config = {
      "Content-Type": "application/json",
      headers: { Authorization: token },
    };

    axios
      .patch(
        `${VITE_API_URL}/api/v1/rooms/${selectedRoom.id}/out`,
        null,
        config,
      )
      .then(() => {
        window.location.replace("/mypage");
        setAlertText("탈퇴되었습니다.");
        setAlertModal(true);
      })
      .catch((error) => {
        if (error.response.data.errorCode === "002") {
          setAlertText("참가되어있지 않은 방입니다.");
          setAlertModal(true);
        }
        // console.error("Error Delete:", error.response.data.errorCode);
      });
  }

  function sendMessage(member: UserType) {
    setSelectedMember(member);
    routeTo("/send");
  }

  return (
    <>
      <div className="flex w-full">
        <div className="flex flex-col w-[50%] h-[247px] items-center justify-center">
          <TextButton
            text="메시지 보내기"
            color="3"
            className={buttonStyle}
            onClick={() => {
              showMemberList();
              traceButton("메시지 보내기");
            }}
            // onClick={() => showMemberList()}
          />
          {memberOpen && (
            <PotModal
              className=" w-[300px] h-[400px] -translate-x-[150px] translate-y-[150px] sm:w-[500px] sm:h-[600px] sm:-translate-x-[250px] sm:translate-y-[250px] rounded-[36px] shadow-lg flex flex-col items-center px-[15px] py-[15px] bg-cg-6"
              overlay
              openModal={memberOpen}
            >
              <div className="flex h-[8%] mb-4 sm:text-[30px] text-[20px] rounded-xl bg-cg-1 p-4 justify-center text-center items-center">
                참가자 목록
              </div>
              <div className="flex flex-col justify-start items-center w-[100%] h-[80%] overflow-y-auto rounded-[30px] bg-cg-7  text-[20px] px-[15px] py-[15px]">
                {memberList.map((member) => (
                  <div
                    key={member.id}
                    className="flex w-[80%] bg-cg-1 m-2 rounded-xl items-center justify-center"
                  >
                    <div className="w-[100%] flex justify-center items-center">
                      <div className="w-[70%]">{member.name}</div>
                      <ImageButton
                        image={sendPotImg}
                        alt="보내기"
                        className="flex items-center justify-center w-[30%] h-[40px]text-[16px] rounded-md"
                        onClick={() => sendMessage(member)}
                      />
                    </div>
                  </div>
                ))}
              </div>
              <PotModal
                className="w-[80px] h-[25px] -translate-x-[40px] translate-y-[190px] sm:w-[100px] sm:h-[35px] sm:-translate-x-[50px] sm:translate-y-[280px] rounded-[60px] bg-cg-1 flex items-center justify-center hover:scale-125 hover:bg-cg-3"
                overlay={false}
                openModal
              >
                <button
                  type="button"
                  className="sm:w-[100%] sm:h-[100%] sm:text-[24px] text-[15px]"
                  onClick={() => setMemberOpen(false)}
                >
                  닫기
                </button>
              </PotModal>
            </PotModal>
          )}
          <TextButton
            text="초대 링크 복사"
            color="3"
            className={buttonStyle}
            onClick={() => {
              roomPaste();
              traceButton("초대 링크 복사");
            }}
            // onClick={() => roomPaste()}
          />
          <TextButton
            text="방 수정"
            color="3"
            className={buttonStyle}
            onClick={() => {
              modifyRoom();
              traceButton("방 수정");
            }}
            // onClick={() => modifyRoom()}
          />
          <TextButton
            text="방 탈퇴"
            color="3"
            className={buttonStyle}
            onClick={() => exitRoom()}
          />
        </div>
        <div className="flex w-[50%] justify-center items">
          <img src={onebear} alt="푸" className="" />
        </div>
      </div>
      {alertModal && (
        <Alert
          openModal={alertModal}
          closeButton="확인"
          overz="z-[200]"
          text={alertText}
          closeAlert={() => setAlertModal(false)}
        />
      )}
    </>
  );
}

export default ButtomMenu;
