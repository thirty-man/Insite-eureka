import styled from "styled-components";

interface ImageProps {
  width: string;
  height: string;
  src: string;
  alt: string;
}

const ImageBoxStyle = styled.img<ImageProps>`
  width: ${(props) => props.width};
  height: ${(props) => props.height};
  src: ${(props) => props.src};
  alt: ${(props) => props.alt};
`;

function ImageBox({ width, height, src, alt }: ImageProps) {
  return <ImageBoxStyle width={width} height={height} src={src} alt={alt} />;
}

export default ImageBox;
