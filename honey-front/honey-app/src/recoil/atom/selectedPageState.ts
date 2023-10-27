import { PageType } from "@customtype/dataTypes";
import { atom } from "recoil";

const defaultPage: PageType = {
  currentPage: 0,
  hasNext: true,
  totalPages: 1,
};

const selectedPageState = atom<PageType>({
  key: "selectedPageAtom",
  default: defaultPage,
});

export default selectedPageState;
