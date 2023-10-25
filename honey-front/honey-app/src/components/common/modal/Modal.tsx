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
        <div className={className}>{children}</div>
        {overlay ? (
          <div className="inset-0 fixed bg-black opacity-[90%] z-[100]" />
        ) : null}
      </>
    )
  );
}
// ----------------------------------------------------------------------------------------------------

/* Export */
export default Modal;
