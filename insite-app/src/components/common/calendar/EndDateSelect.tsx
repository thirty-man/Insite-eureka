import { useEffect, useState } from "react";
import styled from "styled-components";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "@reducer";
import { ItemType } from "@customtypes/dataTypes";
import DropDown from "../dropdown/DropDown";

interface EndDateSelectProps {
  onChange: (item: string) => void;
  openDropEndYear: boolean;
  closeDropEndYear: () => void;
  toggleDropEndYear: () => void;
  openDropEndMonth: boolean;
  closeDropEndMonth: () => void;
  toggleDropEndMonth: () => void;
  openDropEndDay: boolean;
  closeDropEndDay: () => void;
  toggleDropEndDay: () => void;
}

const EndDateSelectContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-direction: row;
  width: 100%;
  height: 100%;
  font-size: 1.2rem;
`;
function EndDateSelect({
  onChange,
  openDropEndYear,
  openDropEndMonth,
  openDropEndDay,
  closeDropEndYear,
  closeDropEndMonth,
  closeDropEndDay,
  toggleDropEndYear,
  toggleDropEndMonth,
  toggleDropEndDay,
}: EndDateSelectProps) {
  const dispatch = useDispatch();

  const startDate = useSelector(
    (state: RootState) => state.dateSelectionInfo.start,
  );
  const pastDate = useSelector(
    (state: RootState) => state.dateSelectionInfo.past,
  );
  const latestDate = useSelector(
    (state: RootState) => state.dateSelectionInfo.latest,
  );
  const endDate = useSelector(
    (state: RootState) => state.dateSelectionInfo.end,
  );

  const isLeapYear = (year: number) => {
    return (year % 4 === 0 && year % 100 !== 0) || year % 400 === 0;
  };

  const parseString = (dateStr: string) => {
    const [year, month, day] = dateStr.split("-");
    return [year, month, day];
  };

  const [endYear, setEndYear] = useState(parseString(endDate)[0]);
  const [endMonth, setEndMonth] = useState(parseString(endDate)[1]);
  const [endDay, setEndDay] = useState(parseString(endDate)[2]);

  const parseDate = (dateStr: string) => {
    const [year, month, day] = dateStr.split("-").map(Number);
    return new Date(year, month - 1, day); // 월은 0에서 시작합니다.
  };

  const startDateObj = startDate ? parseDate(startDate) : parseDate(pastDate);
  const latestDateObj = parseDate(latestDate);

  const getYearsInRange = (start: Date, end: Date) => {
    const sYear = start.getFullYear();
    const eYear = end.getFullYear();

    return Array.from({ length: eYear - sYear + 1 }, (i: number) =>
      (i + sYear).toString(),
    );
  };

  const getMonthsInRange = (start: Date, end: Date) => {
    const months = [];
    // 시작과 종료가 같은 년도일 때만
    if (start.getFullYear() === end.getFullYear()) {
      for (let month = start.getMonth(); month <= end.getMonth(); month += 1) {
        months.push((month + 1).toString()); // 실제 월은 1에서 시작합니다.
      }
    } else {
      // 전체 12개월
      for (let month = 1; month <= 12; month += 1) {
        months.push(month.toString());
      }
    }
    return months;
  };

  const getDaysInRange = (start: Date, end: Date) => {
    const days: string[] = [];
    // 시작과 종료 날짜가 같은 년, 같은 월일 때만
    if (
      start.getFullYear() === end.getFullYear() &&
      start.getMonth() === end.getMonth()
    ) {
      for (let day = start.getDate(); day <= end.getDate(); day += 1) {
        days.push(day.toString());
      }
    } else {
      // 해당 월의 마지막 일자를 계산합니다.
      const month = start.getMonth();
      const year = start.getFullYear();
      let lastDayOfMonth;

      if (month === 1) {
        // 2월의 경우
        lastDayOfMonth = isLeapYear(year) ? 29 : 28;
      } else {
        lastDayOfMonth = new Date(year, month + 1, 0).getDate();
      }

      for (let day = 1; day <= lastDayOfMonth; day += 1) {
        days.push(day.toString());
      }
    }
    return days;
  };

  const yearArray = getYearsInRange(startDateObj, latestDateObj);
  const monthArray = getMonthsInRange(startDateObj, latestDateObj);
  const dayArray = getDaysInRange(startDateObj, latestDateObj);

  const yearOptions: ItemType[] = yearArray.map((year, index) => {
    return { id: index, name: year };
  });
  const monthOptions: ItemType[] = monthArray.map((month, index) => {
    return { id: index, name: month };
  });
  const dayOptions: ItemType[] = dayArray.map((day, index) => {
    return { id: index, name: day };
  });

  const handleEndYear = (item: ItemType) => {
    setEndYear(item.name);
  };
  const handleEndMonth = (item: ItemType) => {
    setEndMonth(item.name);
  };
  const handleEndDay = (item: ItemType) => {
    setEndDay(item.name);
  };

  useEffect(() => {
    const newEndDate: string = `${endYear}-${endMonth}-${endDay}`;
    onChange(newEndDate);
  }, [endYear, endMonth, endDay, dispatch, onChange]);

  return (
    <EndDateSelectContainer>
      <DropDown
        items={yearOptions}
        width="20%"
        height="3rem"
        initialValue={parseString(endDate)[0]}
        onChange={handleEndYear}
        openDropdown={openDropEndYear}
        close={closeDropEndYear}
        toggle={toggleDropEndYear}
      />
      년
      <DropDown
        items={monthOptions}
        width="20%"
        height="3rem"
        initialValue={parseString(endDate)[1]}
        onChange={handleEndMonth}
        openDropdown={openDropEndMonth}
        close={closeDropEndMonth}
        toggle={toggleDropEndMonth}
      />
      월
      <DropDown
        items={dayOptions}
        width="20%"
        height="3rem"
        initialValue={parseString(endDate)[2]}
        onChange={handleEndDay}
        openDropdown={openDropEndDay}
        close={closeDropEndDay}
        toggle={toggleDropEndDay}
      />
      일
    </EndDateSelectContainer>
  );
}

export default EndDateSelect;
