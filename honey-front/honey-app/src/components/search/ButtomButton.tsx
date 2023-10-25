import { TextButton } from "@components/common/button";
import useRouter from "@hooks/useRouter";

function ButtomButton() {
  const { routeTo } = useRouter();

  function goToMyCupboard(): void {
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
