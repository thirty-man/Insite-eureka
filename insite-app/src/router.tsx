import App from "@App";
import LoginPage from "@pages/login/LoginPage";
import { RouteObject, createBrowserRouter } from "react-router-dom";

const routePath: RouteObject[] = [
  {
    id: "login-page",
    path: "/login",
    element: <LoginPage />,
  },
  {
    id: "main-page",
    path: "",
    element: <App />,
  },
];

const router = createBrowserRouter(routePath);

export default router;
