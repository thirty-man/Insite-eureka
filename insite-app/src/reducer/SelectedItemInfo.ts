import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface SelectedItemState {
  selectedSite: string;
  selectedButton: string;
}

const initialState: SelectedItemState = {
  selectedSite: "사이트를 설정해주세요.",
  selectedButton: "버튼을 선택해주세요.",
};

const selectedItemInfoSlice = createSlice({
  name: "selectedItemInfo",
  initialState,
  reducers: {
    setSelectedSite: (state, action: PayloadAction<string>) => {
      state.selectedSite = action.payload;
    },
    setSelectedButton: (state, action: PayloadAction<string>) => {
      state.selectedButton = action.payload;
    },
  },
});

export const { setSelectedSite, setSelectedButton } =
  selectedItemInfoSlice.actions;
export default selectedItemInfoSlice.reducer;
