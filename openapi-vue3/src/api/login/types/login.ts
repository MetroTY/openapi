export interface LoginRequestData {
  /** admin 或 editor */
  userAccount: string;
  /** 密码 */
  userPassword: string;
}

// ApiResponseData是一个泛型接口
export type LoginCodeResponseData = ApiResponseData<string>

export type LoginResponseData = ApiResponseData<{ token: string }>

export type UserInfoResponseData = ApiResponseData<{
  id: number;
  userAccount: string;
  userRole: string[];
  userName: string;
  accessKey: string;
  secretKey: string;
}>

