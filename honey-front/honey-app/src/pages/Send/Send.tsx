import { TextButton } from "@components/common/button";
import { UserType } from "@customtype/dataTypes";
import { AccessError } from "@pages/error";
import { selectedMemberState } from "@recoil/atom";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";

function Send() {
  const selectedMember = useRecoilValue<UserType>(selectedMemberState);
  const navi = useNavigate();
  const basicType = "sm:text-[30px] text-[20px] rounded-xl p-1 m-2";

  function send() {
    // 전송 api요청
  }
  function goToBack() {
    navi(-1);
  }

  if (selectedMember === undefined) {
    return <AccessError />;
  }

  return (
    <div className="flex h-full justify-center items-center">
      <div className="flex justify-center items-center h-[80%] w-full bg-cover bg-writePaper bg-size">
        <div className="flex flex-col justify-center items-center h-[90%] w-full">
          <div className="flex h-[11%] justify-center items-center sm:text-[30px] text-[20px]">
            <p className="text-emerald-800 ">{selectedMember.nickName}</p>님에게
            보내는 편지
          </div>
          <textarea className="h-[78%] w-[80%] bg-inherit sm:text-[20px] text-[15px] p-10" />
          <div className="h-[10%] w-[40%] flex justify-between items-center">
            <TextButton
              text="전송"
              color=""
              className={basicType}
              onClick={() => {
                send();
              }}
            />
            <TextButton
              text="취소"
              color=""
              className={basicType}
              onClick={() => {
                goToBack();
              }}
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Send;
