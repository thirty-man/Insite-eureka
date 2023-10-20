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
 fixed bottom-1/2 left-1/2 z-[99] ${className}
`;
  useEffect(() => {
    if (openModal) {
      setVisible(true);
    } else {
      setVisible(false);
    }
  }, [openModal]);
  return (
    visible && (
      <>
        <div className={modalClasses}>{children}</div>
        {overlay ? <div className="inset-0 fixed bg-black opacity-75" /> : null}
      </>
    )
  );
}
// ----------------------------------------------------------------------------------------------------

/* Export */
export default Modal;
