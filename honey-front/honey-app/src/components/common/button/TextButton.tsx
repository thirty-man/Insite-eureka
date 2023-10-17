interface TextButtonProps {
  text: string;
  fontColor: string;
  className: string;
  onClick: () => void;
}

function TextButton({ text, fontColor, className, onClick }: TextButtonProps) {
  const basicType: string = `bg-cg-${fontColor || 0} btn ${className}`;

  return (
    <button type="button" onClick={onClick} className={basicType}>
      {text}
    </button>
  );
}

export default TextButton;
