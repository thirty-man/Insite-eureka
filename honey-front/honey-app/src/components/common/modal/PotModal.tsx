import { PropsWithChildren, useEffect, useState } from "react";

interface ModalType {
  openModal: boolean;
  overlay: boolean;
  className: string;
}

function PotModal({
  openModal,
  children,
  overlay,
  className,
}: PropsWithChildren<ModalType>) {
  const modalClasses = `
    fixed bottom-1/2 left-1/2 z-[99] ${className}
   `;

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
        <div className={modalClasses}>{children}</div>
        {overlay ? <div className="inset-0 fixed bg-black opacity-75" /> : null}
      </>
    )
  );
}
// ----------------------------------------------------------------------------------------------------

/* Export */
export default PotModal;
