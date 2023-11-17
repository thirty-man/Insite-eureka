import styled, { DefaultTheme } from "styled-components";

interface TextButtonProps {
  children: React.ReactNode;
  width: string;
  height: string;
  color: DefaultTheme;
  onClick: () => void;
}

const TextButtonStyle = styled.button<TextButtonProps>`
  width: ${(props) => props.width};
  height: ${(props) => props.height};
  background-color: ${(props) => props.color};
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 15px;
  cursor: pointer;
  border: none;
  padding: 0;
  overflow: hidden;
  box-shadow:
    0 14px 28px rgba(0, 0, 0, 0.25),
    0 10px 10px rgba(0, 0, 0, 0.22);
`;

function TextButton({
  onClick,
  color,
  children,
  width,
  height,
}: TextButtonProps) {
  return (
    <TextButtonStyle
      width={width}
      height={height}
      color={color}
      onClick={() => onClick()}
    >
      {children}
    </TextButtonStyle>
  );
}

export default TextButton;
