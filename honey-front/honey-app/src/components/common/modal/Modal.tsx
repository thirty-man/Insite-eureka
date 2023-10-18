import { PropsWithChildren, useEffect, useState } from "react";

interface ModalType {
  // width: string;
  // height: string;
  openModal: boolean;
  position: "absolute" | "fixed";
}

function Modal({
  openModal,
  children,
  position,
}: PropsWithChildren<ModalType>) {
  const [visible, setVisible] = useState<boolean>(false);
  const modalClasses = `
    overflow-y-auto w-96 h-96 bottom-1/2 left-1/2 -translate-x-48 translate-y-24 ${position} border-0 rounded-lg bg-white shadow-lg z-[99]
  `;
  useEffect(() => {
    if (openModal) {
      setTimeout(() => {
        setVisible(true);
      }, 115);
    } else {
      setVisible(false);
    }
  }, [openModal]);
  return (
    visible && (
      <>
        <div className={modalClasses}>{children}</div>
        <div className="inset-0 fixed bg-black opacity-75" />
      </>
    )
  );
}
// ----------------------------------------------------------------------------------------------------

/* Export */
export default Modal;
