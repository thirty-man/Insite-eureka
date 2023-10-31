import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import store from "./store";
import { persistStore } from "redux-persist";

export let persistor = persistStore(store);

ReactDOM.createRoot(document.getElementById("root")).render(
  <Provider store={store}>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </Provider>,
);
