<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import { Search } from "@element-plus/icons-vue";
import { InterfaceRequestByName, InterfaceResponse } from "@/api/interface/types/interface";
import { useRouter } from "vue-router";
import { useUserStore } from "@/store/modules/user";
import { getUserInterfaceInfo, getUserInterfaceLikedName } from "@/api/UserInterface";

const router = useRouter();
const user = useUserStore();
const userInfo = ref();
const interfaceInfos = ref<InterfaceResponse[]>([]);

onMounted(async () => {
  loading.value = true;
  userInfo.value = await user.getInfo();
  // 拿到接口信息
  const result = await getUserInterfaceInfo({ userId: userInfo.value.id });
  interfaceInfos.value = result.data;
  loading.value = false;
});

/** 登录表单数据 */
const searchFormData: InterfaceRequestByName = reactive({
  name: ""
});

const loading = ref(false);


// 分页查询
// const pagigationSearch = async () => {
//   const result = await getinterfaceInfoListByPage();
//   interfaceInfos.value = result.records;
// };

const search = async () => {
  loading.value = true
  const result = await getUserInterfaceLikedName({ userId: userInfo.value.id, name: searchFormData.name.trim() });
  interfaceInfos.value = result.data;
  loading.value = false;
};

const toInterface = (id: number, leftNum: number) => {
  // 把具体的接口id传递过去,通过params来传参
  router.push({ path: "/interfaceIndexPage", query: { id: id, leftNum: leftNum } });
};


</script>

<template>
  <div class="interface">
    <div class="top">
      <el-card class="top-card">
        <el-input
          v-model="searchFormData.name"
          maxlength="20"
          placeholder="搜索心仪的接口"
          class="input-with-select"
          @change="search"
        >
          <template #append>
            <el-button type="primary" :icon="Search" @click="search" />
          </template>
        </el-input>
      </el-card>
    </div>
    <div class="middle" v-loading="loading">
      <el-card class="middle-card"
               v-for="item in interfaceInfos"
               @click="toInterface(item.interfaceId, item.leftNum)"
               shadow="hover">
        <div class="card-header">
          <el-badge :value="item.leftNum">
            <img :src="item.avatarUrl" alt="图像丢失" />
          </el-badge>
        </div>
        <div style="text-align: center;margin: 20px auto">
          {{ item.name }}
        </div>
        <div style="text-align: center;margin: 20px auto">
          {{ item.description }}
        </div>
      </el-card>
    </div>
    <!--    需要分页的时候再加也不迟-->
    <!--    <div class="bottom">-->
    <!--      <div class="pagination">-->
    <!--        <el-pagination layout="prev, pager, next" :total="50" />-->
    <!--      </div>-->
    <!--    </div>-->
  </div>
</template>

<style scoped lang="scss">
.interface {
  height: 100%;
  width: 80%;
  margin: 0 auto;

  .top {
    .top-card {
      width: 100%;
      text-align: center;

      .input-with-select {
        height: 50px;
        margin: 20px 0;
        width: 540px;
      }
    }
  }

  .middle {
    display: flex;
    flex-wrap: wrap; /* 允许项目换行 */
    margin-top: 20px;
    gap: 10px;

    .middle-card {
      flex: 0 0 calc(20% - 8px); /* 考虑到间隙，减去间隙的宽度 */
      margin-bottom: 10px; /* 设置下方间隙为 10px */
      cursor: pointer;

      .card-header {
        text-align: center;

        img {
          width: 100px;
          height: 100px;
          border-radius: 50%;
        }
      }

    }
  }

  .bottom {
    height: 100px;
    position: relative;

    .pagination {
      position: absolute;
      bottom: 0;
      right: 50px;
    }
  }

}
</style>
