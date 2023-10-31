import MainPage from "@pages/main/MainPage";
import { createBrowserRouter } from "react-router-dom";

const routePath = [
  {
    id: "main-page",
    path: "",
    element: <MainPage />,
  },
];

const router = createBrowserRouter(routePath);

export default router;
