import styled from "styled-components";

interface TitleBoxProps {
  children: React.ReactNode;
  width: string;
  height: string;
  fontSize: string;
}

const TitleBoxStyle = styled.div<TitleBoxProps>`
  width: ${(props) => props.width};
  height: ${(props) => props.height};
  padding: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: ${(props) => props.fontSize};
  color: white;
  background-color: transparent;
`;

/** 너비, 높이, 폰트 사이즈 */
function TitleBox({ children, width, height, fontSize }: TitleBoxProps) {
  return (
    <TitleBoxStyle width={width} height={height} fontSize={fontSize}>
      {children}
    </TitleBoxStyle>
  );
}

export default TitleBox;
