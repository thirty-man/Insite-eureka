import { RoomType } from "@customtype/dataTypes";
import { roomListState } from "@recoil/atom";
import { selector } from "recoil";

const selectedRoomSelector = (id: number) =>
  selector({
    key: "groupedPotList",
    get: ({ get }) => {
      const roomList: RoomType[] = get(roomListState);
      const selectedRoom = roomList.find((room) => room.id === Number(id));

      return selectedRoom;
    },
  });

export default selectedRoomSelector;
