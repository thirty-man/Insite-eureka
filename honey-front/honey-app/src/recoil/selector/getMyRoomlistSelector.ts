import { RoomType } from "@customtype/dataTypes";
import { myRoomListState } from "@recoil/atom";
import { selector } from "recoil";

const getMyRoomlistSelector = selector({
  key: "getMyRoomList",
  get: ({ get }) => {
    const myRoomlist: RoomType[] = get(myRoomListState);

    return myRoomlist;
  },
});

export default getMyRoomlistSelector;
