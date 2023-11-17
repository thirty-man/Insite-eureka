type RoomType = {
  masterName: string;
  roomTitle: string;
  isOpen: boolean;
  memberCount: number;
  id: number;
  showTime: string;
};

type UserType = {
  id: number;
  name: string;
};

type PotType = {
  id: number;
  content: string;
  honeyCaseType: string;
  nickName: string;
  isCheck: boolean;
};

type PageType = {
  currentPage: number;
  hasNext: boolean;
  totalPages: number;
};

export type { RoomType, UserType, PotType, PageType };
