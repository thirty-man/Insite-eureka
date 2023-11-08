import ReactDOM from "react-dom/client";
import router from "@router";
import { RouterProvider } from "react-router-dom";
import GlobalStyles from "@assets/styles/globalStyles";
import { ThemeProvider } from "styled-components";
import theme from "@assets/styles/colors";
import "@config/axios.config";
import { Provider } from "react-redux";
import store from "@store";
import { persistStore } from "redux-persist";
import { PersistGate } from "redux-persist/integration/react";

const persistor = persistStore(store);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <Provider store={store}>
    <PersistGate loading={null} persistor={persistor}>
      <ThemeProvider theme={theme}>
        <GlobalStyles />
        <RouterProvider router={router} />
      </ThemeProvider>
    </PersistGate>
  </Provider>,
);
