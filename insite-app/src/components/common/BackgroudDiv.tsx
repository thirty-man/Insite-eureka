import styled from "styled-components";
import { ReactNode } from "react";

interface BackgroudDivProps {
  children: ReactNode;
}

const Backgroud = styled.div`
  margin: 0;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100vw;
  height: 100vh;
  background-color: ${(props) => props.theme.colors.b2};
`;

function BackgroudDiv({ children }: BackgroudDivProps) {
  return <Backgroud>{children}</Backgroud>;
}

export default BackgroudDiv;
