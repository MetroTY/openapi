import { request } from "@/utils/service"
import type * as Login from "./types/login"

/** 登录并返回 Token */
export function loginApi(data: Login.LoginRequestData) {
  return request<Login.LoginResponseData>({
    url: "user/login",
    method: "post",
    data
  })
}

/** 退出登录 */
export function userLogout() {
  return request({
    url: "user/logout",
    method: "post"
  })
}

/** 获取用户详情 */
export function getUserInfoApi() {
  return request<Login.UserInfoResponseData>({
    url: "user/get/login",
    method: "get"
  })
}
