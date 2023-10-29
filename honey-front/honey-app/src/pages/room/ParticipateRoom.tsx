import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import useRouter from "@hooks/useRouter";
import { useRecoilState } from "recoil";
import { selectedRoomState } from "@recoil/atom";

function ParticipateRoom() {
  const { roomId } = useParams();
  const { routeTo } = useRouter();
  const token = sessionStorage.getItem("Authorization");
  const [roomPassword, setRoomPassword] = useState("");
  const [isOpen, setIsOpen] = useState(true);
  const [selectedRoom, setSelectedRoom] = useRecoilState(selectedRoomState);
  const [date, setDate] = useState("");
  const [time, setTime] = useState("");

  function enterRoom() {
    const roomParticipateReqDto = {
      room_id: roomId,
      password: isOpen ? null : roomPassword,
    };

    const config = {
      headers: {
        Authorization: token,
      },
    };
    console.log(roomPassword);

    axios
      .post(
        `http://localhost:8080/api/v1/rooms/participate`,
        roomParticipateReqDto,
        config,
      )
      .then(() => {
        console.log("참여 됐음");
        routeTo("/");
      })
      .catch((error) => {
        console.log(error.response.data.errorCode);
        if (error.response.data.errorCode === "001") {
          alert("이미 참가중인 방입니다.");
          return;
        }

        if (error.response.data.errorCode === "003") {
          alert("비밀번호가 틀렸습니다.");
          return;
        }
        if (error.response.data.errorCode === "")
          console.log("참가요청 오류 : ", error);
      });
  }

  useEffect(() => {
    const config = {
      headers: {
        Authorization: token,
      },
    };

    axios
      .get(`http://localhost:8080/api/v1/rooms/${roomId}`, config)
      .then((response) => {
        setSelectedRoom(response.data);
        setIsOpen(response.data.isOpen);
        console.log(isOpen);
        const parts = response.data.showTime.split("T");
        setDate(parts[0]);
        setTime(parts[1]);
      })
      .catch((error) => {
        console.log("part Err : ", error.response);
      });
  }, [roomId, token, setSelectedRoom, isOpen, setDate, setTime]);

  return (
    selectedRoom && (
      <>
        <div className="text-[30px] text-white">방 참여</div>
        <div className="w-full h-full px-[30px]">
          <div className="w-full h-full rounded-[30px] bg-cg-7 flex flex-col items-center justify-around pb-[50px]">
            <div className="pt-[20px] pb-[8px] text-[30px] text-white">
              방제목
            </div>
            <div className="flex w-[90%] h-[13%] min-h-[50px] text-[24px] bg-cg-2 rounded-2xl justify-center items-center">
              {selectedRoom.roomTitle}
            </div>
            <div className="pt-[20px] pb-[8px] text-[30px] text-white">
              공개일
            </div>
            <div className="flex w-[90%] h-[13%] min-h-[50px] sm:text-[24px] text-[15px] bg-cg-2 rounded-2xl justify-center items-center">
              {date} / {time}
            </div>
            {!isOpen && (
              <>
                <div className="w-[100%] flex flex-row items-center justify-center">
                  <div
                    className="pt-[20px] pb-[8px] pr-[15px] text-[28px] text-white"
                    aria-hidden
                  >
                    암호 입력
                  </div>
                </div>
                <input
                  type="password"
                  value={roomPassword}
                  onChange={(e) => setRoomPassword(e.target.value)}
                  placeholder={!isOpen ? "비밀번호를 입력해주세요." : ""}
                  className={
                    !isOpen
                      ? "w-[90%] h-[13%] min-h-[50px] sm:text-[24px] text-[15px] bg-cg-2 rounded-2xl"
                      : "w-[90%] h-[13%] min-h-[50px] sm:text-[24px] text-[15px] bg-transparent text-transparent"
                  }
                  disabled={isOpen}
                />
              </>
            )}
          </div>
        </div>
        <div className="w-full h-[15%] pt-[20px] flex flex-row items-center justify-evenly z-[20]">
          <button
            type="button"
            className="w-[30%] h-[45px] bg-cg-1 text-[24px] rounded-[60px] text-center"
            onClick={enterRoom}
          >
            참여하기
          </button>
          <button
            type="button"
            className="w-[30%] h-[45px] bg-cg-1 text-[24px] rounded-[60px]"
            onClick={() => routeTo("/")}
          >
            닫기
          </button>
        </div>
      </>
    )
  );
}

export default ParticipateRoom;
