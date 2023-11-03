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

export type { SideBarMenuType, IconsType, LogosType, ItemTypes };
