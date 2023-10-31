import { useEffect, useState } from "react";
import { ButtomMenu, Cupboard } from "@components/mypage";
import MypageTitle from "@components/mypage/MypageTitle";
import { RoomType, UserType } from "@customtype/dataTypes";
import {
  memberListState,
  myRoomListState,
  mypageSelectedRoom,
  notHostModifyState,
} from "@recoil/atom";
import modifyState from "@recoil/atom/modifyState";
import axios from "axios";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import { Alert } from "@components/common/modal";
import completeSendState from "@recoil/atom/completeSendState";

function MyPage() {
  const [, setRoomList] = useRecoilState<RoomType[]>(myRoomListState);
  const token = sessionStorage.getItem("Authorization");
  const [selectedRoom] = useRecoilState<RoomType>(mypageSelectedRoom);
  const [, setMemberList] = useRecoilState<UserType[]>(memberListState);
  const { VITE_API_URL } = import.meta.env;

  const send = useRecoilValue(completeSendState);
  const setSend = useSetRecoilState(completeSendState);
  const [successSend, setSuccessSend] = useState<boolean>(send);

  const modified = useRecoilValue<boolean>(modifyState);
  const setModified = useSetRecoilState<boolean>(modifyState);
  const [successModifyRoom, setSuccessModifyRoom] = useState<boolean>(modified);

  const notHostModified = useRecoilValue<boolean>(notHostModifyState);
  const setNotHostModified = useSetRecoilState<boolean>(notHostModifyState);
  const [notHostModifyError, setNotHostModifyError] =
    useState<boolean>(notHostModified);

  useEffect(() => {
    // Axios를 사용하여 데이터 가져오기
    const config = {
      headers: {
        Authorization: token,
      },
    };

    axios.get(`${VITE_API_URL}/api/v1/rooms/list`, config).then((response) => {
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
  }, [setRoomList, token, VITE_API_URL]);

  useEffect(() => {
    if (selectedRoom === undefined) return;

    const config = {
      headers: {
        Authorization: token,
      },
    };

    axios
      .get(
        `${VITE_API_URL}/api/v1/rooms/${selectedRoom.id}/member-list`,
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
  }, [selectedRoom, token, VITE_API_URL, setMemberList]);

  return (
    <>
      <MypageTitle />
      <Cupboard />
      <ButtomMenu />
      {successModifyRoom && (
        <Alert
          openModal={successModifyRoom}
          closeButton="확인"
          overz="z-[100]"
          text="방 수정이 완료되었습니다."
          closeAlert={() => {
            setModified(false);
            setSuccessModifyRoom(false);
          }}
        />
      )}
      {successSend && (
        <Alert
          openModal={successSend}
          closeButton="확인"
          overz="z-[100]"
          text="꿀단지 전송이 완료되었습니다."
          closeAlert={() => {
            setSend(false);
            setSuccessSend(false);
          }}
        />
      )}
      {notHostModifyError && (
        <Alert
          openModal={notHostModifyError}
          closeButton="확인"
          overz="z-[100]"
          text="방장만 수정 가능합니다."
          closeAlert={() => {
            setNotHostModified(false);
            setNotHostModifyError(false);
          }}
        />
      )}
    </>
  );
}

export default MyPage;
