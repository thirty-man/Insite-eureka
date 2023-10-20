import TitleText from "@components/common/textbox/TitleText";
import {
  ButtomButton,
  PageMove,
  SearchRoom,
  ShowRoom,
} from "@components/search/index";

function RoomList() {
  // 여기서 초기 room list를 받아서 출력할 예정

  return (
    <div className="flex flex-col justify-center items-center">
      <TitleText text="방 목록" className="mt-5" />
      <SearchRoom />
      <ShowRoom />
      <div className="flex z-[50] relative bottom-10 w-[80%] items-center justify-center">
        <PageMove />
      </div>
      <ButtomButton />
    </div>
  );
}
export default RoomList;
