import { RoomType } from "@customtype/dataTypes";
import { roomListState } from "@recoil/atom";
import { selector } from "recoil";

const selectedRoomSelector = (roomId: number) =>
  selector({
    key: "groupedPotList",
    get: ({ get }) => {
      const roomList: RoomType[] = get(roomListState);
      const selectedRoom = roomList.find(
        (room) => room.roomId === Number(roomId),
      );

      return selectedRoom;
    },
  });

export default selectedRoomSelector;
