import HelpIcon from "@assets/icons";
import { KakaoLoginButton, PoohHelpModal, PoohLogin } from "@assets/images";
import { Modal } from "@components/common/modal";
import { useState } from "react";

function Login() {
  const [helpOpen, setHelpOpen] = useState<boolean>(false);

  return (
    <>
      <div className="flex h-10 mt-5 items-center justify-end px-5">
        <img
          src={HelpIcon}
          alt="help"
          aria-hidden
          onClick={() => setHelpOpen(true)}
        />
      </div>
      {helpOpen && (
        <Modal
          className="fixed w-[340px] h-[350px] bottom-[50%] left-[50%] -translate-x-[170px] translate-y-[140px] z-[120] rounded-[36px] shadow-lg flex items-center justify-center px-[15px] py-[15px] bg-cg-6"
          overlay
          openModal={helpOpen}
        >
          <div className="w-[100%] h-[100%] overflow-y-auto z-[20] rounded-[30px] bg-cg-7 text-white text-[20px] px-[15px] py-[15px]">
            <span>사용하는 </span>
            <span className="text-cg-1 text-[24px]">방법</span>
            <span>은 다음과 같습니다.</span>
          </div>
          <Modal
            className="fixed w-[120px] h-[120px] bottom-1/2 left-1/2 -translate-x-[180px] translate-y-[190px] z-[120] bg-transparent flex items-center justify-center"
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
            className="fixed w-[100px] h-[35px] bottom-1/2 left-1/2 -translate-x-[50px] translate-y-[170px] z-[120] rounded-[60px] bg-cg-1 flex items-center justify-center"
            overlay={false}
            openModal
          >
            <button
              type="button"
              className="w-[100%] h-[100%] text-[24px]"
              onClick={() => setHelpOpen(false)}
            >
              닫기
            </button>
          </Modal>
        </Modal>
      )}
      <h1 className="h-1/6">
        <span className="text-cg-3">꿀</span>&apos;s Writing
      </h1>
      <div className="flex h-4/6 justify-center items-center">
        <div className="flex flex-col h-full items-center justify-center">
          <img src={PoohLogin} alt="mainpooh" />
          <button className="rounded border border-blue-700" type="button">
            <img src={KakaoLoginButton} alt="kakao Login Btn" />
          </button>
        </div>
      </div>
    </>
  );
}

export default Login;
