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
  font-size: 1.3rem;
  color: white;
  background-color: transparent;
  font-weight: bold;
  cursor: default;

  &:hover {
    position: relative;
    z-index: 1;

    &:before {
      content: "안녕하세요";
      position: absolute;
      font-size: 0.7rem;
      top: 100%;
      left: 50%;
      transform: translateX(-50%);
      border-width: 8px 8px 0;
      border-style: solid;
      background-color: black;
      border-color: black;
    }
  }
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
