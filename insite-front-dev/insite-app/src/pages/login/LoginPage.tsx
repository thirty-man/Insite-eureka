import { kakaoLoginButton } from "@assets/images";
import BackgroundDiv from "@components/common/BackgroundDiv";
import { ImageButton } from "@components/common/button";
import axios from "axios";
import { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import styled from "styled-components";
import InSiteLogo from "@assets/images/슬로건_배경제거.jpg";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  width: 100%;
`;

const ImagePanda = styled.img`
  width: 55%;
`;

const TitleContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 40%;
`;

function LoginPage() {
  const { VITE_KAKAO_REDIRECT_URI, VITE_LOGIN_API_URI, VITE_KAKAO_CLIENT_ID } =
    import.meta.env;
  const location = useLocation();
  const navi = useNavigate();

  const authenticateUser = (code: string) => {
    axios
      .post(`${VITE_LOGIN_API_URI}/api/v1/members/login`, { code })
      .then((response) => {
        const authToken = response.headers.authorization;
        const refreshToken = response.headers.refreshtoken;

        if (authToken) {
          sessionStorage.setItem("Authorization", authToken);
          sessionStorage.setItem("RefreshToken", refreshToken);
        }
        navi("/");
      });
  };

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const code = params.get("code");

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
    <BackgroundDiv>
      <Container>
        <TitleContainer>
          <ImagePanda src={InSiteLogo} alt="인사이트 판다" />
        </TitleContainer>
        <ImageButton
          width="20%"
          height="10%"
          onClick={handleLoginClick}
          src={kakaoLoginButton}
          alt="kakao Login Btn"
        />
      </Container>
    </BackgroundDiv>
  );
}

export default LoginPage;
