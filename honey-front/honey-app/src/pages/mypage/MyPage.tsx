import { ButtomMenu, Cupboard } from "@components/mypage";
import MypageTitle from "@components/mypage/MypageTitle";
import { RoomType } from "@customtype/dataTypes";
import { myRoomListState } from "@recoil/atom";
import { getMyRoomlistSelector } from "@recoil/selector";
import axios from "axios";
import { useEffect, useState } from "react";
import { useRecoilValue, useSetRecoilState } from "recoil";

function MyPage() {
  const roomList = useRecoilValue<RoomType[]>(getMyRoomlistSelector);
  const setRoomList = useSetRecoilState<RoomType[]>(myRoomListState);
  const [roomNum, setRoomNum] = useState<number>(0);
  const [selectedRoom, setSelectedRoom] = useState<RoomType>(roomList[0]);

  useEffect(() => {
    // Axios를 사용하여 데이터 가져오기
    axios
      .get("/api/v1/rooms/list")
      .then((response) => {
        const { data } = response;
        // Recoil 상태 업데이트
        setRoomList(data.roomDtoList);
        if (data.length > 0) {
          setSelectedRoom(data[0]);
        }
      })
      .catch((error) => {
        console.error("Error fetching room list:", error);
      });
  }, [setRoomList]);

  useEffect(() => {
    if (roomNum !== null) {
      if (roomNum >= 0 && roomNum < roomList.length) {
        setSelectedRoom(roomList[roomNum]);
      } else if (roomNum >= roomList.length) {
        setRoomNum(0);
      } else if (roomNum < 0) {
        setRoomNum(roomList.length - 1);
      }
    } else {
      setRoomNum(0);
    }
  }, [roomNum, roomList, setSelectedRoom]);

  return (
    <>
      <MypageTitle
        selectedRoom={selectedRoom}
        roomNum={roomNum}
        setRoomNum={setRoomNum}
      />
      <Cupboard />
      <ButtomMenu />
    </>
  );
}

export default MyPage;
