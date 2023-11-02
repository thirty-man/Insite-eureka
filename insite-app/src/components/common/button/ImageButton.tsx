import styled from "styled-components";

interface ImageButtonProps {
  width: string;
  height: string;
  onClick: () => void;
  borderRadius: string;
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
  border-radius: ${(props) => props.borderRadius};
  border: none;
  padding: 0;
  overflow: hidden;
`;

const ImageStyle = styled.img<ImageProps2>`
  width: 100%;
  height: 100%;
  border-radius: inherit;
`;

function ImageButton({
  width,
  height,
  onClick,
  borderRadius,
  src,
  alt,
}: ImageProps) {
  return (
    <ImageButtonStyle
      width={width}
      height={height}
      borderRadius={borderRadius}
      onClick={() => onClick()}
    >
      <ImageStyle src={src} alt={alt} />
    </ImageButtonStyle>
  );
}

export default ImageButton;
