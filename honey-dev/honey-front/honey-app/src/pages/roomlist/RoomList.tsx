import { useEffect, useState } from "react";
import { Alert } from "@components/common/modal";
import TitleText from "@components/common/textbox/TitleText";
import {
  ButtomButton,
  PageMove,
  SearchRoom,
  ShowRoom,
} from "@components/search/index";
import { PageType, RoomType } from "@customtype/dataTypes";
import { roomListState, selectedPageState } from "@recoil/atom";
import successCreateRoomState from "@recoil/atom/successCreateRoomState";
import axios from "axios";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import participateState from "@recoil/atom/participateState";

function RoomList() {
  const [, setRoomList] = useRecoilState<RoomType[]>(roomListState);
  const [, setSelectedPage] = useRecoilState<PageType>(selectedPageState);

  const token = sessionStorage.getItem("Authorization");

  const successCreateRoom = useRecoilValue<boolean>(successCreateRoomState);
  const setSuccessCreateRoom = useSetRecoilState<boolean>(
    successCreateRoomState,
  );
  const [successCreated, setSuccessCreated] =
    useState<boolean>(successCreateRoom);

  const participated = useRecoilValue<boolean>(participateState);
  const setParticipated = useSetRecoilState<boolean>(participateState);
  const [successParticipated, setSuccessParticipated] =
    useState<boolean>(participated);

  const { VITE_API_URL } = import.meta.env;

  useEffect(() => {
    const config = {
      headers: {
        Authorization: token,
      },
    };

    axios
      .get(`${VITE_API_URL}/api/v1/rooms?title=&page=0`, config)
      .then((response) => {
        const { data } = response;
        const getRoomList = data.roomSearchDtoList;
        // Recoil 상태 업데이트
        if (getRoomList.length > 0) {
          setRoomList(getRoomList);
        }
        const newPage: PageType = {
          currentPage: data.currentPage,
          totalPages: data.totalPages,
          hasNext: data.hasNext,
        };
        setSelectedPage(newPage);
      });
    // .catch((error) => {
    //   console.error("Err:", error);
    // });
  }, [token, setRoomList, setSelectedPage, VITE_API_URL]);

  return (
    <>
      <div className="flex flex-col justify-center items-center">
        <TitleText text="방 목록" className="mt-5" />
        <SearchRoom />
        <ShowRoom />
        <div className="flex z-[50] relative bottom-10 w-[85%] min-w-[340px] items-center justify-center">
          <PageMove />
        </div>
        <ButtomButton />
      </div>
      {successCreated && (
        <Alert
          openModal={successCreated}
          closeButton="확인"
          overz="z-[100]"
          text="방 생성이 완료되었습니다."
          closeAlert={() => {
            setSuccessCreateRoom(false);
            setSuccessCreated(false);
          }}
        />
      )}
      {successParticipated && (
        <Alert
          openModal={successParticipated}
          closeButton="확인"
          overz="z-[100]"
          text="방 참여가 완료되었습니다."
          closeAlert={() => {
            setParticipated(false);
            setSuccessParticipated(false);
          }}
        />
      )}
    </>
  );
}

export default RoomList;
