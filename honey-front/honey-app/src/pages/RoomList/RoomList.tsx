// import TextButton from "@components/common/button/TextButton";
import SearchRoom from "@components/search/index";
import { useRecoilValue } from "recoil";
import roomAtoms from "@recoil/roomAtoms";

function RoomList() {
  const nowText = useRecoilValue(roomAtoms);

  return (
    <>
      <SearchRoom />
      <div>{nowText}</div>
    </>
  );
}
export default RoomList;
