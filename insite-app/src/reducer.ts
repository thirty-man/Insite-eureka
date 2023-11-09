import { combineReducers } from "redux";
import { persistReducer } from "redux-persist";
import storageSession from "redux-persist/lib/storage/session";
import SelectedItemInfoReducer from "@reducer/SelectedItemInfo";
import SelectedSidebarMenuInfoReducer from "@reducer/SelectedSidebarMenuInfo";
import DateSelectionInfoReducer from "@reducer/DateSelectionInfo";

const persistConfig = {
  key: "root",
  storage: storageSession,
  whitelist: [
    "DateSelectionInfo",
    "SelectedSiteInfo",
    "selectedSidebarMenuInfo",
  ],
  blacklist: [],
};

const rootReducer = combineReducers({
  SelectedItemInfo: SelectedItemInfoReducer,
  DateSelectionInfo: DateSelectionInfoReducer,
  SelectedSidebarMenuInfo: SelectedSidebarMenuInfoReducer,
});

export type RootState = ReturnType<typeof rootReducer>;

const persistedReducer = persistReducer(persistConfig, rootReducer);

export default persistedReducer;
