import ReactDOM from "react-dom/client";
import { RecoilRoot } from "recoil";
import "@styles/global.css";
import "@config/axios.config";
import router from "@router";
import { RouterProvider } from "react-router-dom";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <RecoilRoot>
    <RouterProvider router={router} />
  </RecoilRoot>,
);
