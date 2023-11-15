import { Modal } from "@components/common/modal";
import { newbear } from "@assets/images";

interface AlertProps {
  text: string;
  openModal: boolean;
  closeButton: string;
  overz: string;
  closeAlert: () => void;
}

function Alert({
  text,
  openModal,
  closeButton,
  closeAlert,
  overz,
}: AlertProps) {
  return (
    <Modal
      className="fixed w-[340px] h-[250px] bottom-[50%] left-[50%] -translate-x-[170px] translate-y-[120px] z-[220] rounded-[36px] shadow-lg flex items-center justify-center px-[15px] py-[15px] bg-cg-6"
      overlay
      overz={overz}
      openModal={openModal}
    >
      <div className="w-[100%] h-[100%] overflow-y-auto z-[20] rounded-[30px] bg-cg-7 text-white text-[20px] px-[15px] py-[15px]">
        <div className="w-full h-full flex items-center justify-center text-[22px]">
          <span>{text}</span>
        </div>
      </div>
      <Modal
        className="fixed w-[150px] h-[120px] bottom-1/2 left-1/2 -translate-x-[180px] translate-y-[190px] z-[220] bg-transparent flex items-center justify-center"
        overlay={false}
        overz=""
        openModal
      >
        <img src={newbear} className="w-[200px] h-[90px]" alt="곰 모달" />
      </Modal>
      <Modal
        className="fixed w-[100px] h-[35px] bottom-1/2 left-1/2 -translate-x-[50px] translate-y-[170px] z-[220] rounded-[60px] bg-cg-1 flex items-center justify-center"
        overlay={false}
        overz=""
        openModal
      >
        <button
          type="button"
          className="w-[100%] h-[100%] text-[24px]"
          onClick={closeAlert}
        >
          {closeButton}
        </button>
      </Modal>
    </Modal>
  );
}

export default Alert;
