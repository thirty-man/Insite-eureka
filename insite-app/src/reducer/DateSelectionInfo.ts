import ParsingDate from "@components/ParsingDate";
import { DateSelectionType } from "@customtypes/dataTypes";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

const todayDate = new Date();

const today: string = ParsingDate(todayDate);

const initialState: DateSelectionType = {
  past: today,
  start: today,
  end: today,
  latest: today,
};

const dateSelectionInfoSlice = createSlice({
  name: "dateSelectionInfo",
  initialState,
  reducers: {
    setStartDate: (state, action: PayloadAction<string>) => {
      state.start = action.payload;
    },
    setEndDate: (state, action: PayloadAction<string>) => {
      state.end = action.payload;
    },
    setPastDate: (state, action: PayloadAction<string>) => {
      state.past = action.payload;
    },
    setLatestDate: (state, action: PayloadAction<string>) => {
      state.latest = action.payload;
    },
  },
});

export const { setStartDate, setEndDate, setPastDate, setLatestDate } =
  dateSelectionInfoSlice.actions;
export default dateSelectionInfoSlice.reducer;
