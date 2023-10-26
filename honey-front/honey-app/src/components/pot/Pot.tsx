import {
  pot0,
  pot1,
  pot2,
  pot3,
  pot4,
  pot5,
  pot6,
  pot7,
  pot8,
  pot9,
} from "@assets/images/pots";

type PotPropsType = {
  potNum: string;
  onClick: () => void;
};

type PotImages = {
  [key: string]: string;
};

function Pot({ potNum, onClick }: PotPropsType) {
  const potImages: PotImages = {
    "0": pot0,
    "1": pot1,
    "2": pot2,
    "3": pot3,
    "4": pot4,
    "5": pot5,
    "6": pot6,
    "7": pot7,
    "8": pot8,
    "9": pot9,
  };

  const potAlt: string = "pot";

  return (
    <div className="flex w-full justify-center items-center h-full hover:scale-125">
      <button type="button" onClick={onClick}>
        <img src={potImages[potNum]} alt={potAlt} className="w-[300px]" />
      </button>
    </div>
  );
}

export default Pot;
