import { request } from "@/utils/service";
import {
  InterfaceInfo,
  InterfaceRequestByName,
  InterfaceRequestByPage,
  InterfaceResponse,
  PaginationResponse, ResponseData
} from "@/api/interface/types/interface";

export function getInterfaceListByUserId(id) {
  return request<InterfaceResponse>({
    url: `interfaceInfo/list?id=${id}`,
    method: "get"
  });
}

export function getInterfaceInfo(interfaceId) {
  return request<InterfaceInfo>({
    url: `interfaceInfo/get?id=${interfaceId}`,
    method: "get"
  });
}

export function invoke(data) {
  return request<ResponseData>({
    url: `interfaceInfo/invoke`,
    method: "post",
    data
  });
}

/** 分页获取接口信息 */
export function getinterfaceInfoListByPage(data: InterfaceRequestByPage) {
  return request<PaginationResponse>({
    url: `interfaceInfo/list/page?current=${data.current}?pageSize=${data.pageSize}`,
    method: "get"
  });
}

/** 根据接口名称获取接口信息 */
export function getinterfaceInfoListByName(data: InterfaceRequestByName) {
  return request<InterfaceResponse>({
    url: `interfaceInfo/list?name=${data.name}`,
    method: "get"
  });
}

