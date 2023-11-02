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
  box-shadow:
    0 14px 28px rgba(0, 0, 0, 0.25),
    0 10px 10px rgba(0, 0, 0, 0.22);
`;

function TextBox({ children, width, height }: TextBoxProps) {
  return (
    <TextBoxStyle width={width} height={height}>
      {children}
    </TextBoxStyle>
  );
}

export default TextBox;
