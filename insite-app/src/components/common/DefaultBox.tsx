import styled from "styled-components";
import { ReactNode } from "react";

interface DefaultBoxProps {
  children: ReactNode;
  width: string;
  height: string;
}

const DefaultBoxStyle = styled.div<DefaultBoxProps>`
  margin: 0;
  padding: 0;
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: center;
  width: ${(props) => props.width};
  height: ${(props) => props.height};
  background-color: ${(props) => props.theme.colors.b1};
  border-radius: 15px;
  box-shadow:
    0 14px 28px rgba(0, 0, 0, 0.25),
    0 10px 10px rgba(0, 0, 0, 0.22);
`;

/** 너비, 높이 */
function DefaultBox({ children, width, height }: DefaultBoxProps) {
  return (
    <DefaultBoxStyle width={width} height={height}>
      {children}
    </DefaultBoxStyle>
  );
}

export default DefaultBox;
