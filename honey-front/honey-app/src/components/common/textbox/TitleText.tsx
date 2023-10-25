interface TitleTextProps {
  text: string;
  className: string;
}

function TitleText({ text, className }: TitleTextProps) {
  const basicType: string = `${className} sm:text-[50px] text-[20px]`;

  return <p className={basicType}> {text} </p>;
}

export default TitleText;
