import pots from "@assets/images/pots";

type PotPropsType = {
  potNum: string;
  onClick: () => void;
};

function Pot({ potNum, onClick }: PotPropsType) {
  const pot: string = `${pots[Number(potNum)]}`;

  const potAlt: string = "pot";

  return (
    <div className="flex w-[33%] justify-center items-end h-[114%] hover:scale-125">
      <button type="button" onClick={onClick}>
        <img src={pot} alt={potAlt} className="w-[300px]" />
      </button>
    </div>
  );
}

export default Pot;
