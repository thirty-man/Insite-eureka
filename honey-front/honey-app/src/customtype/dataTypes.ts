type RoomType = {
  id: number;
  title: string;
  password: string | null;
  owner: string | null;
};

type UserType = {
  userId: number;
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
