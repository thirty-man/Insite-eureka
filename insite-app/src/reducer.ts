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
    "dateSelectionInfo",
    "selectedSiteInfo",
    "selectedSidebarMenuInfo",
  ],
  blacklist: [],
};

const rootReducer = combineReducers({
  selectedItemInfo: selectedItemInfoReducer,
  dateSelectionInfo: dateSelectionInfoReducer,
  selectedSidebarMenuInfo: selectedSidebarMenuInfoReducer,
});

export type RootState = ReturnType<typeof rootReducer>;

const persistedReducer = persistReducer(persistConfig, rootReducer);

export default persistedReducer;
