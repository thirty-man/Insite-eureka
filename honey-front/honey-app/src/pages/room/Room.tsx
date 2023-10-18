import { Outlet } from "react-router-dom";

function Room() {
  return (
    <>
      <div>방</div>
      <Outlet />
    </>
  );
}
export default Room;
