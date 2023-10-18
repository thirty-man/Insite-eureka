import Help from "@assets/icons";
import Modal from "@components/common/modal";
import { useState } from "react";

function Login() {
  const [helpOpen, setHelpOpen] = useState<boolean>(false);

  const handleHelpModal = () => {
    setHelpOpen((p) => !p);
  };

  return (
    <>
      <div className="flex h-10 mt-5 items-center justify-end px-5">
        <img src={Help} alt="help" aria-hidden onClick={handleHelpModal} />
      </div>
      {helpOpen && (
        <Modal className="w-96 h-96" overlay={false} openModal={helpOpen}>
          사용하는 방법은 다음과 같습니다.
        </Modal>
      )}
      <h1 className="h-1/6">
        <span className="text-cg-3">꿀</span>&apos;s Writing
      </h1>
      <div className="flex h-4/6 justify-center items-center">
        <div className="flex flex-col h-full items-center justify-center">
          <img src="../src/assets/images/푸꿀1.png" alt="mainpooh" />
          <button className="rounded border border-blue-700" type="button">
            <img
              src="../src/assets/images/kakao_login_medium_wide.png"
              alt="kakao Login Btn"
            />
          </button>
        </div>
      </div>
    </>
  );
}

export default Login;
