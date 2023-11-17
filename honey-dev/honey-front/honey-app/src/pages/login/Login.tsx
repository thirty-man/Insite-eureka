import HelpIcon from "@assets/icons";
import { KakaoLoginButton, newbear, threebear } from "@assets/images";
import { Alert, Modal } from "@components/common/modal";
import { useState, useEffect } from "react";
import axios from "axios";
import useRouter from "@hooks/useRouter";
import { useLocation, useNavigate } from "react-router-dom";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { logoutState } from "@recoil/atom";
import { traceButton } from "../../Tracebutton";

function Login() {
  const [helpOpen, setHelpOpen] = useState<boolean>(false);
  const { VITE_KAKAO_CLIENT_ID } = import.meta.env;
  const { VITE_KAKAO_REDIRECT_URI } = import.meta.env;
  const { VITE_API_URL } = import.meta.env;
  const { routeTo } = useRouter();
  const location = useLocation();
  const logout = useRecoilValue(logoutState);
  const setLogout = useSetRecoilState(logoutState);
  const [logoutModal, setLogoutModal] = useState<boolean>(logout);
  const navi = useNavigate();

  const authenticateUser = (code: string) => {
    axios
      // .post(`${VITE_API_URL}/api/v1/members/login`, { code })
      .post(`${VITE_API_URL}/api/v1/members/login`, { code })
      .then((response) => {
        const authToken = response.headers.authorization;
        const refreshToken = response.headers.refreshtoken;

        // const refreshToken = response.headers.authorization;
        if (authToken) {
          sessionStorage.setItem("Authorization", authToken);
          sessionStorage.setItem("RefreshToken", refreshToken);
        }
        // sessionStorage.setItem("redirectUrl", window.location.href);
        const savedUrl = sessionStorage.getItem("redirectUrl");

        if (savedUrl) {
          const relativePath = savedUrl.replace(window.location.origin, "");
          navi(relativePath);
          // routeTo(savedUrl);
          sessionStorage.removeItem("redirectUrl");
          return;
        }
        routeTo("/");
      });
    // .catch((error) => {
    //   console.log("Error:", error);
    // });
  };

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const code = params.get("code");

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
          overz="z-[100]"
          openModal={helpOpen}
        >
          <div className="w-[100%] h-[100%] overflow-y-auto z-[20] rounded-[30px] bg-cg-7 text-white sm:text-[24px] text-[15px] px-[15px] py-[15px] flex items-center justify-center">
            <div className="text-[20px]">
              <span>친구들을 방에 초대해서 서로</span>
              <br />
              <span className="text-cg-1 sm:text-[26px] text-[26px]">
                꿀단지
              </span>
              <span>를 써보세요!</span>
              <br />
              <br />
              <span className="text-cg-1 sm:text-[26px] text-[26px]">
                꿀단지
              </span>
              <span>는</span>
              <span className="text-cg-1 sm:text-[26px] text-[26px]">
                개봉일
              </span>
              <span>이 지나야</span>
              <br />
              <span>확인할 수 있어요!</span>
            </div>
          </div>
          <Modal
            className="fixed w-[150px] h-[120px] bottom-1/2 left-1/2 -translate-x-[180px] translate-y-[190px] z-[120] bg-transparent flex items-center justify-center"
            overlay={false}
            overz=""
            openModal
          >
            <img src={newbear} className="w-[200px] h-[90px]" alt="푸 모달" />
          </Modal>
          <Modal
            className="fixed w-[100px] h-[35px] bottom-1/2 left-1/2 -translate-x-[50px] translate-y-[170px] z-[120] rounded-[60px] bg-cg-1 flex items-center justify-center"
            overlay={false}
            overz=""
            openModal
          >
            <button
              type="button"
              className="w-[100%] h-[100%] text-[24px]"
              onClick={() => {
                setHelpOpen(false);
                traceButton("로그인화면 닫기 버튼");
              }}
            >
              닫기
            </button>
          </Modal>
        </Modal>
      )}
      <div className="flex h-2/6 items-center justify-center text-[48px]">
        <span className="text-cg-3">꿀</span>&apos;
        <span>s Writing</span>
      </div>

      <div className="flex h-2/6 justify-center items-center">
        <div className="flex flex-col h-full items-center justify-center">
          <img src={threebear} alt="mainpooh" />
          <button type="button" onClick={handleLoginClick}>
            <img src={KakaoLoginButton} alt="kakao Login Btn" />
          </button>
        </div>
      </div>
      {logoutModal && (
        <Alert
          openModal={logoutModal}
          closeButton="확인"
          overz="z-[100]"
          text="로그아웃 되었습니다."
          closeAlert={() => {
            setLogout(false);
            setLogoutModal(false);
          }}
        />
      )}
    </>
  );
}

export default Login;
