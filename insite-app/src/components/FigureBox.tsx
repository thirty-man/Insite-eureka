import styled, { DefaultTheme } from "styled-components";
import { ImageBox } from "./common";
import { IconButton } from "./common/button";

interface BaseBoxProps {
  width: string;
  height: string;
}

interface FigureBoxProps extends BaseBoxProps {
  src: string;
  alt: string;
  color: DefaultTheme;
  onClick: () => void;
  titleText: string;
  contentText: string;
}

const FigureBoxStyle = styled.div<BaseBoxProps>`
  margin: 0;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: ${(props) => props.width};
  height: ${(props) => props.height};
  background-color: transparent;
`;

const InnerBoxStyle = styled.div`
  display: flex;
  height: 65%;
  width: 40%;
  flex-direction: column;
  justify-content: center;
  align-items: start;
  margin-left: 15px;
`;

const InnerTitleStyle = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  height: 50%;
  color: white;
  font-size: 20px;
`;

const InnerContentStyle = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  height: 50%;
  color: white;
  font-size: 20px;
  font-weight: bold;
`;

function FigureBox({
  width,
  height,
  src,
  alt,
  color,
  onClick,
  titleText,
  contentText,
}: FigureBoxProps) {
  return (
    <FigureBoxStyle width={width} height={height}>
      <IconButton
        width="35%"
        height="70%"
        color={color}
        onClick={() => onClick()}
      >
        <ImageBox width="70%" height="65%" src={src} alt={alt} />
      </IconButton>
      <InnerBoxStyle>
        <InnerTitleStyle>{titleText}</InnerTitleStyle>
        <InnerContentStyle>{contentText}</InnerContentStyle>
      </InnerBoxStyle>
    </FigureBoxStyle>
  );
}

export default FigureBox;
