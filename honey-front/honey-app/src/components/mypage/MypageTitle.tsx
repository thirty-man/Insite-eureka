import { useState, useEffect, useRef } from "react";
import { ImageButton, TextButton } from "@components/common/button";
import Dropdown from "@components/common/dropdown/Dropdown";
import TitleText from "@components/common/textbox/TitleText";
import { RoomType } from "@customtype/dataTypes";
import { useNavigate } from "react-router-dom";
import { useRecoilState, useRecoilValue } from "recoil";
import { leftArrow } from "@assets/images";
import { getMyRoomlistSelector } from "@recoil/selector";
import axios from "axios";
import logoutState from "@recoil/atom/logoutState";
import { Alert } from "@components/common/modal";
import { mypageSelectedRoom } from "@recoil/atom";

function MypageTitle() {
  const roomList = useRecoilValue<RoomType[]>(getMyRoomlistSelector);
  const [title, setTitle] = useState<string>("방이 없습니다.");
  const [showDate, setShowDate] = useState<string>("");
  const [showTime, setShowTime] = useState<string>("");
  const [selectedRoom, setSelectedRoom] =
    useRecoilState<RoomType>(mypageSelectedRoom);
  const [isDropdownOpen, setIsDropdownOpen] = useState<boolean>(false);
  const navi = useNavigate();
  const [currentIndex, setCurrentIndex] = useState(0); // 현재 선택된 방의 인덱스
  const token = sessionStorage.getItem("Authorization");
  const [, setLoggedOut] = useRecoilState<boolean>(logoutState);
  const [alertNoRoom, setAlertNoRoom] = useState<boolean>(false);
  const { VITE_API_URL } = import.meta.env;

  function goToRoom(room: RoomType) {
    // console.log(room.id);
    setSelectedRoom(room);
  }

  useEffect(() => {
    if (roomList && roomList.length > 0) {
      setTitle(roomList[0].roomTitle);
      setSelectedRoom(roomList[0]);
      const parts = roomList[0].showTime.split("T");
      setShowDate(parts[0]);
      setShowTime(parts[1].split(".")[0].slice(0, 5));
    }
  }, [roomList, setSelectedRoom]);

  useEffect(() => {
    if (selectedRoom === undefined) return;
    setTitle(selectedRoom.roomTitle);
    const parts = selectedRoom.showTime.split("T");
    setShowDate(parts[0]);
    setShowTime(parts[1].split(".")[0].slice(0, 5));
  }, [selectedRoom, setTitle]);

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

  const handleDropdown = () => {
    if (!isDropdownOpen && roomList.length === 0) {
      setAlertNoRoom(true);
    }
    setIsDropdownOpen(!isDropdownOpen);
  };

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
    navi("/");
  }

  function logout(): void {
    const config = {
      headers: {
        Authorization: token,
      },
    };

    axios
      .post(`${VITE_API_URL}/api/v1/members/logout`, null, config)
      .then(() => {
        // alert("로그아웃 됐습니다.");
        setLoggedOut(true);
        sessionStorage.clear();
        navi("/login");
      });
  }

  return (
    <>
      <div className="flex justify-evenly items-center w-full">
        <ImageButton
          image={leftArrow}
          alt="뒤로가기"
          className="flex w-[15%] justify-center items-center"
          onClick={() => goToBack()}
        />
        <div className="flex flex-col justify-center items-center w-full relative">
          <button
            ref={buttonRef}
            type="button"
            className="w-[70%] mt-5 mb-2 relative"
            onClick={handleDropdown}
          >
            {title ? (
              <TitleText
                text={title}
                className="p-1 pr-5 pl-5 rounded-xl sm:h-[90px] h-[38px] bg-cg-9 overflow-x-auto items-start"
              />
            ) : (
              <div>방이 없습니다.</div>
            )}
          </button>
          {isDropdownOpen && (
            <div
              className="flex justify-center w-[100%]"
              ref={dropdownRef}
              onClick={(e) => e.stopPropagation()}
              aria-hidden
            >
              {roomList.length === 0 ? null : (
                <Dropdown
                  className=""
                  items={roomList}
                  onClick={(room) => {
                    goToRoom(room);
                    setIsDropdownOpen(false);
                  }}
                />
              )}
            </div>
          )}
        </div>
        <TextButton
          text="로그아웃"
          color="2"
          className="w-[15%] mr-2 sm:text-[20px] text-[15px] p-[2px] min-w-[65px]"
          onClick={() => logout()}
        />
      </div>
      <div>
        개봉일 : {showDate || ""} {showTime || ""}
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
      {alertNoRoom && (
        <Alert
          openModal={alertNoRoom}
          closeButton="확인"
          overz="z-[100]"
          text="방이 없습니다."
          closeAlert={() => setAlertNoRoom(false)}
        />
      )}
    </>
  );
}

export default MypageTitle;
