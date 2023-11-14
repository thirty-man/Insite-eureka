import TimePicker from "react-time-picker";

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
