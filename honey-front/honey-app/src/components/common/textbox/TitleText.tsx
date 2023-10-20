interface TitleTextProps {
  text: string;
  className: string;
}

function TitleText({ text, className }: TitleTextProps) {
  const basicType: string = `${className}`;

  return <h2 className={basicType}> {text} </h2>;
}

export default TitleText;
