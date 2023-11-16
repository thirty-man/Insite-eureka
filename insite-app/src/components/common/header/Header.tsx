import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { RootState } from "@reducer";
import { useDispatch, useSelector } from "react-redux";
import { IconUser } from "@assets/icons";
import {
  CalendarButton,
  StartDateSelect,
  EndDateSelect,
} from "@components/common/calendar";
import styled from "styled-components";
import {
  setEndDate,
  setLatestDate,
  setPastDate,
  setStartDate,
} from "@reducer/DateSelectionInfo";
import ParsingDate from "@components/ParsingDate";
import DropDown from "@components/common/dropdown/DropDown";
import { ApplicationDtoType } from "@customtypes/dataTypes";
import { setSelectedSite } from "@reducer/SelectedItemInfo";
import { Modal } from "@components/common/modal";
import { getSiteList } from "@api/memberApi";
import { setSelectedMenuId } from "@reducer/SelectedSidebarMenuInfo";

const HeaderContainer = styled.div`
  width: 100%;
  height: 10%;
  top: 0;
  right: 0;
  margin-bottom: 1%;
  background-color: ${(props) => props.theme.colors.b2};
`;

const HeaderWrapper = styled.div`
  width: 100%;
  height: 75%;
  min-width: 50%;
  margin-top: 15px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-end;
  font-weight: 500;
  color: white;
  position: relative;
`;
const ProfileWrapper = styled.div`
  display: flex;
  align-items: center;
  position: relative;
  padding-right: 2rem;
  margin-left: 3rem;
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

const CalendarContainer = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: flex-end;
`;
const CalendarWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-direction: row;
  width: 48%;
  min-width: 38%;
  height: 100%;
  margin-right: 20px;
  border-radius: 15px;
  border: 3px solid #6646ef;
  cursor: pointer;
`;
const DateText = styled.div`
  font-size: 16px;
  margin-right: 2px;
  margin-left: 2px;
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

const SettingDate = styled.button`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 6rem;
  height: 8rem;
  cursor: pointer;
  background-color: #6646ef;
  border-radius: 8px;
  font-size: 18px;
  font-weight: 700;
  color: white;
  margin-top: 1rem;
  margin-bottom: 1.5rem;
`;

function Header() {
  const navi = useNavigate();
  const dispatch = useDispatch();
  const location = useLocation();

  const [openSite, setOpenSite] = useState<boolean>(false);
  const [openProfile, setOpenProfile] = useState<boolean>(false);
  const [openStartDate, setOpenStartDate] = useState<boolean>(false);
  const [openEndDate, setOpenEndDate] = useState<boolean>(false);

  const [openDropStartYear, setOpenDropStartYear] = useState<boolean>(false);
  const [openDropStartMonth, setOpenDropStartMonth] = useState<boolean>(false);
  const [openDropStartDay, setOpenDropStartDay] = useState<boolean>(false);

  const [openDropEndYear, setOpenDropEndYear] = useState<boolean>(false);
  const [openDropEndMonth, setOpenDropEndMonth] = useState<boolean>(false);
  const [openDropEndDay, setOpenDropEndDay] = useState<boolean>(false);

  const [siteList, setSiteList] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getSiteList();
        if (!response.applicationDtoList) setSiteList([]);
        else setSiteList(response.applicationDtoList);
      } catch (error) {
        // eslint-disable-next-line no-console
        // console.error(error); // 에러 처리
      }
    };

    fetchData();
  }, []);

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

  const [currentPathname, setCurrentPathname] = useState<string>(
    location.pathname,
  );
  const [newStartDate, setNewStartDate] = useState<string>(startDate);
  const [newEndDate, setNewEndDate] = useState<string>(endDate);

  const closeDateDropdown = () => {
    setOpenDropStartYear(false);
    setOpenDropStartMonth(false);
    setOpenDropStartDay(false);
    setOpenDropEndYear(false);
    setOpenDropEndMonth(false);
    setOpenDropEndDay(false);
  };

  useEffect(() => {
    if (openSite) {
      setOpenProfile(false);
      setOpenStartDate(false);
      setOpenEndDate(false);
      closeDateDropdown();
    }
    if (openProfile) {
      setOpenSite(false);
      setOpenStartDate(false);
      setOpenEndDate(false);
      closeDateDropdown();
    }
    if (openStartDate) {
      setOpenProfile(false);
      setOpenSite(false);
      setOpenEndDate(false);
      closeDateDropdown();
    }
    if (openEndDate) {
      setOpenProfile(false);
      setOpenSite(false);
      setOpenStartDate(false);
      closeDateDropdown();
    }
  }, [openSite, openProfile, openStartDate, openEndDate]);

  useEffect(() => {
    if (openDropStartYear) {
      setOpenDropStartMonth(false);
      setOpenDropStartDay(false);
      setOpenDropEndYear(false);
      setOpenDropEndMonth(false);
      setOpenDropEndDay(false);
    }
    if (openDropStartMonth) {
      setOpenDropStartYear(false);
      setOpenDropStartDay(false);
      setOpenDropEndYear(false);
      setOpenDropEndMonth(false);
      setOpenDropEndDay(false);
    }
    if (openDropStartDay) {
      setOpenDropStartYear(false);
      setOpenDropStartMonth(false);
      setOpenDropEndYear(false);
      setOpenDropEndMonth(false);
      setOpenDropEndDay(false);
    }
    if (openDropEndYear) {
      setOpenDropStartYear(false);
      setOpenDropStartMonth(false);
      setOpenDropStartDay(false);
      setOpenDropEndMonth(false);
      setOpenDropEndDay(false);
    }
    if (openDropEndMonth) {
      setOpenDropStartYear(false);
      setOpenDropStartMonth(false);
      setOpenDropStartDay(false);
      setOpenDropEndYear(false);
      setOpenDropEndDay(false);
    }
    if (openDropEndDay) {
      setOpenDropStartYear(false);
      setOpenDropStartMonth(false);
      setOpenDropStartDay(false);
      setOpenDropEndYear(false);
      setOpenDropEndMonth(false);
    }
  }, [
    openDropStartYear,
    openDropStartMonth,
    openDropStartDay,
    openDropEndYear,
    openDropEndMonth,
    openDropEndDay,
  ]);

  useEffect(() => {
    if (location.pathname !== currentPathname) {
      dispatch(setStartDate(pastDate));
      dispatch(setEndDate(latestDate));
      setCurrentPathname(location.pathname);
    }
    setCurrentPathname(location.pathname);
  }, [location.pathname, currentPathname, dispatch, pastDate, latestDate]);

  useEffect(() => {
    // 최신 날짜 오늘로 갱신
    const todayDate = new Date();
    const today: string = ParsingDate(todayDate);
    dispatch(setLatestDate(today));
  });

  const handleToggleProfile = (e: React.MouseEvent) => {
    e.stopPropagation();
    setOpenSite(false);
    setOpenStartDate(false);
    setOpenEndDate(false);
    setOpenProfile((p) => !p);
  };

  useEffect(() => {
    const myApp =
      sessionStorage.getItem("myApp") ||
      `{"applicationId":0,"name":"사이트를 선택해주세요.","applicationUrl":"사이트를 선택해주세요", "applicationToken":"사이트를 선택해주세요", "createTime" : "사이트를 선택해주세요"}`;
    const data: ApplicationDtoType = JSON.parse(myApp);
    const { name } = data;
    dispatch(setSelectedSite(name));
  }, [dispatch]);

  const handleSelectedSite = (item: ApplicationDtoType) => {
    dispatch(setSelectedSite(item.name));
    const myApp = {
      ApplicationId: item.id,
      name: item.name,
      applicationUrl: item.applicationUrl,
      applicationToken: item.applicationToken,
      createTime: item.createTime,
    };
    sessionStorage.setItem("myApp", JSON.stringify(myApp));
    dispatch(setSelectedMenuId(1));
    dispatch(setStartDate(item.createTime));
    dispatch(setPastDate(item.createTime));
    navi("/board");
    window.location.reload();
  };
  const handlenewStartDate = (item: string) => {
    setNewStartDate(item);
  };

  const handlenewEndDate = (item: string) => {
    setNewEndDate(item);
  };

  const handleToggleStartYear = () => {
    setOpenDropStartMonth(false);
    setOpenDropStartDay(false);
    setOpenDropEndYear(false);
    setOpenDropEndMonth(false);
    setOpenDropEndDay(false);
    setOpenDropStartYear((p) => !p);
  };
  const handleToggleStartMonth = () => {
    setOpenDropStartYear(false);
    setOpenDropStartDay(false);
    setOpenDropEndYear(false);
    setOpenDropEndMonth(false);
    setOpenDropEndDay(false);
    setOpenDropStartMonth((p) => !p);
  };
  const handleToggleStartDay = () => {
    setOpenDropStartYear(false);
    setOpenDropStartMonth(false);
    setOpenDropEndYear(false);
    setOpenDropEndMonth(false);
    setOpenDropEndDay(false);
    setOpenDropStartDay((p) => !p);
  };
  const handleToggleEndYear = () => {
    setOpenDropStartYear(false);
    setOpenDropStartMonth(false);
    setOpenDropStartDay(false);
    setOpenDropEndMonth(false);
    setOpenDropEndDay(false);
    setOpenDropEndYear((p) => !p);
  };
  const handleToggleEndMonth = () => {
    setOpenDropStartYear(false);
    setOpenDropStartMonth(false);
    setOpenDropStartDay(false);
    setOpenDropEndYear(false);
    setOpenDropEndDay(false);
    setOpenDropEndMonth((p) => !p);
  };
  const handleToggleEndDay = () => {
    setOpenDropStartYear(false);
    setOpenDropStartMonth(false);
    setOpenDropStartDay(false);
    setOpenDropEndYear(false);
    setOpenDropEndMonth(false);
    setOpenDropEndDay((p) => !p);
  };

  const handleStartDate = (e: React.MouseEvent) => {
    e.stopPropagation();
    setOpenProfile(false);
    setOpenSite(false);
    setOpenEndDate(false);
    setOpenStartDate((p) => !p);
  };

  const handleEndDate = (e: React.MouseEvent) => {
    e.stopPropagation();
    setOpenProfile(false);
    setOpenSite(false);
    setOpenStartDate(false);
    setOpenEndDate((p) => !p);
  };

  const setStartDateRange = () => {
    let startD = new Date(newStartDate);
    const endD = new Date(newEndDate);
    const pastD = new Date(pastDate);

    // startDate가 endDate보다 늦거나 pastDate보다 이르면 pastDate로 설정
    if (startD > endD || startD < pastD) {
      startD = pastD;
    }

    dispatch(setStartDate(startD.toISOString().split("T")[0]));
    setOpenStartDate(false);
    setOpenEndDate(false);
  };

  const setEndDateRange = () => {
    const startD = new Date(newStartDate);
    let endD = new Date(newEndDate);
    const latestD = new Date(latestDate);

    // endDate가 latestDate를 초과하면 latestDate로 설정
    if (endD > latestD) {
      endD = latestD;
    }
    if (endD < startD) {
      endD = new Date(endDate);
    }

    dispatch(setEndDate(endD.toISOString().split("T")[0]));
    setOpenStartDate(false);
    setOpenEndDate(false);
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
        {(currentPathname === "/board/track" ||
          currentPathname === "/board/user" ||
          currentPathname === "/board/active" ||
          currentPathname === "/board/button") && (
          <CalendarContainer>
            <CalendarWrapper>
              <CalendarButton date={parseStartDate} onClick={handleStartDate} />
              <DateText>~</DateText>
              <CalendarButton date={parseEndDate} onClick={handleEndDate} />
            </CalendarWrapper>
          </CalendarContainer>
        )}
        {openStartDate && (
          <Modal
            width="26.4rem"
            height="13rem"
            $posX="4.12%"
            $posY="66%"
            $position="absolute"
            close={() => setOpenStartDate(false)}
          >
            <DateSelectContainer>
              <DateHeader>시작 날짜 선택</DateHeader>
              <StartDateSelect
                onChange={handlenewStartDate}
                openDropStartYear={openDropStartYear}
                closeDropStartYear={() => setOpenDropStartYear(false)}
                toggleDropStartYear={handleToggleStartYear}
                openDropStartMonth={openDropStartMonth}
                closeDropStartMonth={() => setOpenDropStartMonth(false)}
                toggleDropStartMonth={handleToggleStartMonth}
                openDropStartDay={openDropStartDay}
                closeDropStartDay={() => setOpenDropStartDay(false)}
                toggleDropStartDay={handleToggleStartDay}
              />
              <SettingDate onClick={setStartDateRange}>설정</SettingDate>
            </DateSelectContainer>
          </Modal>
        )}

        {openEndDate && (
          <Modal
            width="26.4rem"
            height="13rem"
            $posX="4.12%"
            $posY="66%"
            $position="absolute"
            close={() => setOpenEndDate(false)}
          >
            <DateSelectContainer>
              <DateHeader>종료 날짜 선택</DateHeader>
              <EndDateSelect
                onChange={handlenewEndDate}
                openDropEndYear={openDropEndYear}
                closeDropEndYear={() => setOpenDropEndYear(false)}
                toggleDropEndYear={handleToggleEndYear}
                openDropEndMonth={openDropEndMonth}
                closeDropEndMonth={() => setOpenDropEndMonth(false)}
                toggleDropEndMonth={handleToggleEndMonth}
                openDropEndDay={openDropEndDay}
                closeDropEndDay={() => setOpenDropEndDay(false)}
                toggleDropEndDay={handleToggleEndDay}
              />
              <SettingDate onClick={setEndDateRange}>설정</SettingDate>
            </DateSelectContainer>
          </Modal>
        )}
        <DropDown<ApplicationDtoType>
          items={siteList}
          width="15rem"
          height="3rem"
          initialValue={selectedSite}
          onChange={handleSelectedSite}
          openDropdown={openSite}
          close={() => {
            setOpenSite(false);
          }}
          toggle={() => {
            setOpenProfile(false);
            setOpenStartDate(false);
            setOpenEndDate(false);
            setOpenSite((p) => !p);
          }}
        />
        <ProfileWrapper>
          <ProfileButton>
            <ProfileImg
              src={IconUser}
              alt="my profile"
              onClick={handleToggleProfile}
            />
          </ProfileButton>
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
                로그아웃
              </Option>
              <Option
                onClick={() => {
                  navi("/mysite");
                  setOpenProfile(false);
                }}
              >
                사이트 선택하러 가기
              </Option>
            </Modal>
          )}
        </ProfileWrapper>
      </HeaderWrapper>
    </HeaderContainer>
  );
}

export default Header;
