import { RoomType } from "@customtype/dataTypes";
import { AccessError } from "@pages/error";
import { selectedRoomSelector } from "@recoil/selector";
import { useParams } from "react-router-dom";
import { useRecoilValue } from "recoil";

function ParticipateRoom() {
  const { roomId } = useParams();
  // 여기서 roomId를 통해 방 정보 받아오기(통신)
  const roomInfo = useRecoilValue<RoomType | undefined>(
    selectedRoomSelector(Number(roomId)),
  );

  if (!roomInfo) {
    return <AccessError />;
  }

  return (
    <>
      <div>안녕</div>
      <div>나는 {roomId} 방이야 </div>
    </>
  );
}

export default ParticipateRoom;
