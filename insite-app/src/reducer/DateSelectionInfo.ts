import ParsingDate from "@components/ParsingDate";
import { DateSelectionType } from "@customtypes/dataTypes";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

const todayDate = new Date();

const today: string = ParsingDate(todayDate);

const initialState: DateSelectionType = {
  start: "2003-01-01", // 똑같이 past값으로 초기값 설정
  end: today,
  past: "1999-01-01", // 백에서 past값 가져오기
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
