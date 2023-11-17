import potList from "@assets/images/pots";

type PotPropsType = {
  potNum: string;
  onClick: () => void;
};

function Pot({ potNum, onClick }: PotPropsType) {
  const potAlt: string = "pot";

  return (
    <div className="flex w-full justify-center items-center h-full hover:scale-125">
      <button type="button" onClick={onClick}>
        <img src={potList[Number(potNum)]} alt={potAlt} className="w-[300px]" />
      </button>
    </div>
  );
}

export default Pot;
