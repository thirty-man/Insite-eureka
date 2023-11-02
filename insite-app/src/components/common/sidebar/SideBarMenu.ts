import { SideBarMenuType, IconsType } from "@customtypes/dataTypes";
import {
  IconSetting,
  IconTime,
  IconNetwork,
  IconUser,
  IconButton,
  IconApi,
  IconLink,
  IconGuide,
} from "@assets/icons";

const icons: IconsType = {
  IconSetting,
  IconTime,
  IconNetwork,
  IconUser,
  IconButton,
  IconApi,
  IconLink,
  IconGuide,
};

const SideBarMenu: SideBarMenuType[] = [
  {
    id: 0,
    image: "IconSetting",
    menu: "내 서비스 관리",
    route: "/manage",
  },
  {
    id: 1,
    image: "IconTime",
    menu: "실시간",
    route: "/realtime",
  },
  {
    id: 2,
    image: "IconNetwork",
    menu: "추적",
    route: "/track",
  },
  {
    id: 3,
    image: "IconUser",
    menu: "사용자",
    route: "/user",
  },
  {
    id: 4,
    image: "IconUser",
    menu: "활동 사용자",
    route: "/active",
  },
  {
    id: 5,
    image: "IconButton",
    menu: "버튼",
    route: "/button",
  },
  {
    id: 6,
    image: "IconApi",
    menu: "API 테스트",
    route: "/api",
  },
  {
    id: 7,
    image: "IconLink",
    menu: "서비스 바로 가기",
    route: "",
  },
  {
    id: 8,
    image: "IconGuide",
    menu: "이용 가이드",
    route: "/guide",
  },
];

export { SideBarMenu, icons };
