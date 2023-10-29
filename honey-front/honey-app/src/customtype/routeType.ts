import { ReactNode } from "react";

export interface RouterFuncType {
  routeTo: (path: string) => void;
}

export interface ProtectedRouteProps {
  children: ReactNode;
}
