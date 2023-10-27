import { PoohHelpModal } from "@assets/images";
import { Modal } from "@components/common/modal";
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
        <Modal
          className="fixed w-[340px] h-[350px] bottom-[50%] left-[50%] -translate-x-[170px] translate-y-[140px] z-[120] rounded-[36px] shadow-lg flex items-center justify-center px-[15px] py-[15px] bg-cg-6"
          overlay
          openModal={successCreateRoom}
        >
          <div className="w-[100%] h-[100%] overflow-y-auto z-[20] rounded-[30px] bg-cg-7 text-white text-[20px] px-[15px] py-[15px]">
            <div className="flex items-center justify-center">
              <span>방 생성이 완료되었습니다.</span>
            </div>
          </div>
          <Modal
            className="fixed w-[120px] h-[120px] bottom-[50%] left-[50%] -translate-x-[180px] translate-y-[190px] z-[120] bg-transparent flex items-center justify-center"
            overlay={false}
            openModal
          >
            <img
              src={PoohHelpModal}
              className="w-[100px] h-[90px]"
              alt="푸 모달"
            />
          </Modal>
          <Modal
            className="fixed w-[100px] h-[35px] bottom-[50%] left-[50%] -translate-x-[50px] translate-y-[170px] z-[120] rounded-[60px] bg-cg-1 flex items-center justify-center"
            overlay
            openModal
          >
            <button
              type="button"
              className="w-[100%] h-[100%] text-[24px]"
              onClick={() => {
                setSuccessCreateRoom(false);
              }}
            >
              확인
            </button>
          </Modal>
        </Modal>
      )}
    </>
  );
}
export default RoomList;
