import { PropsWithChildren, useEffect, useState } from "react";

interface ModalType {
  openModal: boolean;
  overlay: boolean;
  overz: string;
  className: string;
}

function Modal({
  openModal,
  children,
  overlay,
  overz,
  className,
}: PropsWithChildren<ModalType>) {
  const [visible, setVisible] = useState<boolean>(false);
  useEffect(() => {
    if (openModal) {
      setVisible(true);
    } else {
      setVisible(false);
    }
  }, [openModal]);

  const overlayClass = `inset-0 fixed bg-black opacity-[90%] ${
    overz === "" ? "z-[100]" : overz
  }`;

  return (
    visible && (
      <>
        <div className={className}>{children}</div>
        {overlay ? <div className={overlayClass} /> : null}
      </>
    )
  );
}
// ----------------------------------------------------------------------------------------------------

/* Export */
export default Modal;
