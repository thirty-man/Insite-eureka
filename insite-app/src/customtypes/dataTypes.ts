// -------------- CommonType
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

type ItemType = {
  id: number;
  name: string;
};

type DateSelectionType = {
  start: string;
  end: string;
  past: string;
  latest: string;
};

type StyleType = {
  fontSize: string;
};

// -------------- DtoType
type UserCountDtoType = {
  id: number;
  count: number;
  percentage: number;
  currentPage: string;
  responseTime: string;
};

type ChartDtoType = {
  name: string;
  y: number;
  dataLabels: {
    enabled: boolean;
    format: string;
    style?: StyleType;
    textOutline?: string;
  };
};

type UserRefDtoType = {
  id: number;
  referrer: string;
  rank: number;
  count: number;
  percentage: number;
};

type ButtonCountDtoType = {
  id: number;
  name: string;
  count: number;
  countPerUser: number;
};

type PageExitType = {
  id: number;
  currentUrl: string;
  exitCount: number;
  ratio: number;
};

type AbnormalType = {
  id: number;
  cookieId: string;
  time: string;
  currentUrl: string;
  language: string;
  osId: string;
};

type BounceDtoType = {
  id: number;
  currentUrl: string;
  count: number;
  ratio: number;
};

type PageEnterDtoType = {
  id: number;
  enterPage: string;
  enterCount: number;
  enterRate: number;
};

type EntryExitDtoType = {
  id: number;
  exitPage: string;
  exitCount: number;
  exitRate: number;
};

type AverageActiveTimeDtoType = {
  id: number;
  currentUrl: string;
  averageActiveTime: number;
};

type ActiveUserPerUserDtoType = {
  id: number;
  currentUrl: string;
  activeUserPerUser: number;
};

type ActiveUserCountDtoType = {
  id: number;
  currentUrl: string;
  activeUserCount: number;
  ratio: number;
};

type ViewCountsPerActiveUserDtoType = {
  id: number;
  currentUrl: string;
  count: number;
  ratio: number;
};

type OSActiveUserDtoType = {
  id: number;
  os: string;
  count: number;
  ratio: number;
};

type ActiveUserPertimeDtoType = {
  nightActiveUserCount: number;
  morningActiveUserCount: number;
  afternoonActiveUserCount: number;
  eveningActiveUserCount: number;
};

type UserStatisticsDtoType = {
  id: number;
  currentUrl: string;
  userCount: number;
};

type ViewCountsPerUserDtoType = {
  currentUrl: string;
  count: number;
  ratio: number;
};

type CookieIdUrlDtoType = {
  id: number;
  cookieId: string;
  size: number;
  viewCountsPerUserDtoList: ViewCountsPerUserDtoType[];
};

type AbnormalDtoListType = {
  id: number;
  cookieId: string;
  date: string;
  currentUrl: string;
  language: string;
  requestCnt: number;
  osId: string;
};

type UrlFlowDtoType = {
  id: number;
  beforeUrl: string;
  count: number;
};

type ButtonDistDtoType = {
  id: number;
  name: string;
  clickCounts: number;
  increaseDecreaseRate: number;
};

type EveryButtonDistResDto = {
  totalAvg: number;
  buttonDistDtoList: ButtonDistDtoType[];
};

type CurrentUrlDtoType = {
  id: number;
  currentUrl: string;
  count: number;
};

type ButtonLogDtoType = {
  id: number;
  currentUrl: string;
  clickDateTime: string;
  cookieId: string;
  isAbnormal: boolean;
};

type ButtonLogsResDto = {
  exitRate: number;
  clickCountsPerActiveUsers: number;
  buttonLogDtoList: ButtonLogDtoType[];
};

type ApplicationDtoType = {
  applicationId: number;
  name: string;
  applicationUrl: string;
  applicationToken: string;
};

// -------------- Data Type
type ButtonType = {
  id: number;
  name: string;
  counts: number;
  date: string;
};

export type {
  SideBarMenuType,
  IconsType,
  LogosType,
  ItemType,
  DateSelectionType,
  UserCountDtoType,
  ChartDtoType,
  StyleType,
  UserRefDtoType,
  ButtonCountDtoType,
  AbnormalType,
  ButtonType,
  PageExitType,
  BounceDtoType,
  PageEnterDtoType,
  EntryExitDtoType,
  AverageActiveTimeDtoType,
  ActiveUserPerUserDtoType,
  ActiveUserCountDtoType,
  ViewCountsPerActiveUserDtoType,
  OSActiveUserDtoType,
  ActiveUserPertimeDtoType,
  UserStatisticsDtoType,
  ViewCountsPerUserDtoType,
  CookieIdUrlDtoType,
  AbnormalDtoListType,
  UrlFlowDtoType,
  EveryButtonDistResDto,
  ButtonDistDtoType,
  CurrentUrlDtoType,
  ButtonLogDtoType,
  ButtonLogsResDto,
  ApplicationDtoType,
};
