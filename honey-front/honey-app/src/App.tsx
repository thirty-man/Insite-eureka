import "@config/recoil";
import { RouterProvider } from "react-router-dom";
import router from "./router";

function App() {
  return (
    <div className="bg-backgroundImg bg-cover bg-center bg-no-repeat w-screen h-screen flex items-center justify-center">
      <div className="sm:w-640 w-full h-full break-normal bg-cg overflow-y-auto ">
        <RouterProvider router={router} />
      </div>
    </div>
  );
}

export default App;
