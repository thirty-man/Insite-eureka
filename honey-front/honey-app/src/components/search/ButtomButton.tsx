import { TextButton } from "@components/common/button";
import useRouter from "@hooks/useRouter";

function ButtomButton() {
  const { routeTo } = useRouter();

  function goToMyCupboard(): void {
    // 내가 참여한 방 목록을 가져오는 api 호출(전역 myRoomList 변경)

    routeTo("/mypage");
  }
  function createRoom(): void {
    routeTo("/room");
  }
  const basicType: string = "rounded-xl p-2 m-2 text-xl w-[30%]";

  return (
    <div className="flex w-full justify-evenly">
      <TextButton
        text="내 찬장"
        color="3"
        className={basicType}
        onClick={() => goToMyCupboard()}
      />
      <TextButton
        text="방 만들기"
        color="3"
        className={basicType}
        onClick={() => createRoom()}
      />
    </div>
  );
}

export default ButtomButton;
