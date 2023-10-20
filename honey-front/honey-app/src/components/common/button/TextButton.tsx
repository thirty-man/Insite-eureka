interface TextButtonProps {
  text: string;
  color: string;
  className: string;
  onClick: () => void;
}

function TextButton({ text, color, className, onClick }: TextButtonProps) {
  const basicType: string = `bg-cg-${color || 0} ${className} hover:bg-cg-5`;

  return (
    <button type="button" onClick={onClick} className={basicType}>
      {text}
    </button>
  );
}

export default TextButton;
