import { RoomButton } from "@components/common/button";
import roomListState from "@recoil/atom/roomListState";
import { RoomType } from "@customtype/dataTypes";
import { useRecoilState, useRecoilValue } from "recoil";
import { selectedRoomState } from "@recoil/atom";
import useRouter from "@hooks/useRouter";

function ShowRoom() {
  const roomList = useRecoilValue<RoomType[]>(roomListState);
  const [, setSelectedRoom] = useRecoilState<RoomType>(selectedRoomState);
  const { routeTo } = useRouter();

  function gotoRoom(room: RoomType) {
    // 룸으로 이동
    setSelectedRoom(room);
    routeTo(`/room/participate/${room.id}`);
  }

  return (
    <div className="flex flex-col bg-board h-[640px] w-full items-center justify-center bg-cover bg-size m-0 p-0">
      <div className="flex w-full h-8 justify-center">
        <div className="flex w-[65%]">
          <p className="text-white w-[12%]">공개</p>
          <p className="text-white w-[20%]">방 번호</p>
          <p className="text-white w-[40%]">방 이름</p>
          <p className="text-white w-[26%]">주최자</p>
        </div>
      </div>
      <div className="flex flex-col h-[400px] w-4/5 items-center justify-start">
        {roomList.map((room) => (
          <div className="flex w-full h-20 justify-center" key={room.id}>
            <RoomButton
              className="bg-cg-5 w-5/6 h-15 roomBtn rounded-2xl m-0"
              key={room.id}
              room={room}
              onClick={() => gotoRoom(room)}
            />
          </div>
        ))}
      </div>
    </div>
  );
}

export default ShowRoom;
