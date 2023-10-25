/* eslint-disable @typescript-eslint/no-unused-vars */
import { ImageButton } from "@components/common/button";
import { TextInput } from "@components/common/input";
// import roomListState from "@recoil/atom/roomListState";
// import { RoomType } from "@customtype/dataTypes";
import { useState } from "react";
// import { useRecoilState } from "recoil";
import { searchImg } from "@assets/images";

function SearchRoom() {
  const imgAddress: string = searchImg;
  const [inputSearch, setInputSearch] = useState<string>("");
  // const [, setRoomList] = useRecoilState<RoomType[]>(roomListState);

  const searchRoom = () => {
    // 이 부분에서 방 목록을 호출하는 axios통신 코드 작성
    // dummy test

    setInputSearch("");
  };

  return (
    <div className="flex w-full h-[70px] items-center justify-center relative z-[50] top-6 ">
      <div className="flex w-[70%] justify-end">
        <TextInput
          value={inputSearch}
          holder="방을 검색하세요"
          readonly={false}
          className="h-12 w-full m-5 p-1"
          onChange={(e) => setInputSearch(e.target.value)}
          onKeyDown={(e: React.KeyboardEvent<HTMLInputElement>) =>
            e.key === "Enter" && searchRoom()
          }
        />
      </div>
      <ImageButton
        image={imgAddress}
        alt="검색"
        className="flex items-center justify-start w-[10%] mr-5"
        onClick={searchRoom}
      />
    </div>
  );
}
export default SearchRoom;
