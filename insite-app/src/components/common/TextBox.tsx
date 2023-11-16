import styled from "styled-components";

interface TextBoxProps {
  children: React.ReactNode;
  width: string;
  height: string;
}

const TextBoxStyle = styled.div<TextBoxProps>`
  width: ${(props) => props.width};
  height: ${(props) => props.height};
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  background-color: ${(props) => props.theme.colors.b3};
  border-radius: 15px;

  &:hover {
    color: white;
    text-decoration: none;
    transform: scale(1.02);
    transition: transform 0.1s ease;
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.9);
  }

  &:active {
    transform: scale(0.99);
    transition: transform 0.1s;
  }
`;

/** 검정색 테스트 박스: 너비, 높이 */
function TextBox({ children, width, height }: TextBoxProps) {
  return (
    <TextBoxStyle width={width} height={height}>
      {children}
    </TextBoxStyle>
  );
}

export default TextBox;
