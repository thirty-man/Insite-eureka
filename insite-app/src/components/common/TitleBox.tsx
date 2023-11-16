import { ReactNode } from "react";
import styled from "styled-components";
import { IconInfo } from "@assets/icons";

interface TitleBoxProps {
  children: ReactNode;
  text: string;
}

const TitleBoxStyle = styled.div`
  width: fit-content; // 컨텐츠에 맞게 너비 조정
  padding: 10px;
  display: flex;
  align-items: center;
  justify-content: center; // 가운데 정렬
  font-size: 1.3rem;
  color: white;
  background-color: transparent;
  font-weight: bold;
  cursor: default;
  position: relative;
  margin: 0 auto; // 상하좌우 마진 자동으로 조정하여 중앙에 위치
`;

const InfoIconWrapper = styled.div`
  margin-left: 10px;
  position: relative;
  display: inline-block; // inline-block으로 변경
  cursor: pointer;

  &:hover .Tooltip {
    visibility: visible;
    opacity: 1;
  }
`;

const Tooltip = styled.div`
  white-space: pre-line;
  visibility: hidden;
  width: 300px;
  background-color: #333744;
  color: #fff;
  text-align: start;
  font-size: 15px;
  font-weight: 300;
  border-radius: 6px;
  padding: 15px;
  position: absolute;
  z-index: 10001;
  transform: translateX(120px);
  top: 100%; // 아이콘 바로 아래로 위치 조정
  right: 0%;
  margin-right: 0;
  transition: opacity 0.3s;

  &::before {
    content: "";
    position: absolute;
    background-color: #333744;
    top: -5px; // 화살표를 툴팁 상단으로 이동
    left: 60%; // 화살표를 왼쪽으로 조금 이동
    border-width: 5px;
    border-style: solid;
    border-color: transparent transparent black transparent;
  }
`;

const InfoIcon = styled.img`
  padding-top: 1px;
  width: 20px;
  height: 20px;
`;

function TitleBox({ children, text }: TitleBoxProps) {
  return (
    <TitleBoxStyle>
      {children}
      <InfoIconWrapper>
        <InfoIcon src={IconInfo} alt="Info" />
        <Tooltip className="Tooltip">{text}</Tooltip>
      </InfoIconWrapper>
    </TitleBoxStyle>
  );
}

export default TitleBox;
