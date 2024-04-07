export interface UserInterfaceRequest {
  userId: number;
}

export interface UserLikeInterfaceRequest {
  userId: number;
  name: string;
}

export interface UserInterfaceResponse {
  code: number,
  data: {
    id: number
    userId: number
    interfaceId: number
    leftNum: number
    name: string
    description: string
  },
  message: string
}


