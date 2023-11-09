import { PayloadAction, createSlice } from "@reduxjs/toolkit";

interface SelectedSidebarMenuState {
  selectedMenuId: number;
}

const initialState: SelectedSidebarMenuState = {
  selectedMenuId: 1,
};

const selectedSidebarMenuInfoSlice = createSlice({
  name: "selectedSidebarMenuInfo",
  initialState,
  reducers: {
    setSelectedMenuId: (state, action: PayloadAction<number>) => {
      state.selectedMenuId = action.payload;
    },
  },
});

export const { setSelectedMenuId } = selectedSidebarMenuInfoSlice.actions;
export default selectedSidebarMenuInfoSlice.reducer;
