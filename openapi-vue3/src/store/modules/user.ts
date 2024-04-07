import { ref } from "vue";
import store from "@/store";
import { defineStore } from "pinia";
import { resetRouter } from "@/router";
import { getUserInfoApi, loginApi, userLogout } from "@/api/login";
import { type LoginRequestData } from "@/api/login/types/login";
import routeSettings from "@/config/route";

export const useUserStore = defineStore("user", () => {
  const roles = ref<string[]>([]);
  const username = ref<string>("");
  const userId = ref<number>();
  const accessKey = ref<string>();
  const secretKey = ref<string>();

  /** 登录 */
  const login = async ({ userAccount, userPassword }: LoginRequestData) => {
    await loginApi({ userAccount, userPassword });
  };

  /** 获取用户详情 */
  const getInfo = async () => {
    const { data} = await getUserInfoApi()
    username.value = data.userAccount;
    userId.value = data.id;
    accessKey.value = data.accessKey;
    secretKey.value = data.secretKey;
    // 验证返回的 roles 是否为一个非空数组，否则塞入一个没有任何作用的默认角色，防止路由守卫逻辑进入无限循环
    roles.value = data.userRole?.length > 0 ? data.userRole : routeSettings.defaultRoles;
    return data;
  };

  /** 登出 */
  const logout = async () => {
    await userLogout();
    username.value = "";
    accessKey.value = "";
    secretKey.value = "";
    roles.value = [];
    resetRouter();
    _resetTagsView();
  };

  return { roles, username, login, getInfo, logout };
})
;

/** 在 setup 外使用 */
export function useUserStoreHook() {
  return useUserStore(store);
}
