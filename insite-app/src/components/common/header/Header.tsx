import { useState } from "react";
import { myprofile } from "@assets/icons";
import styled from "styled-components";
import { ItemTypes } from "@customtypes/dataTypes";
import Modal from "../modal/Modal";
import DropDown from "../dropdown/DropDown";

const HeaderContainer = styled.div`
  position: fixed;
  width: 82%;
  height: 10%;
  top: 0;
  right: 0;
  background-color: ${(props) => props.theme.colors.b2};
`;

const HeaderWrapper = styled.div`
  width: 100%;
  height: 50%;
  margin-top: 15px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-end;
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
  justify-content: center;
  width: 3rem;
  height: 3rem;
  margin-top: 10px;
  margin-right: 35px;
  margin-left: 15px;
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

function Header() {
  const [openProfile, setOpenProfile] = useState<boolean>(false);
  const [openDropdown, setOpenDropdown] = useState<boolean>(false);
  const reduxStateValue: string | null = "moduo.kr";
  const sites: ItemTypes[] = [
    { id: 0, name: "moduo.kr" },
    { id: 1, name: "naver.com" },
    { id: 2, name: "google.com" },
    { id: 3, name: "moduo.kr" },
    { id: 4, name: "moduo.kr" },
    { id: 5, name: "moduo.kr" },
    { id: 6, name: "moduo.kr" },
    { id: 7, name: "moduo.kr" },
    { id: 8, name: "moduo.kr" },
    { id: 9, name: "moduo.kr" },
  ];

  const handleProfileClick = (e: React.MouseEvent) => {
    e.stopPropagation();
    setOpenProfile(!openProfile);
    setOpenDropdown(false);
  };

  const handleDropdownClick = (e: React.MouseEvent) => {
    e.stopPropagation();
    setOpenProfile(false);
  };

  return (
    <HeaderContainer>
      <HeaderWrapper>
        <DropDown
          items={sites}
          width="15rem"
          height="1rem"
          placeholder="사이트를 설정해주세요."
          initialValue={reduxStateValue}
          openDropdown={openDropdown}
          setOpenDropdown={setOpenDropdown}
          onClickProfile={handleDropdownClick}
        />
        <ProfileWrapper>
          <ProfileImg
            src={myprofile}
            alt="my profile"
            onClick={handleProfileClick}
          />
          {openProfile && (
            <Modal
              width="15rem"
              height="6.5rem"
              posX="-50%"
              posY="80%"
              close={() => setOpenProfile(false)}
              position="absolute" // absolute 포지션을 설정합니다.
            >
              <Option>로그인 / 로그아웃</Option>
              <Option>메인으로 가기</Option>
            </Modal>
          )}
        </ProfileWrapper>
      </HeaderWrapper>
    </HeaderContainer>
  );
}

export default Header;
