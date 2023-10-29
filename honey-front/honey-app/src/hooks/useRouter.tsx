/* Import */
import { RouterFuncType } from "@customType/routeType";
import { useCallback } from "react";
import { useNavigate } from "react-router-dom";

function useRouter(): RouterFuncType {
  const navigate = useNavigate();
  const routeTo = useCallback(
    (path: string) => {
      // navigate(path, { replace: true });
      navigate(path);
    },
    [navigate],
  );
  return { routeTo };
}

export default useRouter;
