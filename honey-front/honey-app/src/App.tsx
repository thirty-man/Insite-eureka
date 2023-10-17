// import { Outlet } from "react-router-dom";

import Login from "@pages/Login/Login";

function App() {
  return (
    <div className="bg-backgroundImg bg-cover bg-center bg-no-repeat w-screen h-screen flex items-center justify-center">
      <div className="sm:w-640 w-full h-full break-normal bg-cg overflow-y-auto ">
        <Login />
      </div>
    </div>
  );
}

export default App;
