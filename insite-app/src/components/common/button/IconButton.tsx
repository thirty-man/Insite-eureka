import styled, { DefaultTheme } from "styled-components";

interface IconButtonProps {
  children: React.ReactNode;
  width: string;
  height: string;
  color: DefaultTheme;
  onClick: () => void;
}

const IconButtonStyle = styled.button<IconButtonProps>`
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

function IconButton({
  onClick,
  color,
  children,
  width,
  height,
}: IconButtonProps) {
  return (
    <IconButtonStyle
      width={width}
      height={height}
      color={color}
      onClick={() => onClick()}
    >
      {children}
    </IconButtonStyle>
  );
}

export default IconButton;
