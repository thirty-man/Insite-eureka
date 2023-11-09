import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { myprofile } from "@assets/icons";
import styled from "styled-components";
import { homeLogo } from "@assets/images";
import Modal from "../modal/Modal";
import ImageButton from "../button/ImageButton";

const HeaderContainer = styled.div`
  width: 100%;
  height: 10%;
  top: 0;
  right: 0;
  background-color: ${(props) => props.theme.colors.b2};
`;

// const HeaderWrapper = styled.div`
//   width: 100%;
//   height: 50%;
//   margin-top: 15px;
//   display: flex;
//   flex-direction: row;
//   align-items: center;
//   justify-content: flex-end;
//   font-weight: 900;
// `;
const HeaderWrapper = styled.div`
  width: 100%;
  height: 50%;
  margin-top: 15px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  font-weight: 900;
`;

const ProfileWrapper = styled.div`
  display: flex;
  align-items: center;
  position: relative;
`;

const ProfileImg = styled.img`
  display: flex;
  align-items: center;
  //   justify-content: center;
  width: 3rem;
  height: 3rem;
  margin-top: 10px;
  margin-right: 35px;
  margin-left: 15px;
  cursor: pointer;
`;

const LogoContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  //   justify-content: center;
  width: 100%;
  height: 10%;
  margin-top: 20px;
  margin-bottom: 20px;
`;

const LogoImgWrapper = styled.div`
  display: flex;
  align-items: center;
  //   justify-content: center;
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
function MainHeader() {
  const navi = useNavigate();
  const [openProfile, setOpenProfile] = useState<boolean>(false);

  const handleToggleProfile = (e: React.MouseEvent) => {
    e.stopPropagation();
    setOpenProfile((p) => !p);
  };

  return (
    <HeaderContainer>
      <HeaderWrapper>
        <LogoContainer>
          <LogoImgWrapper>
            <ImageButton
              width="100%"
              height="100%"
              src={homeLogo}
              alt="insite Home Logo"
              onClick={() => navi("/main")}
            />
          </LogoImgWrapper>
        </LogoContainer>
        <ProfileWrapper>
          <ProfileImg
            src={myprofile}
            alt="my profile"
            onClick={handleToggleProfile}
          />
          {openProfile && (
            <Modal
              width="15rem"
              height="6.5rem"
              $posX="-50%"
              $posY="80%"
              close={() => setOpenProfile(false)}
              $position="absolute"
            >
              <Option
                onClick={() => {
                  navi("/login");
                  setOpenProfile(false);
                }}
              >
                로그인 / 로그아웃
              </Option>
              {/* 다른 Option을 추가할 수 있음 */}
            </Modal>
          )}
        </ProfileWrapper>
      </HeaderWrapper>
    </HeaderContainer>
  );
}
export default MainHeader;
