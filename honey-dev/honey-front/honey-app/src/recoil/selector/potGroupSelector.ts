import potListState from "@recoil/atom/potListState";
import { selector } from "recoil";

const potGroupSelector = selector({
  key: "groupedPotList",
  get: ({ get }) => {
    const potList = get(potListState);
    const chunkSize = 9;
    const groupedPotList = [];

    for (let i = 0; i < potList.length; i += chunkSize) {
      groupedPotList.push(potList.slice(i, i + chunkSize));
    }

    return groupedPotList;
  },
});

export default potGroupSelector;
