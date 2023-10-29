import { useParams } from "react-router-dom";

function ParticipateRoom() {
  const { roomId } = useParams();

  return (
    <>
      <div>안녕</div>
      <div>나는 {roomId} 방이야 </div>
    </>
  );
}

export default ParticipateRoom;
