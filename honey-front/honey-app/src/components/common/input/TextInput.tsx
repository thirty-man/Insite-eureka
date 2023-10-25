interface TextInputProps {
  holder: string;
  value: string | number;
  className: string;
  readonly: boolean;
  onChange(event: React.ChangeEvent<HTMLInputElement>): void;
  onKeyDown?(event: React.KeyboardEvent<HTMLInputElement>): void;
}

function TextInput({
  value,
  holder,
  className,
  readonly,
  onChange,
  onKeyDown,
}: TextInputProps) {
  const basicType = `${className} bg-cg-3 rounded-2xl`;

  return (
    <input
      placeholder={holder}
      className={basicType}
      value={value}
      readOnly={readonly}
      onChange={onChange}
      onKeyDown={onKeyDown}
    />
  );
}

// 엔터를 치지 않는 input은 무시할 수 있도록
TextInput.defaultProps = {
  onKeyDown: undefined, // defaultProps로 기본값을 지정
};

export default TextInput;
