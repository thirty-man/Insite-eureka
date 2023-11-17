import { useEffect, useState } from "react";
import useRouter from "@hooks/useRouter";
import { useRecoilState } from "recoil";
import { mypageSelectedRoom, notHostModifyState } from "@recoil/atom";
import axios from "axios";
import { Alert } from "@components/common/modal";
import modifyState from "@recoil/atom/modifyState";

function ModifyRoom() {
  const { routeTo } = useRouter();
  const [roomName, setRoomName] = useState<string>("");
  const [boxChecked, setBoxChecked] = useState<boolean>(false);
  const [roomNameFocused, setRoomNameFocused] = useState<boolean>(false);
  const [roomPassword, setRoomPassword] = useState<string>("");
  const [roomPasswordFocused, setRoomPasswordFocused] =
    useState<boolean>(false);
  const [alertModal, setAlertModal] = useState<boolean>(false);
  const [alertText, setAlertText] = useState<string>("");
  const [, setModified] = useRecoilState<boolean>(modifyState);
  const [, setNotHostModified] = useRecoilState<boolean>(notHostModifyState);

  const token = sessionStorage.getItem("Authorization");
  const [selectedRoom] = useRecoilState(mypageSelectedRoom);
  const { VITE_API_URL } = import.meta.env;

  useEffect(() => {
    setRoomName(selectedRoom.roomTitle);
  }, [setRoomName, selectedRoom]);

  const handleInputFocus = () => {
    setRoomNameFocused(true);
  };

  const handleInputBlur = () => {
    if (!roomName.trim()) {
      setRoomNameFocused(false);
    }
  };

  const handleRoomPasswordFocus = () => {
    setRoomPasswordFocused(true);
  };

  const handleRoomPasswordBlur = () => {
    if (!roomPassword.trim()) {
      setRoomPasswordFocused(false);
    }
  };

  const handleRoomName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRoomName(e.target.value);
  };

  const handleRoomPassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    const password = e.target.value;
    const newPassword = password.replace(/\s/g, "");
    setRoomPassword(newPassword);
  };

  const handleCheckboxToggle = () => {
    setBoxChecked(!boxChecked);
    setRoomPasswordFocused(false);
  };

  const submitModifyRoom = () => {
    const newRoomName = roomName.replace(/^\s+|\s+$|\n/g, "");
    setRoomName(newRoomName);
    if (newRoomName.trim() === "") {
      setAlertText("방제목을 입력하세요.");
      setAlertModal(true);
      // alert("방제목을 입력하세요.");
      return;
    }
    if (newRoomName.trim().length >= 50) {
      setAlertText("방제목은 50자 이하로 작성되어야합니다.");
      setAlertModal(true);
      // alert("방제목은 50자 이하로 작성되어야합니다.");
      return;
    }

    if (boxChecked) {
      const password = roomPassword;
      const passwordPattern = /^[A-Za-z0-9@$!%*?&]{8,16}$/;
      if (passwordPattern.test(password)) {
        setRoomPassword(password);
      } else {
        if (password === null || password === "") {
          setAlertText("비밀번호를 설정해주세요.");
          setAlertModal(true);
          // alert("비밀번호를 설정해주세요.");
          return;
        }
        setAlertText(
          "8자 이상 16자 이하의 영문 대소문자, 숫자, 특수문자를 입력해야 합니다.",
        );
        setAlertModal(true);
        return;
        // alert(
        //   "8자 이상 16자 이하의 영문 대소문자, 숫자, 특수문자를 입력해야 합니다.",
        // );
      }
    }

    const config = {
      headers: {
        Authorization: token,
      },
    };

    const roomModifyReqDto = {
      roomTitle: roomName,
      password: boxChecked ? roomPassword : null,
    };

    axios
      .patch(
        `${VITE_API_URL}/api/v1/rooms/${selectedRoom.id}/update`,
        roomModifyReqDto,
        config,
      )
      .then(() => {
        setModified(true);
        routeTo("/mypage");
      })
      .catch((error) => {
        if (error.response.data.errorCode === "003") {
          setNotHostModified(true);
          routeTo("/mypage");
        }
      });
  };

  return (
    <>
      <div className="text-[30px] text-white">방 수정</div>
      <div className="w-full h-full px-[30px]">
        <div className="w-full h-full rounded-[30px] bg-cg-7 flex flex-col items-center justify-around pb-[100px]">
          <div className="pt-[50px] text-[30px] text-white">방제목</div>
          <input
            value={roomName}
            placeholder={roomNameFocused ? "" : "방제목을 입력해주세요."}
            className="w-[90%] h-[15%] min-h-[50px] text-[20px] bg-cg-2 rounded-2xl"
            onChange={handleRoomName}
            onFocus={handleInputFocus}
            onBlur={handleInputBlur}
          />
          <div className="w-[100%] flex flex-row items-center justify-center">
            <div
              className="pt-[20px] pb-[8px] pr-[15px] text-[28px] text-white cursor-pointer"
              onClick={handleCheckboxToggle}
              aria-hidden
            >
              암호 사용
            </div>
            <div className="flex items-center pt-[20px] pb-[10px]">
              <label
                htmlFor="passwordCheckbox"
                className={`w-[35px] h-[35px] border-[5px] border-white relative rounded-sm cursor-pointer ${
                  boxChecked ? "bg-white" : "bg-cg-7"
                }`}
              >
                <input
                  type="checkbox"
                  checked={boxChecked}
                  onChange={handleCheckboxToggle}
                  className="w-0 h-0 opacity-0 absolute"
                  id="passwordCheckbox"
                />
                <span
                  className={`block w-[24px] h-[14px] border-t-[5px] border-r-[5px] transform rotate-[135deg] absolute top-[40%] left-[53%] -translate-x-[50%] -translate-y-[50%] ${
                    boxChecked ? "border-cg-7" : "border-transparent"
                  }`}
                />
              </label>
            </div>
          </div>
          <input
            type="password"
            value={roomPassword}
            placeholder={
              boxChecked && !roomPasswordFocused
                ? "비밀번호를 설정해주세요."
                : ""
            }
            className={
              boxChecked
                ? "w-[90%] h-[15%] min-h-[50px] text-[20px] bg-cg-2 rounded-2xl"
                : "w-[90%] h-[15%] min-h-[50px] text-[20px] bg-transparent text-transparent"
            }
            disabled={!boxChecked}
            onFocus={handleRoomPasswordFocus}
            onBlur={handleRoomPasswordBlur}
            onChange={handleRoomPassword}
          />
        </div>
      </div>
      <div className="w-full h-[15%] pt-[20px] flex flex-row items-center justify-evenly z-[20]">
        <button
          type="button"
          className="w-[30%] h-[45px] bg-cg-1 text-[24px] rounded-[60px] text-center"
          onClick={submitModifyRoom}
        >
          수정하기
        </button>
        <button
          type="button"
          className="w-[30%] h-[45px] bg-cg-1 text-[24px] rounded-[60px]"
          onClick={() => routeTo("/mypage")} // 방장 찬장으로 이동
        >
          닫기
        </button>
      </div>
      {alertModal && (
        <Alert
          openModal={alertModal}
          closeButton="확인"
          overz="z-[100]"
          text={alertText}
          closeAlert={() => setAlertModal(false)}
        />
      )}
    </>
  );
}
export default ModifyRoom;
