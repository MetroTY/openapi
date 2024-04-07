import router from "@/router";
import { useUserStoreHook } from "@/store/modules/user";
import NProgress from "nprogress";
import "nprogress/nprogress.css";

NProgress.configure({ showSpinner: false });

router.beforeEach(async (to, next) => {
  NProgress.start();
  const userStore = useUserStoreHook();
  // 做路由权限校验
});

router.afterEach((to) => {
  NProgress.done();  // 一个进度条
});
