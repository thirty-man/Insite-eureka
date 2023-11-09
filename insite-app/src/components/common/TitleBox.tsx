import styled from "styled-components";

interface TitleBoxProps {
  children: React.ReactNode;
  width: string;
  height: string;
}

const TitleBoxStyle = styled.div<TitleBoxProps>`
  width: ${(props) => props.width};
  height: ${(props) => props.height};
  padding: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.7rem;
  color: white;
  background-color: transparent;
`;

/** 너비, 높이, 폰트 사이즈 */
function TitleBox({ children, width, height }: TitleBoxProps) {
  return (
    <TitleBoxStyle width={width} height={height}>
      {children}
    </TitleBoxStyle>
  );
}

export default TitleBox;
