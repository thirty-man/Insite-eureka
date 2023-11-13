interface DropdownType<T> {
  isVisible: boolean;
  data: T[];
}

function ButtonDropdown<T>({ isVisible }: DropdownType<T>) {
  if (!isVisible) {
    return null; // 드롭다운을 숨길 때
  }

  return <div className="dropdown">{/* 드롭다운 내용 */}</div>;
}

export default ButtonDropdown;
