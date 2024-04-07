import { type RouteRecordRaw, createRouter } from "vue-router";
import { history, flatMultiLevelRoutes } from "./helper";
import routeSettings from "@/config/route";

const Layouts = () => import("@/layouts/index.vue");

/**
 * 常驻路由
 * 除了 redirect/403/404/login 等隐藏页面，其他页面建议设置 Name 属性
 */
export const constantRoutes: RouteRecordRaw[] = [
  {
    path: "/redirect",
    component: Layouts,
    meta: {
      hidden: true
    },
    children: [
      {
        path: ":path(.*)",
        component: () => import("@/views/redirect/index.vue")
      }
    ]
  },
  {
    path: "/403",
    component: () => import("@/views/error-page/403.vue"),
    meta: {
      hidden: true
    }
  },
  {
    path: "/404",
    component: () => import("@/views/error-page/404.vue"),
    meta: {
      hidden: true
    },
    alias: "/:pathMatch(.*)*"
  },
  {
    path: "/login",
    component: () => import("@/views/login/index.vue"),
    meta: {
      hidden: true
    }
  },
  {
    path: "/",
    component: Layouts,
    redirect: "/welcome",
    children: [
      {
        path: "welcome",
        component: () => import("@/views/welcome/index.vue"),
        name: "Welcome",
        meta: {
          title: "欢迎页",
          svgIcon: "Welcome",
          affix: true
        }
      }
    ]
  },
  {
    path: "/interfacePage",
    component: Layouts,
    redirect: "/interfacePage/index",
    children: [
      {
        path: "index",
        component: () => import("@/views/interfacePage/index.vue"),
        name: "interfacePage",
        meta: {
          title: "接口页面",
          svgIcon: "interfacePage",
          affix: true
        }
      }
    ]
  },
  {
    path: "/interfaceIndexPage",
    component: Layouts,
    redirect: "/interfaceIndexPage/index",
    children: [
      {
        path: "index",
        component: () => import("@/views/interfaceIndexPage/index.vue"),
        name: "interfaceIndexPage",
        meta: {
          title: "具体的接口页面",
          svgIcon: "interfaceIndexPage",
          affix: true
        }
      }
    ]
  },
  // 用户的详细信息
  {
    path: "/userInfo",
    component: Layouts,
    redirect: "/userInfo/index",
    children: [
      {
        path: "index",
        component: () => import("@/views/userInfo/index.vue"),
        name: "userInfo",
        meta: {
          title: "用户详细信息",
          svgIcon: "userInfo",
          affix: true
        }
      }
    ]
  }
];

/**
 * 动态路由
 * 用来放置有权限 (Roles 属性) 的路由
 * 必须带有 Name 属性
 */
// export const dynamicRoutes: RouteRecordRaw[] = [
//   {
//     path: "/permission",
//     component: Layouts,
//     redirect: "/permission/page",
//     name: "Permission",
//     meta: {
//       title: "权限",
//       svgIcon: "lock",
//       roles: ["admin", "editor"], // 可以在根路由中设置角色
//       alwaysShow: true // 将始终显示根菜单
//     },
//     children: [
//       {
//         path: "page",
//         component: () => import("@/views/permission/page.vue"),
//         name: "PagePermission",
//         meta: {
//           title: "页面级",
//           roles: ["admin"] // 或者在子导航中设置角色
//         }
//       },
//       {
//         path: "directive",
//         component: () => import("@/views/permission/directive.vue"),
//         name: "DirectivePermission",
//         meta: {
//           title: "按钮级" // 如果未设置角色，则表示：该页面不需要权限，但会继承根路由的角色
//         }
//       }
//     ]
//   }
// ];

const router = createRouter({
  history,
  routes: routeSettings.thirdLevelRouteCache ? flatMultiLevelRoutes(constantRoutes) : constantRoutes
});

/** 重置路由 */
export function resetRouter() {
  // 注意：所有动态路由路由必须带有 Name 属性，否则可能会不能完全重置干净
  try {
    router.getRoutes().forEach((route) => {
      const { name, meta } = route;
      if (name && meta.roles?.length) {
        router.hasRoute(name) && router.removeRoute(name);
      }
    });
  } catch {
    // 强制刷新浏览器也行，只是交互体验不是很好
    window.location.reload();
  }
}

export default router;
