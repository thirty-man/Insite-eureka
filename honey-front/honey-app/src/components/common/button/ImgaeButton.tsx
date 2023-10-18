interface ImageButtonProps {
  image: string;
  alt: string;
  className: string;
  onClick: () => void;
}

function ImageButton({ image, alt, className, onClick }: ImageButtonProps) {
  const basicType: string = `${className}`;

  return (
    <button type="button" onClick={onClick} className={basicType}>
      <img src={image} alt={alt} />
    </button>
  );
}

export default ImageButton;
