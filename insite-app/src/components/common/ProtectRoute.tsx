import { ReactNode, useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

interface ProtectRouteProps {
  children: ReactNode;
}

function ProtectRoute({ children }: ProtectRouteProps) {
  const [isVerified, setIsVerified] = useState<boolean>(false);
  const navi = useNavigate();
  const location = useLocation();
  const token = sessionStorage.getItem("Authorization");

  useEffect(() => {
    if (!token && location.pathname !== "") {
      navi("/");
    } else {
      setIsVerified(true);
    }
  }, [navi, location, token]);

  return isVerified ? children : null;
}

export default ProtectRoute;
