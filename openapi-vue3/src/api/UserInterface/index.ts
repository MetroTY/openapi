import { request } from "@/utils/service";
import {
  UserInterfaceRequest,
  UserInterfaceResponse,
  UserLikeInterfaceRequest
} from "@/api/UserInterface/types/interface";

export function getUserInterfaceInfo(data: UserInterfaceRequest) {
  return request<UserInterfaceResponse[]>({
    url: `userInterfaceInfo/get/byUserId?userId=${data.userId}`,
    method: "get"
  });
}

export function getUserInterfaceLikedName(data: UserLikeInterfaceRequest) {
  return request<UserInterfaceResponse[]>({
    url: `userInterfaceInfo/get/likedName/byUserId?userId=${data.userId}&&name=${data.name}`,
    method: "get"
  });
}

export function updateAS(data: UserLikeInterfaceRequest) {
  return request({
    url: `user/updateAS`,
    method: "get"
  });
}

// /** 分页获取接口信息 */
// export function getinterfaceInfoListByPage(data: InterfaceRequestByPage) {
//   return request<PaginationResponse>({
//     url: `interfaceInfo/list/page?current=${data.current}?pageSize=${data.pageSize}`,
//     method: "get"
//   });
// }
//
// /** 根据接口名称获取接口信息 */
// export function getinterfaceInfoListByName(data: InterfaceRequestByName) {
//   return request<InterfaceResponse>({
//     url: `interfaceInfo/list?name=${data.name}`,
//     method: "get"
//   });
// }

