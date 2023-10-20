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

export type { RoomType, UserType };
