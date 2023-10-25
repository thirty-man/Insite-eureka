import { ImageButton } from "@components/common/button";
import Dropdown from "@components/common/dropdown/Dropdown";
import TitleText from "@components/common/textbox/TitleText";
import { RoomType } from "@customtype/dataTypes";
import { selectedRoomState } from "@recoil/atom";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilState, useRecoilValue } from "recoil";
import { leftArrow } from "@assets/images";
import { getMyRoomlistSelector } from "@recoil/selector";

interface MypageTitleProps {
  selectedRoom: RoomType;
  roomNum: number;
  setRoomNum: React.Dispatch<React.SetStateAction<number>>;
}

function MypageTitle({ selectedRoom, roomNum, setRoomNum }: MypageTitleProps) {
  // const roomList = useRecoilValue<RoomType[]>(getMyRoomlistSelector);
  const roomList = useRecoilValue<RoomType[]>(getMyRoomlistSelector);

  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [, setNextRoom] = useRecoilState<RoomType>(selectedRoomState);
  const navi = useNavigate();

  function goToRoom(roomId: number) {
    setRoomNum(roomId - 1);
  }

  useEffect(() => {
    setNextRoom(roomList[roomNum]);
  }, [roomNum, roomList, setNextRoom]);

  function nextPage(): void {
    if (roomNum === null) {
      setRoomNum(0);
    } else {
      setRoomNum(roomNum + 1);
    }
  }

  function beforePage(): void {
    if (roomNum === null) {
      setRoomNum(0);
    } else {
      setRoomNum(roomNum - 1);
    }
  }

  function goToBack(): void {
    navi(-1);
  }

  return (
    <>
      <div className="flex justify-center items-center w-full">
        <ImageButton
          image={leftArrow}
          alt="뒤로가기"
          className="flex w-[10%] justify-center items-center"
          onClick={() => goToBack()}
        />
        <div className="flex flex-col justify-center items-center w-full pr-10 ">
          <button
            type="button"
            className="w-[70%] mt-5 mb-2"
            onClick={() => setIsDropdownOpen(!isDropdownOpen)}
          >
            <TitleText
              text={selectedRoom ? selectedRoom.title : "방이 없습니다"}
              className="p-1 pr-5 pl-5 rounded-xl sm:h-[90px] h-[38px] bg-cg-9 overflow-x-auto items-start"
            />
          </button>
          {isDropdownOpen && (
            <div className="flex justify-center w-[50%]">
              {roomList.length === 0 ? (
                <div>방이 없습니다</div>
              ) : (
                <Dropdown
                  className=""
                  items={roomList.map((room) => ({
                    ...room,
                    roomTitle:
                      room.title.length > 10
                        ? `${room.title.slice(0, 10)}...`
                        : room.title,
                    roomId: room.id,
                  }))}
                  onClick={(roomId) => goToRoom(roomId)}
                />
              )}
            </div>
          )}
        </div>
      </div>
      <div className="flex justify-center items-center">
        <button
          className="bg-cg-3 rounded-xl p-2 m-2"
          type="button"
          onClick={() => beforePage()}
        >
          이전
        </button>
        <p>
          {roomNum === null ? 1 : roomNum + 1} / {roomList.length}
        </p>
        <button
          className="bg-cg-3 rounded-xl p-2 m-2"
          type="button"
          onClick={() => nextPage()}
        >
          다음
        </button>
      </div>
    </>
  );
}

export default MypageTitle;
