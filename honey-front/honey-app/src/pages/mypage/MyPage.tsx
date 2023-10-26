import { ButtomMenu, Cupboard } from "@components/mypage";
import MypageTitle from "@components/mypage/MypageTitle";
import { RoomType } from "@customtype/dataTypes";
import { myRoomListState } from "@recoil/atom";
// import axios from "axios";
import { useEffect, useState } from "react";
import { useRecoilState } from "recoil";

const dummyList: RoomType[] = [
  {
    id: 1,
    title: "1번",
    password: "1234",
    owner: "누구",
  },
  {
    id: 2,
    title: "2번",
    password: "1234",
    owner: "ㅎㅇ",
  },
  {
    id: 3,
    title: "3번",
    password: "1234",
    owner: "ㅂㅇ",
  },
  {
    id: 4,
    title: "4번",
    password: "1234",
    owner: "놉",
  },
];

function MyPage() {
  const [roomList, setRoomList] = useRecoilState<RoomType[]>(myRoomListState);
  console.log("1번", roomList);
  const [roomNum, setRoomNum] = useState<number>(0);
  console.log("2번", roomNum);
  const [selectedRoom, setSelectedRoom] = useState<RoomType | null>(null);
  console.log("3번", selectedRoom);

  useEffect(() => {
    // Axios를 사용하여 데이터 가져오기
    // axios
    //   .get("http://k9a701a.p.ssafy.io:8080/api/v1/rooms/list")
    //   .then((response) => {
    //     const { data } = response;
    //     console.log(response.data);
    //     // Recoil 상태 업데이트
    //     if (data.length > 0) {
    //       setSelectedRoom(data[0]);
    //     }
    //   })
    //   .catch((error) => {
    //     console.error("Error fetching room list:", error);
    //   });
    setRoomList(dummyList);
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
