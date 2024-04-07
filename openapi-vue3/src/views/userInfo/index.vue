<script setup lang="ts">
import { useUserStore } from "@/store/modules/user";
import { onMounted, ref } from "vue";
import { updateAS } from "@/api/UserInterface"; // 响应式

const userStore = useUserStore();
const accessKey = ref("");
const secretKey = ref("");

const getInfo = async () => {
  const res = await userStore.getInfo();
  accessKey.value = res.accessKey;
  secretKey.value = res.secretKey;
};

onMounted(async () => {
  await getInfo();
});


const credentialsUpdate = async () => {
  await updateAS();
  await getInfo();
};

</script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>开发者凭证（调用接口的凭证）</span>
        <el-button type="text" @click="credentialsUpdate">更新凭证</el-button>
      </div>
    </template>
    <div>
      <div class="ap">AccessKey：</div>
      <span>{{ accessKey }}</span>
    </div>
    <div>
      <div class="ap">SecretKey：</div>
      <span>{{ secretKey }}</span>
    </div>
  </el-card>
</template>

<style scoped lang="scss">
.box-card {
  width: 80%;
  margin: 0 auto;

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 25px;
    font-weight: bold;
  }

  .ap {
    display: inline-block;
    margin-top: 20px;
    font-size: 15px;
    color: #409eff;
    margin-bottom: 20px;
  }
}
</style>
