import { Room, CreateRoom, ModifyRoom, ParticipateRoom } from "@pages/room";
import { RouteObject, createBrowserRouter } from "react-router-dom";
import App from "@App";
import ProtectedRoute from "@components/common/protect";
import Login from "@pages/login";
import RoomList from "@pages/roomlist";
import MyPage from "@pages/mypage";
import { ErrorNotFound } from "@pages/error";
import Send from "@pages/send";

const routePaths: RouteObject[] = [
  {
    id: "app",
    path: "",
    element: <App />,
    children: [
      {
        id: "roomlist",
        path: "",
        element: (
          <ProtectedRoute>
            <RoomList />
          </ProtectedRoute>
        ),
      },
      {
        id: "login",
        path: "/login",
        element: <Login />,
      },
      {
        id: "mypage",
        path: "/mypage",
        element: (
          <ProtectedRoute>
            <MyPage />
          </ProtectedRoute>
        ),
      },
      {
        id: "send",
        path: "/send",
        element: (
          <ProtectedRoute>
            <Send />
          </ProtectedRoute>
        ),
      },
      {
        id: "room",
        path: "/room",
        element: (
          <ProtectedRoute>
            <Room />
          </ProtectedRoute>
        ),
        children: [
          {
            id: "create-room",
            path: "",
            element: <CreateRoom />,
          },
          {
            id: "modify-room",
            path: "modify",
            element: <ModifyRoom />,
          },
          {
            id: "participate-room",
            path: "participate/:roomId",
            element: <ParticipateRoom />,
          },
        ],
      },
      {
        id: "error404",
        path: "*",
        element: <ErrorNotFound />,
      },
    ],
  },
];

const router = createBrowserRouter(routePaths);

export default router;
