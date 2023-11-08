import styled from "styled-components";
import { IconCalendar } from "@assets/icons";
import { DefaultBox } from "@components/common";

interface CalendarButtonSizeProps {
  width: string;
  height: string;
}

interface CalendarButtonProps {
  startDate: string;
  endDate: string;
}

const DateText = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  margin-left: 2px;
  margin-right: 2px;
`;

const DateText2 = styled.div`
  width: 2%;
  height: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
`;
const DateImg = styled.img`
  width: 30%;
  height: 50%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  margin-right: 5px;
  filter: invert(100%);
`;

const CalendarContainer = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  padding: 5px;
`;

function CalendarButton({
  startDate,
  endDate,
  width,
  height,
}: CalendarButtonProps & CalendarButtonSizeProps) {
  return (
    <DefaultBox width={width} height={height}>
      <CalendarContainer>
        <DateText>{startDate}</DateText>
        <DateText2>~</DateText2>
        <DateText>{endDate}</DateText>
        <DateImg src={IconCalendar} alt="calendar icon" />
      </CalendarContainer>
    </DefaultBox>
  );
}
export default CalendarButton;
