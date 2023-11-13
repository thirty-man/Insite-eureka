import { combineReducers } from "redux";
import { persistReducer } from "redux-persist";
import storageSession from "redux-persist/lib/storage/session";
import selectedItemInfoReducer from "@reducer/SelectedItemInfo";
import selectedSidebarMenuInfoReducer from "@reducer/SelectedSidebarMenuInfo";
import dateSelectionInfoReducer from "@reducer/DateSelectionInfo";

const persistConfig = {
  key: "root",
  storage: storageSession,
  whitelist: [
    "DateSelectionInfo",
    "SelectedSiteInfo",
    "SelectedSidebarMenuInfo",
  ],
  blacklist: [],
};

const rootReducer = combineReducers({
  SelectedItemInfo: selectedItemInfoReducer,
  DateSelectionInfo: dateSelectionInfoReducer,
  SelectedSidebarMenuInfo: selectedSidebarMenuInfoReducer,
});

export type RootState = ReturnType<typeof rootReducer>;

const persistedReducer = persistReducer(persistConfig, rootReducer);

export default persistedReducer;
