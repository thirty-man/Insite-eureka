import { ButtomMenu, Cupboard } from "@components/mypage";
import MypageTitle from "@components/mypage/MypageTitle";
import { RoomType } from "@customtype/dataTypes";
import { myRoomListState, selectedRoomState } from "@recoil/atom";
import { useEffect, useState } from "react";
import { useRecoilState, useRecoilValue } from "recoil";

function MyPage() {
  // 내가 참여한 방 목록 중 가장 앞에 있는 방을 우선 보여준다
  const roomList = useRecoilValue<RoomType[]>(myRoomListState);
  const [roomNum, setRoomNum] = useState<number | null>(0);
  const [selectedRoom, setSelectedRoom] = useRecoilState<RoomType | null>(
    selectedRoomState,
  );

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
