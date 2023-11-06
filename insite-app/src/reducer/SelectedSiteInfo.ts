import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface SelectedSite {
  selectedSite: string;
}

const initialState: SelectedSite = {
  selectedSite: "moduo.kr",
};

const SelectedSiteInfoSlice = createSlice({
  name: "SelectedSiteInfo",
  initialState,
  reducers: {
    setSelectedSite: (state, action: PayloadAction<string>) => {
      state.selectedSite = action.payload;
    },
  },
});

export const { setSelectedSite } = SelectedSiteInfoSlice.actions;
export default SelectedSiteInfoSlice.reducer;
