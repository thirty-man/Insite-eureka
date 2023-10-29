import { ImageButton } from "@components/common/button";
import Dropdown from "@components/common/dropdown/Dropdown";
import TitleText from "@components/common/textbox/TitleText";
import { RoomType } from "@customtype/dataTypes";
import { selectedRoomState } from "@recoil/atom";
import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilState, useRecoilValue } from "recoil";
import { leftArrow } from "@assets/images";
import { getMyRoomlistSelector } from "@recoil/selector";

function MypageTitle() {
  // const roomList = useRecoilValue<RoomType[]>(getMyRoomlistSelector);
  const roomList = useRecoilValue<RoomType[]>(getMyRoomlistSelector);
  // const [selectedNum, setSelectedNum] = useRecoilState<number>(roomNumState);
  const [title, setTitle] = useState<string>("방이 없습니다.");

  const [selectedRoom, setSelectedRoom] =
    useRecoilState<RoomType>(selectedRoomState);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const navi = useNavigate();
  const [currentIndex, setCurrentIndex] = useState(0); // 현재 선택된 방의 인덱스

  // console.log("마이페이지타이틀 : ", selectedRoom);

  function goToRoom(room: RoomType) {
    // console.log(room.id);
    setSelectedRoom(room);
  }

  useEffect(() => {
    // selectedRoom이 변경될 때 title 업데이트
    if (selectedRoom !== undefined) {
      setTitle(selectedRoom.roomTitle);
    } else if (roomList.length > 0) {
      setTitle(roomList[0].roomTitle);
      setSelectedRoom(roomList[0]);
    } else {
      setTitle("방을 선택하세요");
    }
  }, [selectedRoom, roomList, setSelectedRoom]);

  const buttonRef = useRef<HTMLButtonElement | null>(null);
  const dropdownRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      const target = event.target as Node;
      if (
        dropdownRef.current &&
        !dropdownRef.current.contains(target) &&
        buttonRef.current &&
        !buttonRef.current.contains(target)
      ) {
        setIsDropdownOpen(false);
      }
    }

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  function beforePage(): void {
    if (currentIndex > 0) {
      setCurrentIndex(currentIndex - 1);
      setSelectedRoom(roomList[currentIndex - 1]);
    } else {
      setCurrentIndex(roomList.length - 1);
      setSelectedRoom(roomList[roomList.length - 1]);
    }
  }

  function nextPage(): void {
    if (currentIndex < roomList.length - 1) {
      setCurrentIndex(currentIndex + 1);
      setSelectedRoom(roomList[currentIndex + 1]);
    } else {
      setCurrentIndex(0);
      setSelectedRoom(roomList[0]);
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
            ref={buttonRef}
            type="button"
            className="w-[70%] mt-5 mb-2 relative"
            onClick={() => setIsDropdownOpen(!isDropdownOpen)}
          >
            <TitleText
              text={title}
              className="p-1 pr-5 pl-5 rounded-xl sm:h-[90px] h-[38px] bg-cg-9 overflow-x-auto items-start"
            />
            {isDropdownOpen && (
              <div
                className="flex justify-center w-[100%]"
                ref={dropdownRef}
                onClick={(e) => e.stopPropagation()}
                aria-hidden
              >
                {roomList.length === 0 ? (
                  <div>방이 없습니다</div>
                ) : (
                  <Dropdown
                    className=""
                    // items={roomList.map((room) => ({
                    //   ...room,
                    //   roomTitle:
                    //     room.title.length > 10
                    //       ? `${room.title.slice(0, 10)}...`
                    //       : room.title,
                    //   roomId: room.id,
                    // }))}
                    items={roomList}
                    onClick={(room) => goToRoom(room)}
                  />
                )}
              </div>
            )}
          </button>
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
          {" "}
          {currentIndex + 1} / {roomList.length}
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
