import { Room, CreateRoom, ModifyRoom, ParticipateRoom } from "@pages/room";
import { RouteObject, createBrowserRouter } from "react-router-dom";
import Login from "@pages/login";
import RoomList from "@pages/roomlist";
import MyPage from "@pages/mypage";
import { ErrorNotFound } from "@pages/error";
import Send from "@pages/send";

const routePaths: RouteObject[] = [
  {
    id: "roomlist",
    path: "",
    element: <RoomList />,
  },
  {
    id: "login",
    path: "/login",
    element: <Login />,
  },
  {
    id: "mypage",
    path: "/mypage",
    element: <MyPage />,
  },
  {
    id: "send",
    path: "/send",
    element: <Send />,
  },
  {
    id: "room",
    path: "/room",
    element: <Room />,
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
    path: "/*",
    element: <ErrorNotFound />,
  },
];

const router = createBrowserRouter(routePaths);

export default router;
