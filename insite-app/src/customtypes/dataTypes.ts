type SideBarMenuType = {
  id: number;
  image: string;
  menu: string;
  route: string;
};

type IconsType = {
  [key: string]: string;
};

type LogosType = {
  [key: string]: string;
};

type ItemTypes = {
  id: number;
  name: string;
};

type UserCountDto = {
  count: number;
  percentage: number;
  currentPage: string;
};

type Style = {
  fontSize: string;
};

type ChartDto = {
  name: string;
  y: number;
  dataLables: {
    enabled: boolean;
    format: string;
    style?: Style;
    textOutline?: string;
  };
};

export type {
  SideBarMenuType,
  IconsType,
  LogosType,
  ItemTypes,
  UserCountDto,
  ChartDto,
  Style,
};
