import { TextButton } from "@components/common/button";
import { useNavigate } from "react-router-dom";

function AccessError() {
  const navi = useNavigate();

  function goToBack() {
    navi(-1);
  }

  return (
    <div>
      <h1> 잘못된 접근입니다.</h1>
      <TextButton
        text="돌아가기"
        color="3"
        className="p-2 rounded-xl sm:text-[30px] text-[20px]"
        onClick={() => {
          goToBack();
        }}
      />
    </div>
  );
}

export default AccessError;
