import { insiteText, insitepanda, kakaoLoginButton } from "@assets/images";
import BackgroudDiv from "@components/common/BackgroudDiv";
import DefaultBox from "@components/common/DefaultBox";
import ImageButton from "@components/common/button/ImageButton";
import axios from "axios";
import { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import styled from "styled-components";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  width: 100%;
`;

const ImagePanda = styled.img`
  width: 45%;
  height: 70%;
`;

const ImageName = styled.img`
  width: 50%;
  height: 30%;
`;

const TitleContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 70%;
  width: 100%;
  margin-bottom: 5%;
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
    <BackgroudDiv>
      <DefaultBox width="500px" height="500px">
        <Container>
          <TitleContainer>
            <ImagePanda src={insitepanda} alt="인사이트 판다" />
            <ImageName src={insiteText} alt="인사이트 이름" />
          </TitleContainer>
          <ImageButton
            width="50%"
            height="8%"
            onClick={handleLoginClick}
            borderRadius="0px"
            src={kakaoLoginButton}
            alt="kakao Login Btn"
          />
        </Container>
      </DefaultBox>
    </BackgroudDiv>
  );
}

export default LoginPage;
