import { ButtomMenu, Cupboard } from "@components/mypage";
import MypageTitle from "@components/mypage/MypageTitle";
import { RoomType, UserType } from "@customtype/dataTypes";
import {
  memberListState,
  myRoomListState,
  selectedRoomState,
} from "@recoil/atom";
import axios from "axios";
import { useEffect } from "react";
import { useRecoilState } from "recoil";

function MyPage() {
  const [, setRoomList] = useRecoilState<RoomType[]>(myRoomListState);
  // const [roomNum, setRoomNum] = useRecoilState<number>(roomNumState);
  // const [selectedRoom, setSelectedRoom] = useState<RoomType | null>(null);
  // const { VITE_API_URL } = import.meta.env;
  const token = sessionStorage.getItem("Authorization");
  const [selectedRoom] = useRecoilState<RoomType>(selectedRoomState);
  const [, setMemberList] = useRecoilState<UserType[]>(memberListState);

  useEffect(() => {
    // Axios를 사용하여 데이터 가져오기
    const config = {
      headers: {
        Authorization: token,
      },
    };

    axios
      .get(`http://localhost:8080/api/v1/rooms/list`, config)
      .then((response) => {
        const { data } = response;
        const getRoomList = data.roomDtoList;
        // Recoil 상태 업데이트
        if (getRoomList.length > 0) {
          setRoomList(() => [...data.roomDtoList]);
        }
      });
    // .catch((error) => {
    //   console.error("Error fetching room list:", error);
    // });
  }, [setRoomList, token]);

  useEffect(() => {
    if (selectedRoom) {
      const config = {
        headers: {
          Authorization: token,
        },
      };

      axios
        .get(
          `http://localhost:8080/api/v1/rooms/${selectedRoom.id}/member-list`,
          config,
        )
        .then((response) => {
          const { data } = response;
          const getMemberList = data.memberDtoList;
          setMemberList(getMemberList);
        });
      // .catch((error) => {
      //   console.error("Error fetching room list:", error);
      // });
    }
  }, [selectedRoom, token, setMemberList]);

  return (
    <>
      <MypageTitle />
      <Cupboard />
      <ButtomMenu />
    </>
  );
}

export default MyPage;
