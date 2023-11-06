import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface HeaderModalState {
  openDropdown: boolean;
  openProfile: boolean;
}

const initialState: HeaderModalState = {
  openDropdown: false,
  openProfile: false,
};

const HeaderModalStateInfoSlice = createSlice({
  name: "HeaderModalStateInfo",
  initialState,
  reducers: {
    setOpenDropdown: (state, action: PayloadAction<boolean>) => {
      state.openDropdown = action.payload;
    },
    setOpenProfile: (state, action: PayloadAction<boolean>) => {
      state.openProfile = action.payload;
    },
  },
});

export const { setOpenDropdown, setOpenProfile } =
  HeaderModalStateInfoSlice.actions;
export default HeaderModalStateInfoSlice.reducer;
