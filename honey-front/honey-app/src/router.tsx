import { RouteObject, createBrowserRouter } from "react-router-dom";
import Login from "@pages/login";

const routePaths: RouteObject[] = [
  {
    id: "login",
    path: "/login",
    element: <Login />,
  },
];

const router = createBrowserRouter(routePaths);

export default router;
