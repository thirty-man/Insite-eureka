import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { RootState } from "@reducer";
import { useDispatch, useSelector } from "react-redux";
import { IconUser } from "@assets/icons";
import CalendarButton from "@components/common/calendar";
import styled from "styled-components";
import {
  setEndDate,
  setLatestDate,
  setStartDate,
} from "@reducer/DateSelectionInfo";
import ParsingDate from "@components/ParsingDate";
import DropDown from "@components/common/dropdown/DropDown";
import { ApplicationDtoType, ItemType } from "@customtypes/dataTypes";
import { setSelectedSite } from "@reducer/SelectedItemInfo";
import { Modal } from "@components/common/modal";
import { getSiteList } from "@api/memberApi";

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
  width: 38%;
  min-width: 38%;
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
  background-color: #6646ef;
  border-radius: 8px;
  font-size: 18px;
  font-weight: 700;
  color: white;
  margin-top: 1rem;
  margin-bottom: 1.5rem;
`;
const StartDateSelectContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-direction: row;
  width: 100%;
  height: 100%;
  font-size: 1.2rem;
`;
const EndDateSelectContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-direction: row;
  width: 100%;
  height: 100%;
  font-size: 1.2rem;
`;

function Header() {
  const navi = useNavigate();
  const dispatch = useDispatch();
  const location = useLocation();

  const [openSite, setOpenSite] = useState<boolean>(false);
  const [openProfile, setOpenProfile] = useState<boolean>(false);
  const [openDate, setOpenDate] = useState<boolean>(false);

  const [openDropStartYear, setOpenDropStartYear] = useState<boolean>(false);
  const [openDropStartMonth, setOpenDropStartMonth] = useState<boolean>(false);
  const [openDropStartDay, setOpenDropStartDay] = useState<boolean>(false);

  const [openDropEndYear, setOpenDropEndYear] = useState<boolean>(false);
  const [openDropEndMonth, setOpenDropEndMonth] = useState<boolean>(false);
  const [openDropEndDay, setOpenDropEndDay] = useState<boolean>(false);
  const [siteList, setSiteList] = useState([]);

  const [startYearOptions, setStartYearOptions] = useState<ItemType[]>([]);
  const [startMonthOptions, setStartMonthOptions] = useState<ItemType[]>([]);
  const [startDayOptions, setStartDayOptions] = useState<ItemType[]>([]);

  const [endYearOptions, setEndYearOptions] = useState<ItemType[]>([]);
  const [endMonthOptions, setEndMonthOptions] = useState<ItemType[]>([]);
  const [endDayOptions, setEndDayOptions] = useState<ItemType[]>([]);

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

  const parseString = (dateStr: string) => {
    const [year, month, day] = dateStr.split("-");
    return [year, month, day];
  };

  const [startYear, setStartYear] = useState(parseString(startDate)[0]);
  const [startMonth, setStartMonth] = useState(parseString(startDate)[1]);
  const [startDay, setStartDay] = useState(parseString(startDate)[2]);

  const [endYear, setEndYear] = useState(parseString(endDate)[0]);
  const [endMonth, setEndMonth] = useState(parseString(endDate)[1]);
  const [endDay, setEndDay] = useState(parseString(endDate)[2]);

  const [currentPathname, setCurrentPathname] = useState<string>(
    location.pathname,
  );
  const [newStartDate, setNewStartDate] = useState<string>(startDate);
  const [newEndDate, setNewEndDate] = useState<string>(endDate);

  const isLeapYear = (year: number): boolean => {
    return (year % 4 === 0 && year % 100 !== 0) || year % 400 === 0;
  };

  useEffect(() => {
    // 문자열을 숫자로 변환
    const pastYearNum = parseInt(parseString(pastDate)[0], 10);
    const latestYearNum = parseInt(parseString(latestDate)[0], 10);
    const startYearNum = parseInt(startYear, 10);
    const endYearNum = parseInt(endYear, 10);

    const pastMonthNum = parseInt(parseString(pastDate)[1], 10);
    const latestMonthNum = parseInt(parseString(latestDate)[1], 10);
    const startMonthNum = parseInt(startMonth, 10);
    const endMonthNum = parseInt(endMonth, 10);

    const pastDayNum = parseInt(parseString(pastDate)[2], 10);
    const latestDayNum = parseInt(parseString(latestDate)[2], 10);
    const startDayNum = parseInt(startDay, 10);
    const endDayNum = parseInt(endDay, 10);

    // 시작 연도가 과거 연도와 같은 경우
    if (startYearNum === pastYearNum) {
      // 시작 월 옵션 재설정
      setStartMonthOptions(
        Array.from({ length: 12 - pastMonthNum + 1 }, (_, i) => ({
          id: i,
          name: (pastMonthNum + i).toString(),
        })),
      );

      // 시작 월이 과거 월과 같은 경우
      if (startMonthNum === pastMonthNum) {
        // 시작 일 옵션 재설정
        setStartDayOptions(
          Array.from({ length: 31 - pastDayNum }, (_, i) => ({
            id: i,
            name: (pastDayNum + 1 + i).toString(),
          })),
        );

        // 시작 일이 과거 일보다 이른 경우
        if (startDayNum <= pastDayNum) {
          setStartDay((pastDayNum + 1).toString());
        }
      }
    }

    // 시작 연도 옵션 설정
    setStartYearOptions(
      Array.from({ length: endYearNum - pastYearNum + 1 }, (_, i) => ({
        id: i,
        name: (startYearNum + i).toString(),
      })),
    );

    // 종료 연도 옵션 설정
    setEndYearOptions(
      Array.from({ length: latestYearNum - startYearNum + 1 }, (_, i) => ({
        id: i,
        name: (startYearNum + i).toString(),
      })),
    );

    // 월 옵션 설정
    const startMonthOptionsTemp = Array.from(
      { length: 12 - startMonthNum + 1 },
      (_, i) => ({
        id: i,
        name: (startMonthNum + i).toString(),
      }),
    );

    let endMonthOptionsTemp;
    if (endYearNum === startYearNum) {
      endMonthOptionsTemp = startMonthOptionsTemp;
    } else if (endYearNum === latestYearNum) {
      endMonthOptionsTemp = Array.from({ length: latestMonthNum }, (_, i) => ({
        id: i,
        name: (i + 1).toString(),
      }));
    } else {
      endMonthOptionsTemp = Array.from({ length: 12 }, (_, i) => ({
        id: i,
        name: (i + 1).toString(),
      }));
    }

    setStartMonthOptions(startMonthOptionsTemp);
    setEndMonthOptions(endMonthOptionsTemp);

    // 날짜 유효성 검사
    if (endYearNum === latestYearNum && endMonthNum > latestMonthNum) {
      // 종료 월을 최신 월로 설정
      setEndMonth(latestMonthNum.toString());
    }

    const getDayOptions = (year: number, month: number, limitDay?: number) => {
      let daysInMonth;

      if (month === 2) {
        daysInMonth = isLeapYear(year) ? 29 : 28;
      } else {
        daysInMonth = new Date(year, month, 0).getDate();
      }

      if (limitDay) {
        daysInMonth = Math.min(daysInMonth, limitDay);
      }

      return Array.from({ length: daysInMonth }, (_, i) => ({
        id: i,
        name: (i + 1).toString(),
      }));
    };
    // 날짜 유효성 검사
    // 종료 연도가 최신 연도와 동일하고 종료 월이 최신 월과 같은 경우
    if (
      endYearNum === latestYearNum &&
      endMonthNum === latestMonthNum &&
      endDayNum > latestDayNum
    ) {
      // 종료 일을 최신 일로 설정
      setEndDay(latestDayNum.toString());
    }

    // 시작 일 옵션 설정
    setStartDayOptions(getDayOptions(startYearNum, startMonthNum));

    // 종료 일 옵션 설정
    if (endYearNum === latestYearNum && endMonthNum === latestMonthNum) {
      setEndDayOptions(getDayOptions(endYearNum, endMonthNum, latestDayNum));
    } else {
      setEndDayOptions(getDayOptions(endYearNum, endMonthNum));
    }
  }, [
    startDay,
    endDay,
    startDate,
    endDate,
    pastDate,
    latestDate,
    startYear,
    endYear,
    startMonth,
    endMonth,
  ]);

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
      setOpenDate(false);
      closeDateDropdown();
    }
    if (openProfile) {
      setOpenSite(false);
      setOpenDate(false);
      closeDateDropdown();
    }
    if (openDate) {
      setOpenProfile(false);
      setOpenSite(false);
      closeDateDropdown();
    }
  }, [openSite, openProfile, openDate]);

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
    const todayDate = new Date();
    const today: string = ParsingDate(todayDate);
    dispatch(setLatestDate(today));
  });

  const handleToggleProfile = (e: React.MouseEvent) => {
    e.stopPropagation();
    setOpenSite(false);
    setOpenDate(false);
    setOpenProfile((p) => !p);
  };

  const handleSelectedSite = (item: ApplicationDtoType) => {
    dispatch(setSelectedSite(item.name));
    const myApp = {
      id: item.id,
      name: item.name,
      applicationUrl: item.applicationUrl,
      applicationToken: item.applicationToken,
      createTime: item.createTime,
    };
    sessionStorage.setItem("myApp", JSON.stringify(myApp));
    navi("/board");
    window.location.reload();
  };

  const handleStartYear = (item: ItemType) => {
    setStartYear(item.name);
  };
  const handleStartMonth = (item: ItemType) => {
    setStartMonth(item.name);
  };
  const handleStartDay = (item: ItemType) => {
    setStartDay(item.name);
  };

  const handleEndYear = (item: ItemType) => {
    setEndYear(item.name);
  };
  const handleEndMonth = (item: ItemType) => {
    setEndMonth(item.name);
  };
  const handleEndDay = (item: ItemType) => {
    setEndDay(item.name);
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

  const setDateRange = () => {
    setNewStartDate(`${startYear}-${startMonth}-${startDay}`);
    setNewEndDate(`${endYear}-${endMonth}-${endDay}`);
    dispatch(setStartDate(newStartDate));
    dispatch(setEndDate(newEndDate));
    setOpenDate(false);
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
            <CalendarWrapper
              onClick={(e: React.MouseEvent) => {
                e.stopPropagation();
                setOpenProfile(false);
                setOpenSite(false);
                setOpenDate((p) => !p);
              }}
            >
              <CalendarButton
                startDate={parseStartDate}
                endDate={parseEndDate}
              />
            </CalendarWrapper>
          </CalendarContainer>
        )}
        {openDate && (
          <Modal
            width="24rem"
            height="22rem"
            $posX="15%"
            $posY="60%"
            $position="absolute"
            close={() => setOpenDate(false)}
          >
            <DateSelectContainer>
              <DateHeader>기간 선택</DateHeader>
              <DateText>시작 날짜</DateText>
              <StartDateSelectContainer>
                <DropDown
                  items={startYearOptions}
                  width="20%"
                  height="3rem"
                  initialValue={parseString(startDate)[0]}
                  onChange={handleStartYear}
                  openDropdown={openDropStartYear}
                  close={() => setOpenDropStartYear(false)}
                  toggle={handleToggleStartYear}
                />
                년
                <DropDown
                  items={startMonthOptions}
                  width="20%"
                  height="3rem"
                  initialValue={parseString(startDate)[1]}
                  onChange={handleStartMonth}
                  openDropdown={openDropStartMonth}
                  close={() => setOpenDropStartMonth(false)}
                  toggle={handleToggleStartMonth}
                />
                월
                <DropDown
                  items={startDayOptions}
                  width="20%"
                  height="3rem"
                  initialValue={parseString(startDate)[2]}
                  onChange={handleStartDay}
                  openDropdown={openDropStartDay}
                  close={() => setOpenDropStartDay(false)}
                  toggle={handleToggleStartDay}
                />
                일
              </StartDateSelectContainer>
              <DateText>종료 날짜</DateText>
              <EndDateSelectContainer>
                <DropDown
                  items={endYearOptions}
                  width="20%"
                  height="3rem"
                  initialValue={parseString(endDate)[0]}
                  onChange={handleEndYear}
                  openDropdown={openDropEndYear}
                  close={() => setOpenDropEndYear(false)}
                  toggle={handleToggleEndYear}
                />
                년
                <DropDown
                  items={endMonthOptions}
                  width="20%"
                  height="3rem"
                  initialValue={parseString(endDate)[1]}
                  onChange={handleEndMonth}
                  openDropdown={openDropEndMonth}
                  close={() => setOpenDropEndMonth(false)}
                  toggle={handleToggleEndMonth}
                />
                월
                <DropDown
                  items={endDayOptions}
                  width="20%"
                  height="3rem"
                  initialValue={parseString(endDate)[2]}
                  onChange={handleEndDay}
                  openDropdown={openDropEndDay}
                  close={() => setOpenDropEndDay(false)}
                  toggle={handleToggleEndDay}
                />
                일
              </EndDateSelectContainer>
              <SettingDate onClick={setDateRange}>설정</SettingDate>
            </DateSelectContainer>
          </Modal>
        )}
        {siteList.length > 0 ? (
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
              setOpenDate(false);
              setOpenSite((p) => !p);
            }}
          />
        ) : (
          <div>사이트를 등록해주세요</div>
        )}
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
                  sessionStorage.clear();
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
