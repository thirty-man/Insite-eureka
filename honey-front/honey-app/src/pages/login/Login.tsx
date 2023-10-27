import HelpIcon from "@assets/icons";
import { KakaoLoginButton, PoohHelpModal, PoohLogin } from "@assets/images";
import { Modal } from "@components/common/modal";
import { useState, useEffect } from "react";
import axios from "axios";
import useRouter from "@hooks/useRouter";
import { useLocation } from "react-router-dom";

function Login() {
  const [helpOpen, setHelpOpen] = useState<boolean>(false);
  const VITE_KAKAO_CLIENT_ID = "367be5f2a1031bc9fb556dd456869c88";
  const VITE_KAKAO_REDIRECT_URI = "http://localhost:3000/login";
  const { routeTo } = useRouter();
  const location = useLocation();

  const authenticateUser = (code: string) => {
    axios
      .post("http://localhost:8080/api/v1/members/login", { code })
      .then((response) => {
        console.log(response);
        const authToken = response.headers.authorization;
        const refreshToken = response.headers.refreshtoken;
        console.log("헤더 auth", authToken);
        console.log("헤더 ref", refreshToken);

        // const refreshToken = response.headers.authorization;
        if (authToken) {
          sessionStorage.setItem("Authorization", authToken);
          sessionStorage.setItem("RefreshToken", refreshToken);
        }
        routeTo("/");
      })
      .catch((error) => {
        console.log("Error:", error);
      });
  };

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const code = params.get("code");
    console.log(code);

    // If code exists, authenticate the user
    if (code) {
      authenticateUser(code);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [location]);

  const handleLoginClick = () => {
    const KAKAO_BASE_URL = "https://kauth.kakao.com/oauth/authorize";
    window.location.href = `${KAKAO_BASE_URL}?client_id=${VITE_KAKAO_CLIENT_ID}&redirect_uri=${VITE_KAKAO_REDIRECT_URI}&response_type=code`;
  };

  return (
    <>
      <div className="flex h-10 mt-5 items-center justify-end px-5">
        <img
          src={HelpIcon}
          alt="help"
          aria-hidden
          onClick={() => setHelpOpen(true)}
          className="cursor-pointer"
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
          <button type="button" onClick={handleLoginClick}>
            <img src={KakaoLoginButton} alt="kakao Login Btn" />
          </button>
        </div>
      </div>
    </>
  );
}

export default Login;
