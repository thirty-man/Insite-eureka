import React from "react";
import ReactDOM from "react-dom/client";
// import "./index.css";
// import "@config/axios.config";
// import { Provider } from "react-redux";

// export const persistor = persistStore(store);
// import store from "./store";
// import { persistStore } from "redux-persist";
import router from "./router";
import { RouterProvider } from "react-router-dom";

ReactDOM.createRoot(document.getElementById("root")).render(
  // <Provider store={store}>
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
  // </Provider>,
);
