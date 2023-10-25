import { RoomType } from "@customtype/dataTypes";

interface DropdwonProps {
  items: RoomType[];
  className: string;
  onClick: (roomId: number) => void;
}

function Dropdown({ className, items, onClick }: DropdwonProps) {
  return (
    <div className="absolute flex flex-col max-w-[300px] sm:w-[25%] justify-start items-center bg-cg-1 rounded-xl sm:h-[250px] overflow-y-scroll">
      {items.map((item, index) => (
        <div key={item.id}>
          <button
            type="button"
            className={className}
            onClick={() => onClick(item.id)}
          >
            <p className="sm:text-2xl p-2">{item.title}</p>
          </button>
          {index !== items.length - 1 && (
            <hr className="w-[90%] border border-black" />
          )}
        </div>
      ))}
    </div>
  );
}

export default Dropdown;
