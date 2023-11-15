import TimePicker from "react-time-picker";
import "react-time-picker/dist/TimePicker.css";
import "./CustomeTimePicker.css";

type Value = string | null;

interface TimePickerProps {
  value: Value;
  onChange: (value: Value) => void;
}

function CustomTimePicker({ value, onChange }: TimePickerProps) {
  return (
    <div>
      <TimePicker onChange={onChange} value={value} />
    </div>
  );
}

export default CustomTimePicker;
