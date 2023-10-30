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
import { useEffect } from "react";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";

function RoomList() {
  // 방 목록 가져오기
  const [, setRoomList] = useRecoilState<RoomType[]>(roomListState);
  const [, setSelectedPage] = useRecoilState<PageType>(selectedPageState);
  // const [title] = useRecoilState<string>(inputSearchState);
  const token = sessionStorage.getItem("Authorization");

  const successCreateRoom = useRecoilValue<boolean>(successCreateRoomState);
  const setSuccessCreateRoom = useSetRecoilState<boolean>(
    successCreateRoomState,
  );
  useEffect(() => {
    const config = {
      headers: {
        Authorization: token,
      },
    };

    axios
      .get(
        // `http://localhost:8080/api/v1/rooms?${title}&${selectedPage}`,
        `http://localhost:8080/api/v1/rooms?title=&page=0`,
        config,
      )
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
      })
      .catch((error) => {
        console.error("Err:", error);
      });
  }, [token, setRoomList, setSelectedPage]);

  return (
    <>
      <div className="flex flex-col justify-center items-center">
        <TitleText text="방 목록" className="mt-5" />
        <SearchRoom />
        <ShowRoom />
        <div className="flex z-[50] relative bottom-10 w-[80%] items-center justify-center">
          <PageMove />
        </div>
        <ButtomButton />
      </div>
      {successCreateRoom && (
        <Alert
          openModal={successCreateRoom}
          closeButton="확인"
          overz="z-[100]"
          text="방 생성이 완료되었습니다."
          closeAlert={() => setSuccessCreateRoom(false)}
        />
      )}
    </>
  );
}

export default RoomList;
