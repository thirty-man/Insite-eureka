import styled from "styled-components";
import { ReactNode } from "react";

interface BackgroundDivProps {
  children: ReactNode;
}

const Background = styled.div`
  margin: 0;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100vw;
  height: 100vh;
  background-color: ${(props) => props.theme.colors.b2};
`;

function BackgroundDiv({ children }: BackgroundDivProps) {
  return <Background>{children}</Background>;
}

export default BackgroundDiv;
