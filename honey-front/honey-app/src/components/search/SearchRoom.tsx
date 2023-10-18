// import TextButton from "@components/common/button/TextButton";
import roomAtoms from "@recoil/roomAtoms";
import { ImageButton } from "@components/common/button";
import { TextInput } from "@components/common/input";
import { useRecoilState } from "recoil";

function SearchRoom() {
  const imgAddress: string = "src/assets/images/search.png";
  const [inputSearch, setInputSearch] = useRecoilState<string>(roomAtoms);

  const searchRoom = () => {
    setInputSearch("");
  };

  return (
    <div className="flex h-1/6 items-center justify-around">
      <TextInput
        value={inputSearch}
        holder="여기서 검색하세요"
        className="h-1/3 w-3/4 m-5 p-1"
        onChange={(e) => setInputSearch(e.target.value)}
        onKeyDown={(e: React.KeyboardEvent<HTMLInputElement>) =>
          e.key === "Enter" && searchRoom()
        }
      />
      <ImageButton
        image={imgAddress}
        alt="검색"
        className="flex items-center justify-center w-1/6 m-3 p-1"
        onClick={searchRoom}
      />
    </div>
  );
}
export default SearchRoom;
