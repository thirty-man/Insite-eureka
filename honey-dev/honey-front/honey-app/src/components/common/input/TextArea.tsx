interface TextAreaProps {
  className: string;
}

function TextArea({ className }: TextAreaProps) {
  return <textarea className={className} />;
}

export default TextArea;
