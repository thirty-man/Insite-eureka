import styled from "styled-components";

interface ButtonBoxProps {
  width: string;
  height: string;
  color:string;
  children: React.ReactNode;
}

const TextBoxStyle = styled.div<ButtonBoxProps>`
  width: ${(props) => props.width};
  height: ${(props) => props.height};
  display: flex;
  justify-content: center;
  align-items: center;
  color: black;
  background-color: ${(props) => props.color};
  border-radius: 15px;
  box-shadow:
    0 14px 28px rgba(0, 0, 0, 0.25),
    0 10px 10px rgba(0, 0, 0, 0.22);
`;

/** 검정색 테스트 박스: 너비, 높이 */
function ButtonBox({ children, width, height,color }: ButtonBoxProps) {
  return (
    <TextBoxStyle width={width} height={height} color={color}>
        {children}
    </TextBoxStyle>
  );
}

export default ButtonBox;
