import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { RootState } from "@reducer";
import { useDispatch, useSelector } from "react-redux";
import { setOpenProfile } from "@reducer/HeaderModalStateInfo";
import { myprofile } from "@assets/icons";
import {
  CalendarButton,
  StartDateSelect,
  EndDateSelect,
} from "@components/common/calendar";
import styled from "styled-components";
import {
  setEndDate,
  setLatestDate,
  setStartDate,
} from "@reducer/DateSelectionInfo";
import ParsingDate from "@components/ParsingDate";
import SiteList from "@components/common/dropdown/SiteList";
import DropDown from "@components/common/dropdown/DropDown";
import { ItemType } from "@customtypes/dataTypes";
import { setSelectedSite } from "@reducer/SelectedItemInfo";
import { Modal } from "@components/common/modal";

const HeaderContainer = styled.div`
  width: 100%;
  height: 5%;
  top: 0;
  right: 0;
  margin-bottom: 1%;
  background-color: ${(props) => props.theme.colors.b2};
`;

const HeaderWrapper = styled.div`
  width: 100%;
  height: 70%;
  margin-top: 15px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-end;
  font-weight: 900;
  color: white;
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

const CalendarContainer = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: flex-end;
`;
const CalendarWrapper = styled.div`
  width: 38%;
  height: 100%;
  margin-right: 20px;
  cursor: pointer;
`;

const DateSelectContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
`;

const DateHeader = styled.div`
  margin-top: 1rem;
  font-size: 1.5rem;
`;

const DateText = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-start;
  width: 100%;
  height: 50%;
  margin-left: 70px;
  font-size: 18px;
`;

const SettingDate = styled.button`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 8rem;
  height: 14rem;
  cursor: pointer;
  background-color: ${(props) => props.theme.colors.a1};
  border-radius: 8px;
  font-size: 18px;
  font-weight: 700;
  color: black;
  margin-top: 1rem;
  margin-bottom: 1.5rem;
`;

function Header() {
  const navi = useNavigate();
  const dispatch = useDispatch();
  const location = useLocation();
  const openDropdown = useSelector(
    (state: RootState) => state.HeaderModalStateInfo.openDropdown,
  );

  const startDate = useSelector(
    (state: RootState) => state.DateSelectionInfo.start,
  );
  const endDate = useSelector(
    (state: RootState) => state.DateSelectionInfo.end,
  );
  const pastDate = useSelector(
    (state: RootState) => state.DateSelectionInfo.past,
  );
  const latestDate = useSelector(
    (state: RootState) => state.DateSelectionInfo.latest,
  );

  const selectedSite = useSelector(
    (state: RootState) => state.SelectedItemInfo.selectedSite,
  );

  const [isProfile, setIsProfile] = useState<boolean>(false);
  const [currentPathname, setCurrentPathname] = useState<string>(
    location.pathname,
  );
  const [dateModal, setDateModal] = useState<boolean>(false);
  const [newStartDate, setNewStartDate] = useState<string>(startDate);
  const [newEndDate, setNewEndDate] = useState<string>(endDate);

  useEffect(() => {
    if (location.pathname !== currentPathname) {
      dispatch(setStartDate(pastDate));
      dispatch(setEndDate(latestDate));
      setCurrentPathname(location.pathname);
      console.log(currentPathname);
      console.log(location.pathname);
    }
    setCurrentPathname(location.pathname);
  }, [location.pathname, currentPathname, dispatch, pastDate, latestDate]);

  useEffect(() => {
    // 최신 날짜 오늘로 갱신
    const todayDate = new Date();
    const today: string = ParsingDate(todayDate);
    dispatch(setLatestDate(today));
  });

  useEffect(() => {
    if (openDropdown) {
      setIsProfile(false);
      dispatch(setOpenProfile(false));
    }
  }, [openDropdown, dispatch, setIsProfile]);

  const handleOpenProfile = (e: React.MouseEvent) => {
    e.stopPropagation();
    const newIsProfile = !isProfile;
    setIsProfile(newIsProfile);
    dispatch(setOpenProfile(newIsProfile));
  };

  const handleSelectedSite = (item: ItemType) => {
    dispatch(setSelectedSite(item.name));
  };
  const handlenewStartDate = (item: string) => {
    setNewStartDate(item);
  };

  const handlenewEndDate = (item: string) => {
    setNewEndDate(item);
  };

  const setDateRange = () => {
    dispatch(setStartDate(newStartDate));
    dispatch(setEndDate(newEndDate));
    console.log(newStartDate);
    console.log(newEndDate);
  };

  const formatDateString = (dateString: string): string => {
    const parts = dateString.split("-");
    const year = parseInt(parts[0], 10);
    const month = parseInt(parts[1], 10);
    const day = parseInt(parts[2], 10);
    return `${year}년 ${month}월 ${day}일`;
  };

  const parseStartDate = formatDateString(startDate);
  const parseEndDate = formatDateString(endDate);

  return (
    <HeaderContainer>
      <HeaderWrapper>
        {(currentPathname === "/track" ||
          currentPathname === "/user" ||
          currentPathname === "/active" ||
          currentPathname === "/button") && (
          <CalendarContainer>
            <CalendarWrapper
              onClick={(e: React.MouseEvent) => {
                e.stopPropagation();
                setDateModal(!dateModal);
              }}
            >
              <CalendarButton
                width="100%"
                height="100%"
                startDate={parseStartDate}
                endDate={parseEndDate}
              />
            </CalendarWrapper>
          </CalendarContainer>
        )}
        {dateModal && (
          <Modal
            width="24rem"
            height="22rem"
            $posX="75%"
            $posY="-30%"
            $position="absolute"
            close={() => setDateModal(false)}
          >
            <DateSelectContainer>
              <DateHeader>기간 선택</DateHeader>
              <DateText>시작 날짜</DateText>
              <StartDateSelect onChange={handlenewStartDate} />
              <DateText>종료 날짜</DateText>
              <EndDateSelect onChange={handlenewEndDate} />
              <SettingDate onClick={setDateRange}>설정</SettingDate>
            </DateSelectContainer>
          </Modal>
        )}
        <DropDown
          items={SiteList}
          width="15rem"
          height="3rem"
          placeholder="사이트를 설정해주세요."
          initialValue={selectedSite}
          onChange={handleSelectedSite}
        />
        <ProfileWrapper>
          <ProfileImg
            src={myprofile}
            alt="my profile"
            onClick={handleOpenProfile}
          />
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
                onClick={() => {
                  navi("/login");
                  setIsProfile(false);
                }}
              >
                로그인 / 로그아웃
              </Option>
              <Option
                onClick={() => {
                  if (currentPathname !== "/main") {
                    navi("/main");
                  } else {
                    navi("/mysite");
                  }

                  setIsProfile(false);
                }}
              >
                {currentPathname === "/main"
                  ? "사이트 선택하러 가기"
                  : "메인으로 가기"}
              </Option>
            </Modal>
          )}
        </ProfileWrapper>
      </HeaderWrapper>
    </HeaderContainer>
  );
}

export default Header;
