import { TextButton } from "@components/common/button";
import useRouter from "@hooks/useRouter";

function ButtomMenu() {
  const greedyPooh = "./src/assets/images/greedypooh.png";
  const buttonStyle: string =
    "flex rounded-xl sm:h-[17%] h-[15%] m-2 p-3 sm:w-[50%] w-[60%] sm:text-[20px] text-[13px] justify-center items-center";
  const { routeTo } = useRouter();

  function moveTo(path: string) {
    routeTo(path);
  }

  return (
    <div className="flex w-full">
      <div className="flex flex-col w-[50%] h-[247px] items-center justify-center">
        <TextButton
          text="참가자 보기"
          color="3"
          className={buttonStyle}
          onClick={() => moveTo("")}
        />
        <TextButton
          text="초대 링크 복사"
          color="3"
          className={buttonStyle}
          onClick={() => moveTo("")}
        />
        <TextButton
          text="방 수정"
          color="3"
          className={buttonStyle}
          onClick={() => moveTo("")}
        />
        <TextButton
          text="방 탈퇴"
          color="3"
          className={buttonStyle}
          onClick={() => moveTo("")}
        />
      </div>
      <div className="flex w-[50%] justify-center items">
        <img src={greedyPooh} alt="푸" className="" />
      </div>
    </div>
  );
}

export default ButtomMenu;
