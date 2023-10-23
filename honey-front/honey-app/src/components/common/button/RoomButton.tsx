import { RoomType } from "@customtype/dataTypes";

interface RoomButtonProps {
  room: RoomType;
  onClick: () => void;
  className: string;
}

function RoomButton({ room, onClick, className }: RoomButtonProps) {
  const basicType: string = `${className} flex hover:bg-cg-1 hover:scale-110`;
  const lockImg: string = "./src/assets/images/lock.png";

  return (
    <button type="button" onClick={onClick} className={basicType}>
      <p className="w-[12%] text-left">
        {room.password !== null ? null : (
          <img className="w-[90%]" src={lockImg} alt="잠김" />
        )}
      </p>
      <p className="w-[20%]">{room.roomId}</p>
      <p className="w-[46%]">
        {room.roomName.length <= 10
          ? room.roomName
          : `${room.roomName.slice(0, 10)}...`}
      </p>
      <p className="w-[26%]">{room.owner}</p>
    </button>
  );
}

export default RoomButton;
