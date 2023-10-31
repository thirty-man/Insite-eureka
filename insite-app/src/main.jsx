import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import store from "./store";
import { persistStore } from "redux-persist";
import "@config/axios.config";
import { Provider } from "react-redux";

export const persistor = persistStore(store);

ReactDOM.createRoot(document.getElementById("root")).render(
  <Provider store={store}>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </Provider>,
);
