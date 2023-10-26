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

function Pot({ potNum, onClick }: PotPropsType) {
  const pot: string[] = [
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
  ];

  const potAlt: string = "pot";

  return (
    <div className="flex w-[33%] justify-center items-end h-[114%] hover:scale-125">
      <button type="button" onClick={onClick}>
        <img src={pot[Number(potNum)]} alt={potAlt} className="w-[300px]" />
      </button>
    </div>
  );
}

export default Pot;
