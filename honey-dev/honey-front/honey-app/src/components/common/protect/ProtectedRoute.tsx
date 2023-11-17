import { useEffect, useState } from "react";
import { ProtectedRouteProps } from "@customtype/routeType";
import { useNavigate, useLocation } from "react-router-dom";

function ProtectedRoute({ children }: ProtectedRouteProps) {
  const [isVerified, setIsVerified] = useState(false);
  const navi = useNavigate();
  const location = useLocation();
  const token = sessionStorage.getItem("Authorization");

  useEffect(() => {
    if (!token && location.pathname !== "/login") {
      sessionStorage.setItem("redirectUrl", window.location.href);
      navi("/login");
    } else {
      setIsVerified(true);
    }
  }, [navi, location, token]);

  return isVerified ? children : null;
}

export default ProtectedRoute;
