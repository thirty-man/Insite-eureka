import App from "@App";
import ProtectRoute from "@components/common/ProtectRoute";
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
    path: "/",
    element: <MainPage />,
  },
  {
    id: "my-site",
    path: "/mysite",
    element: (
      <ProtectRoute>
        <MySitePage />
      </ProtectRoute>
    ),
  },
  {
    id: "app",
    path: "board",
    element: <App />,
    children: [
      {
        id: "real-time",
        path: "",
        element: (
          <ProtectRoute>
            <RealTimePage />
          </ProtectRoute>
        ),
      },
      {
        id: "service-management",
        path: "manage",
        element: (
          <ProtectRoute>
            <ServiceManagementPage />
          </ProtectRoute>
        ),
      },
      {
        id: "tracking",
        path: "track",
        element: (
          <ProtectRoute>
            <TrackingPage />
          </ProtectRoute>
        ),
      },
      {
        id: "user",
        path: "user",
        element: (
          <ProtectRoute>
            <UserPage />
          </ProtectRoute>
        ),
      },
      {
        id: "active-user",
        path: "active",
        element: (
          <ProtectRoute>
            <ActiveUserPage />
          </ProtectRoute>
        ),
      },
      {
        id: "button-management",
        path: "button",
        element: (
          <ProtectRoute>
            <ButtonManagementPage />
          </ProtectRoute>
        ),
      },
      {
        id: "api-test",
        path: "api",
        element: (
          <ProtectRoute>
            <ApiTestPage />
          </ProtectRoute>
        ),
      },
      {
        id: "guide",
        path: "guide",
        element: (
          <ProtectRoute>
            <GuidPage />
          </ProtectRoute>
        ),
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
