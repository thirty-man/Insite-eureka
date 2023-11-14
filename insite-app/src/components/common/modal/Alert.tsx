import styled from "styled-components";
import Modal from "./Modal";

interface AlertProps {
  width: string;
  height: string;
  text: string;
  closeAlert: () => void;
}

const TextDiv = styled.div`
  width: 90%;
  height: 70%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 15px;
`;

const CloseButton = styled.div`
  width: 30%;
  height: 10%;
`;

const Overlay = styled.div`
  position: fixed;
  inset: 0px;
  width: 100vw;
  height: 100vh;
  background-color: black;
  opacity: 75%;
  z-index: 9999;
`;

function Alert({ width, height, text, closeAlert }: AlertProps) {
  return (
    <>
      <Modal
        width={width}
        height={height}
        $posX="0px"
        $posY="0px"
        $position="fixed"
        close={closeAlert}
      >
        <TextDiv>{text}</TextDiv>
        <CloseButton>닫기</CloseButton>
      </Modal>
      <Overlay />
    </>
  );
}
export default Alert;
