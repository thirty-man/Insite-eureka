import React from "react";
import ReactDOM from "react-dom/client";
import { RouterProvider } from "react-router-dom";

// import "@config/axios.config";
// import { Provider } from "react-redux";

// export const persistor = persistStore(store);
// import store from "./store";
// import { persistStore } from "redux-persist";

import router from "./router";

ReactDOM.createRoot(document.getElementById("root")).render(
  // <Provider store={store}>
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
  // </Provider>,
);
