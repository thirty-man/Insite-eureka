import styled from "styled-components";

interface ImageButtonProps {
  width: string;
  height: string;
  onClick: () => void;
}

interface ImageProps extends ImageButtonProps {
  src: string;
  alt: string;
}

interface ImageProps2 {
  src: string;
  alt: string;
}

const ImageButtonStyle = styled.button<ImageButtonProps>`
  width: ${(props) => props.width};
  height: ${(props) => props.height};
  cursor: pointer;
  background: transparent;
  border: none;
  padding: 0;
`;

const ImageStyle = styled.img<ImageProps2>`
  width: 100%;
  height: 100%;
`;

function ImageButton({ width, height, onClick, src, alt }: ImageProps) {
  return (
    <ImageButtonStyle width={width} height={height} onClick={() => onClick()}>
      <ImageStyle src={src} alt={alt} />
    </ImageButtonStyle>
  );
}

export default ImageButton;
