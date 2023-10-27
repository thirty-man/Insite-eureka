type RoomType = {
  id: number;
  title: string;
};

type UserType = {
  id: number;
  name: string;
};

type PotType = {
  potId: number;
  content: string;
  honeyCaseType: string;
  nickname: string;
  isCheck: boolean;
};

export type { RoomType, UserType, PotType };
