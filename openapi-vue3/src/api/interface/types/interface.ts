export interface InterfaceRequestByPage {
  /** admin 或 editor */
  current: number;
  /** 密码 */
  pageSize: number;
}

export interface InterfaceRequestByName {
  name: string;
}

export interface InterfaceResponse {
  id: number;
  userId: number;
  interfaceId: number;
  leftNum: number;
  name: string;
  description: string;
  avatarUrl: string;
}


export interface InterfaceInfo {
  id: number;
  name: string,
  method: string,
  url: string;
  status: number;
  reauestParams: string;
  responseParams: string;
}

export interface ResponseData {
  code: number,
  data: string,
  message: string
}

export interface RequestFormat {
  paramName: string,
  must: number | string,
  type: string,
  description: string
}

export interface PaginationResponse {
  records: InterfaceResponse[]; // 请求数据的数组
  total: number; // 总记录数
  size: number; // 每页显示的记录数
  current: number; // 当前页码
  orders: any[]; // 排序规则
  optimizeCountSql: boolean;
  searchCount: boolean;
  countId: any;
  maxLimit: any;
  pages: number; // 总页数
}
