import { PropsWithChildren, useEffect, useRef } from "react";
import styled from "styled-components";

interface ModalContentType {
  width: string;
  height: string;
  $posX: string;
  $posY: string;
  $position: "absolute" | "fixed" | "relative";
}

interface ModalType extends ModalContentType {
  close: () => void;
}

// ----------------------------------------------------------------------------------------------------

/* Style */
const ModalContent = styled.div<ModalContentType>`
  width: ${(props) => props.width};
  height: ${(props) => props.height};
  bottom: 50%;
  left: 50%;
  transform: translate(-50%, 50%)
    ${(props) => (props.$posX ? `translateX(${props.$posX})` : "")}${(props) => (props.$posY ? `translateY(${props.$posY})` : "")};
  display: flex;
  flex-direction: column;
  align-items: center;
  position: ${(props) => props.$position};
  border: none;
  background-color: ${(props) => props.theme.colors.b3};
  color: white;
  border-radius: 0.6rem;
  border: 3px solid #6646ef;
  box-shadow: 0 0 30px rgba(30, 30, 30, 0.185);
  box-sizing: border-box;
  z-index: 10000;
`;

// ----------------------------------------------------------------------------------------------------

function Modal({
  children,
  width,
  height,
  $posX,
  $posY,
  $position,
  close,
}: PropsWithChildren<ModalType>) {
  const modalRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    const handleModal = (event: MouseEvent) => {
      if (
        modalRef.current &&
        !modalRef.current.contains(event.target as Node)
      ) {
        close();
      }
    };
    document.addEventListener("click", handleModal);
    return () => {
      document.removeEventListener("click", handleModal);
    };
  }, [close]);

  return (
    <ModalContent
      width={width}
      height={height}
      $posX={$posX}
      $posY={$posY}
      $position={$position}
      ref={modalRef}
    >
      {children}
    </ModalContent>
  );
}
// ----------------------------------------------------------------------------------------------------

/* Export */
export default Modal;
