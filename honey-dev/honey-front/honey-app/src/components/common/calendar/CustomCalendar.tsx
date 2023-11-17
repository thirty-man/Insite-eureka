import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import "./CustomCalenderStyle.css";
import moment from "moment";

type ValuePiece = Date | null;

type Value = ValuePiece | [ValuePiece, ValuePiece];

interface CalendarProps {
  value: Date | null;
  onChange: (value: Value, event: React.MouseEvent<HTMLButtonElement>) => void;
}

function CustomCalendar({ value, onChange }: CalendarProps) {
  return (
    <div>
      <Calendar
        onChange={onChange}
        value={value}
        formatDay={(_locale, date) => moment(date).format("DD")}
      />
    </div>
  );
}

export default CustomCalendar;
