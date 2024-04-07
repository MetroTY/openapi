<script lang="ts" setup>
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useSettingsStore } from "@/store/modules/settings";
import { useUserStore } from "@/store/modules/user";
import { UserFilled } from "@element-plus/icons-vue";
import ThemeSwitch from "@/components/ThemeSwitch/index.vue";
import Screenfull from "@/components/Screenfull/index.vue";
import Top from "@/layouts/components/Top/index.vue";

const router = useRouter();
const userStore = useUserStore();
const settingsStore = useSettingsStore();
const { showThemeSwitch, showScreenfull } = storeToRefs(settingsStore);

/** 登出 */
const logout = () => {
  router.push({ path: "/login" });
  userStore.logout();
};

const toUserInfo = () => {
  router.push("/userInfo");
};

</script>

<template>
  <Top />
  <div class="navigation-bar">
    <div class="right-menu">
      <Screenfull v-if="showScreenfull" class="right-menu-item" />
      <ThemeSwitch v-if="showThemeSwitch" class="right-menu-item" />
      <el-dropdown class="right-menu-item">
        <div class="right-menu-avatar">
          <el-avatar :icon="UserFilled" src="src/assets/layouts/travel.png" :size="30" />
          <span>{{ userStore.username }}</span>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item divided>
              <span style="display: block" @click="toUserInfo">详细信息</span>
            </el-dropdown-item>
            <el-dropdown-item divided @click="logout">
              <span style="display: block">退出登录</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.navigation-bar {
  height: var(--v3-navigationbar-height);
  overflow: hidden;
  color: var(--v3-navigationbar-text-color);
  display: flex;
  justify-content: space-between;

  .hamburger {
    display: flex;
    align-items: center;
    height: 100%;
    padding: 0 15px;
    cursor: pointer;
  }

  .breadcrumb {
    flex: 1;
    // 参考 Bootstrap 的响应式设计将宽度设置为 576
    @media screen and (max-width: 576px) {
      display: none;
    }
  }

  .sidebar {
    flex: 1;
    // 设置 min-width 是为了让 Sidebar 里的 el-menu 宽度自适应
    min-width: 0px;

    :deep(.el-menu) {
      background-color: transparent;
    }

    :deep(.el-sub-menu) {
      &.is-active {
        .el-sub-menu__title {
          color: var(--el-color-primary) !important;
        }
      }
    }
  }

  .right-menu {
    margin-right: 10px;
    height: 100%;
    display: flex;
    align-items: center;

    .right-menu-item {
      padding: 0 10px;
      cursor: pointer;

      .right-menu-avatar {
        display: flex;
        align-items: center;

        .el-avatar {
          margin-right: 10px;
        }

        span {
          font-size: 16px;
        }
      }
    }
  }
}
</style>
