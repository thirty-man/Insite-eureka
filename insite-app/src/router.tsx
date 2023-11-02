import App from "@App";
import ActiveUserPage from "@pages/activeuser/ActiveUserPage";
import ApiTestPage from "@pages/apitest/ApiTestPage";
import ButtonManagementPage from "@pages/buttonmanagement/ButtonManagementPage";
import NotFoundErrorPage from "@pages/error/NotFoundErrorPage";
import GuidPage from "@pages/guide/GuidePage";
import LoginPage from "@pages/login/LoginPage";
import MainPage from "@pages/main/MainPage";
import MySitePage from "@pages/mysite/MySitePage";
import RealTimePage from "@pages/realtime/RealTimePage";
import ServiceManagementPage from "@pages/servicemanagement/ServiceManagementPage";
import TrackingPage from "@pages/tracking/TrackingPage";
import UserPage from "@pages/user/UserPage";
import { RouteObject, createBrowserRouter } from "react-router-dom";

const routePath: RouteObject[] = [
  {
    id: "login-page",
    path: "/login",
    element: <LoginPage />,
  },
  {
    id: "main-page",
    path: "/main",
    element: <MainPage />,
  },
  {
    id: "my-site",
    path: "/mysite",
    element: <MySitePage />,
  },
  {
    id: "app",
    path: "",
    element: <App />,
    children: [
      {
        id: "real-time",
        path: "",
        element: <RealTimePage />,
      },
      {
        id: "service-management",
        path: "/manage",
        element: <ServiceManagementPage />,
      },
      {
        id: "tracking",
        path: "/track",
        element: <TrackingPage />,
      },
      {
        id: "user",
        path: "/user",
        element: <UserPage />,
      },
      {
        id: "active-user",
        path: "/active",
        element: <ActiveUserPage />,
      },
      {
        id: "button-management",
        path: "/button",
        element: <ButtonManagementPage />,
      },
      {
        id: "api-test",
        path: "/api",
        element: <ApiTestPage />,
      },
      {
        id: "guide",
        path: "/guide",
        element: <GuidPage />,
      },
    ],
  },
  {
    id: "error",
    path: "/*",
    element: <NotFoundErrorPage />,
  },
];

const router = createBrowserRouter(routePath);

export default router;
