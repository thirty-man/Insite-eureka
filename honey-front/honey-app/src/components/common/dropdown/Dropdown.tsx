import { RoomType } from "@customtype/dataTypes";

interface DropdwonProps {
  items: RoomType[];
  className: string;
  onClick: (room: RoomType) => void;
}

function Dropdown({ className, items, onClick }: DropdwonProps) {
  return (
    <div className="absolute flex flex-col max-w-[300px] mt-[10px] sm:w-[100%] justify-start items-center bg-cg-1 rounded-xl sm:h-[250px] overflow-y-scroll">
      {items.map((item, index) => (
        <div key={item.id}>
          <button
            type="button"
            className={className}
            onClick={() => onClick(item)}
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
