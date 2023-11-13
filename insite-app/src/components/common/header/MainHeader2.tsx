/* eslint-disable react/prop-types */
/* eslint-disable import/no-extraneous-dependencies */
/* eslint-disable react/self-closing-comp */
/* eslint-disable import/order */
/* eslint-disable @typescript-eslint/no-unused-vars */
import {useState } from "react";
import {useNavigate } from "react-router-dom";
import { IconUser } from "@assets/icons";
import styled from "styled-components";
import Modal from "../modal/Modal";
import ImageButton from "../button/ImageButton";
import { animated, useSpring, SpringValue } from "@react-spring/web";
import InSiteLogo from "@assets/images/InSiteLogo3.svg";

interface MainHeaderProps {
  scrollY: SpringValue<number>;
}

const AnimatedHeaderContainer = styled(animated.div)`
  width: 100%;
  height: 120px;
  top: 0;
  right: 0;
  /* background-color: rgba(0, 0, 0, 0.7); */
  position: fixed; // ensure the header is fixed at the top
  z-index: 10; // make sure it's above other content
`;

const HeaderWrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  font-weight: 900;
  position: fixed;
`;

const ProfileWrapper = styled.div`
  display: flex;
  align-items: center;
  position: relative;
  padding-right: 2.5%;
  padding-top: 10px;
`;

const ProfileButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 2.8rem;
  height: 2.8rem;
  background-image: linear-gradient(
    to right,
    #4776e6 0%,
    #8e54e9 100%,
    #4776e6
  );
  border-radius: 15px;
  margin-top: 2px;
  margin-right: 35px;
  margin-left: 15px;
  cursor: pointer;

  &:hover {
    text-decoration: none;
    transform: scale(1.1);
    transition: transform 0.3s ease;
  }

  &:active {
    transform: scale(0.96);
    transition: transform 0.1s;
  }
`;
const ProfileImg = styled.img`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 60%;
  height: 60%;
  cursor: pointer;
`;

const Option = styled.button`
  width: 100%;
  color: white;
  background-color: ${(props) => props.theme.colors.b3};
  font-size: 1rem;
  height: 2.5rem;
  margin-top: 0.5rem;
  &:hover {
    border-radius: 0.6rem;
    background-color: rgba(255, 255, 255, 0.1);
    cursor: pointer;
  }
`;

const LogoContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  width: 100%;
  height: 10%;
  margin-top: 30px;
  margin-bottom: 20px;
  padding-left: 3.5%;
`;

const LogoImgWrapper = styled.div`
  display: flex;
  align-items: center;
  padding-left: 50px;
`;

function MainHeader({ scrollY }: MainHeaderProps) {
  const navi = useNavigate();
  const [isProfile, setIsProfile] = useState<boolean>(false);

  const handleOpenProfile = (e: React.MouseEvent) => {
    e.stopPropagation();
    const newIsProfile = !isProfile;
    setIsProfile(newIsProfile);
  };

  // Use the scrollY value to interpolate the scale and opacity
  const style = useSpring({
    transform: scrollY.interpolate((y) => `scale(${1 + y / 5000})`), // Adjust scale rate as needed
    opacity: scrollY.interpolate((y) => 1 - y / 300), // Adjust fade out rate as needed
  });

  return (
    <AnimatedHeaderContainer style={style}>
      <HeaderWrapper>
        <LogoContainer>
          <LogoImgWrapper>
            <ImageButton
              width="20%"
              height="100%"
              src={InSiteLogo}
              alt="insite Home Logo"
              onClick={() => navi("/main")}
            />
          </LogoImgWrapper>
        </LogoContainer>
        <ProfileWrapper>
          <ProfileButton onClick={handleOpenProfile}>
            <ProfileImg src={IconUser} alt="my profile" />
          </ProfileButton>
          {isProfile && (
            <Modal
              width="15rem"
              height="6.5rem"
              $posX="-50%"
              $posY="80%"
              close={() => setIsProfile(false)}
              $position="absolute"
            >
              <Option
                onClick={(e) => {
                  e.stopPropagation();
                  navi("/login");
                }}
              >
                로그인 / 로그아웃
              </Option>
              <Option
                onClick={(e) => {
                  e.stopPropagation();
                  navi("/mysite");
                }}
              >
                사이트 선택하러 가기
              </Option>
            </Modal>
          )}
        </ProfileWrapper>
      </HeaderWrapper>
    </AnimatedHeaderContainer>
  );
}
export default MainHeader;
