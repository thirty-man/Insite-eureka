type RoomType = {
  roomId: number;
  roomName: string;
  owner: string;
  password: number | null;
};

type UserType = {
  userId: number;
  nickName: string;
};

type PotType = {
  potId: number;
  content: string;
  honeyCaseType: string;
  nickname: string;
  isCheck: boolean;
};

export type { RoomType, UserType, PotType };
