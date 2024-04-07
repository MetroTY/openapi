<script lang="ts" setup>
import { useRouter } from "vue-router";

const router = useRouter();

const toWel = () => {
  router.push({ path: "/welcome" });
};

const toInterface = () => {
  router.push({ path: "/interfacePage" });
};

</script>

<template>
  <div class="layout-side">
    <div class="wel" @click="toWel">
      <span style="cursor: pointer">💻欢迎</span>
    </div>
    <div class="interface" @click="toInterface">
      <span style="cursor: pointer">⛺接口平台</span>
    </div>
    <!-- 跳转到外部链接 -->
    <a href="https://www.bilibili.com" target="_blank" class="dev">📓开发者文档</a>
  </div>
</template>

<style lang="scss" scoped>
.layout-side {
  width: 90%;
  display: flex;
  align-items: center;

  .interface {
    margin-left: 20px;
  }

  .dev {
    margin-left: 20px;
  }
}
</style>
