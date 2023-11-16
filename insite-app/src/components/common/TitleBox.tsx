import { IconInfo } from "@assets/icons";
import styled from "styled-components";

interface TitleBoxProps {
  children: React.ReactNode;
}

const TitleBoxStyle = styled.div<TitleBoxProps>`
  width: fit-content;
  max-width: 300px;
  height: auto;
  padding: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.3rem;
  color: white;
  background-color: transparent;
  font-weight: bold;
  cursor: default;
  position: relative;
  z-index: 100;

  &:hover {
    position: relative;
    z-index: 1;

    &:before {
      content: "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요";
      position: absolute;
      font-size: 0.7rem;
      top: 100%;
      left: 50%;
      transform: translateX(-40%);
      border-width: 8px 8px 0;
      border-style: solid;
      background-color: black;
      border-color: black;
    }
  }
`;
const InfoIcon = styled.img`
  width: 10%;
  height: 10%;
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
`;

/** 너비, 높이, 폰트 사이즈 */
function TitleBox({ children }: TitleBoxProps) {
  return (
    <TitleBoxStyle>
      {children} <InfoIcon src={IconInfo} alt="Info" />
    </TitleBoxStyle>
  );
}

export default TitleBox;
