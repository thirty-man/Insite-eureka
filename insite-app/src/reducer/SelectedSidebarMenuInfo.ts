import { PayloadAction, createSlice } from "@reduxjs/toolkit";

interface SelectedSidebarMenuState {
  selectedMenuId: number;
}

const initialState: SelectedSidebarMenuState = {
  selectedMenuId: 1,
};

const SelectedSidebarMenuInfoSlice = createSlice({
  name: "SelectedSidebarMenuInfo",
  initialState,
  reducers: {
    setSelectedMenuId: (state, action: PayloadAction<number>) => {
      state.selectedMenuId = action.payload;
    },
  },
});

export const { setSelectedMenuId } = SelectedSidebarMenuInfoSlice.actions;
export default SelectedSidebarMenuInfoSlice.reducer;
