import ParsingDate from "@components/ParsingDate";
import { DateSelectionType } from "@customtypes/dataTypes";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

const todayDate = new Date();

const today: string = ParsingDate(todayDate);

interface DateSelectionState {
  realtimeDate: DateSelectionType;
  trackDate: DateSelectionType;
  userDate: DateSelectionType;
  activeDate: DateSelectionType;
  buttonDate: DateSelectionType;
}

const initialState: DateSelectionState = {
  realtimeDate: { start: today, end: today },
  trackDate: { start: today, end: today },
  userDate: { start: today, end: today },
  activeDate: { start: today, end: today },
  buttonDate: { start: today, end: today },
};

const DateSelectionInfoSlice = createSlice({
  name: "DateSelectionInfo",
  initialState,
  reducers: {
    setRealtimeDate: (state, action: PayloadAction<string>) => {
      state.realtimeDate.start = action.payload;
      state.realtimeDate.end = action.payload;
    },
    setTrackDate: (state, action: PayloadAction<string>) => {
      state.trackDate.start = action.payload;
      state.trackDate.end = action.payload;
    },
    setUserDate: (state, action: PayloadAction<string>) => {
      state.userDate.start = action.payload;
      state.userDate.end = action.payload;
    },
    setActivekDate: (state, action: PayloadAction<string>) => {
      state.activeDate.start = action.payload;
      state.activeDate.end = action.payload;
    },
    setButtonDate: (state, action: PayloadAction<string>) => {
      state.buttonDate.start = action.payload;
      state.buttonDate.end = action.payload;
    },
  },
});

export const {
  setRealtimeDate,
  setTrackDate,
  setUserDate,
  setActivekDate,
  setButtonDate,
} = DateSelectionInfoSlice.actions;
export default DateSelectionInfoSlice.reducer;
