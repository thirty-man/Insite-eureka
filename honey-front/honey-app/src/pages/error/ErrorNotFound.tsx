import { TextButton } from "@components/common/button";
import { useNavigate } from "react-router-dom";

function ErrorNotFound() {
  const navi = useNavigate();

  function goToBack() {
    navi("/");
  }

  return (
    <div>
      <h1>존재하지 않는 페이지입니다.</h1>
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
export default ErrorNotFound;
