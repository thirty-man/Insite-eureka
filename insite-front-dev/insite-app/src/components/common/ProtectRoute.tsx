import { setSelectedMenuId } from "@reducer/SelectedSidebarMenuInfo";
import { ReactNode, useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";

interface ProtectRouteProps {
  children: ReactNode;
}

function ProtectRoute({ children }: ProtectRouteProps) {
  const [isVerified, setIsVerified] = useState<boolean>(false);
  const navi = useNavigate();
  const location = useLocation();
  const token = sessionStorage.getItem("Authorization");
  const dispatch = useDispatch();

  useEffect(() => {
    if (!token) {
      navi("/");
    } else {
      setIsVerified(true);
    }
  }, [navi, location, token]);

  useEffect(() => {
    const currentPath = location.pathname;

    const cleanup = () => {
      dispatch(setSelectedMenuId(1));
    };

    const isBoardSubPath =
      currentPath.startsWith("/board/") || currentPath === "/board";

    return isBoardSubPath ? undefined : cleanup;
  }, [location, dispatch]);

  return isVerified ? children : null;
}

export default ProtectRoute;
