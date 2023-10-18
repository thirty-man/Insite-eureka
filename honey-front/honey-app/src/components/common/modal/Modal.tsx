import { PropsWithChildren, useEffect, useState } from "react";

interface ModalType {
  openModal: boolean;
  overlay: boolean;
  className: string;
}

function Modal({
  openModal,
  children,
  overlay,
  className,
}: PropsWithChildren<ModalType>) {
  const [visible, setVisible] = useState<boolean>(false);
  const modalClasses = `
    fixed overflow-y-auto bottom-1/2 left-1/2 -translate-x-48 translate-y-24 border-0 rounded-lg bg-white shadow-lg z-[99] ${className}
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
        {overlay && <div className="inset-0 fixed bg-black opacity-75" />}
      </>
    )
  );
}
// ----------------------------------------------------------------------------------------------------

/* Export */
export default Modal;
