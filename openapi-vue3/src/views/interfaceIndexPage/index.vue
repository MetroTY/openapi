<script setup lang="ts">
import { useRoute } from "vue-router";
import { onMounted, reactive, ref } from "vue";
import { InterfaceInfo, RequestFormat } from "@/api/interface/types/interface";
import { getInterfaceInfo, invoke } from "@/api/interface";

const route = useRoute();
const { id, leftNum } = route.query;
const totalNum = ref(leftNum);
const interfaceInfoDisplay = ref<InterfaceInfo>({});

const tableData = ref<RequestFormat[]>([]);

onMounted(async () => {
  const result = await getInterfaceInfo(id);
  interfaceInfoDisplay.value = result.data;
  tableData.value = JSON.parse(interfaceInfoDisplay.value.requestParams);
  // 遍历must为0的话就是✔，为1的话就是❌
  tableData.value = tableData.value.map(item => {
    return {
      ...item,
      must: item.status === 0 ? "✔" : "❌"
    };
  });
});

const activeName = ref("first");
const handleClick = (tab, event) => {
  console.log(tab, event);
};

// 请求参数部分
const requestData = reactive([]);
const dialogVisible = ref(false);
const formData = ref({ name: "", value: "" });  // 编辑回显的使用用的
const editingIndex = ref(-1);

const handleAdd = () => {
  formData.value.name = "";
  formData.value.value = "";
  editingIndex.value = -1;
  dialogVisible.value = true;
};

const handleEdit = (row) => {
  formData.value.name = row.name;
  formData.value.value = row.value;
  editingIndex.value = requestData.indexOf(row);
  dialogVisible.value = true;
};

// 删除该行的逻辑
const handleDelete = (row) => {
  // 直接删除
  const index = requestData.indexOf(row);
  requestData.splice(index, 1);
};

// 编辑保存
const handleSave = () => {
  if (editingIndex.value > -1) {
    requestData[editingIndex.value].name = formData.value.name;
    requestData[editingIndex.value].value = formData.value.value;
  } else {
    // 添加保存
    requestData.push({ name: formData.value.name, value: formData.value.value });
  }
  dialogVisible.value = false;
};

const handleCloseDialog = (done) => {
  done(); // 关闭对话框
};

// 发送请求拿到请求结果
const loadingState = ref(false);
const responseData = ref("");
const sendRequest = async () => {
  loadingState.value = true;
  responseData.value = "";
  await sleep(2000);
  try {
    // 将数组转换为对象
    const requestDataObject = requestData.reduce((acc, item) => {
      acc[item.name] = item.value;
      return acc;
    }, {});
    const res = await invoke({
      id: id,
      requestParams: {
        ...requestDataObject
      }
    });
    if (res.code === 0) {
      // 响应成功
      responseData.value = res;
      totalNum.value--;
    }
  } finally {
    loadingState.value = false;
  }
};

// 定义一个异步函数来休眠指定的毫秒数
const sleep = async (ms) => {
  await new Promise(resolve => setTimeout(resolve, ms));
};


</script>

<template>
  <div class="info">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>{{ interfaceInfoDisplay.name }}</span>
        </div>
      </template>
      <div class="interfaceInfo">
        <span>
          <span class="ap">接口地址：</span>
          {{ interfaceInfoDisplay.url }}
        </span>
        <span>
          <span class="ap">请求方法：</span>
          {{ interfaceInfoDisplay.method }}
        </span>
        <span>
          <span class="ap">接口状态：</span>
          {{ interfaceInfoDisplay.status === 1 ? "✔" : "❌ " }}
        </span>
        <span>
          <span class="ap">消费次数：</span>
          1
        </span>
        <span>
          <span class="ap">剩余次数：</span>
          {{ totalNum }}
        </span>
      </div>
      <template #footer>
        <a href="https://www.bilibili.com" target="_blank" style="color: #409eff">点击获取详细的参考文档</a>
      </template>
    </el-card>
    <el-card class="api">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="API文档" name="first">
          <div style="margin: 10px 0">📘请求参数说明:</div>
          <div class="table-one">
            <el-table :data="tableData" style="width: 100%" border="true">
              <el-table-column prop="paramName" label="参数名称"></el-table-column>
              <el-table-column prop="must" label="必选"></el-table-column>
              <el-table-column prop="type" label="类型"></el-table-column>
              <el-table-column prop="description" label="描述" width="800px"></el-table-column>
            </el-table>
          </div>
          <div class="returnExample">
            <div style="margin: 10px 0">📘返回示例:</div>
            <pre>
              {{ interfaceInfoDisplay.responseParams }}
            </pre>
          </div>
        </el-tab-pane>
        <el-tab-pane label="在线调试" name="second">
          <div style="margin: 10px 0">📘请求参数设置:</div>
          <div class="table-two">
            <el-table :data="requestData" style="width: 60%" border>
              <el-table-column label="参数名称" prop="name"></el-table-column>
              <el-table-column label="参数值" prop="value"></el-table-column>
              <el-table-column label="操作">
                <template #default="{ row }">
                  <el-button size="default" @click="handleEdit(row)">编辑</el-button>
                  <el-button size="default" type="danger" @click="handleDelete(row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="display: flex;margin-top: 20px">
              <el-button @click="handleAdd" type="primary">添加数据</el-button>
              <el-button @click="sendRequest" type="primary">发送请求</el-button>
            </div>
            <el-dialog
              v-model="dialogVisible"
              title="编辑数据"
              width="30%"
              :before-close="handleCloseDialog">
              <el-form v-model="formData" label-width="80px">
                <el-form-item label="参数名称">
                  <el-input v-model="formData.name"></el-input>
                </el-form-item>
                <el-form-item label="参数值">
                  <el-input v-model="formData.value"></el-input>
                </el-form-item>
              </el-form>
              <div class="dialog-footer" slot="footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSave">保存</el-button>
              </div>
            </el-dialog>
          </div>
          <div class="returnExample">
            <div style="margin: 10px 0">📘返回结果:</div>
            <div v-if="loadingState">
              <el-skeleton v-loading="loadingState" />
            </div>
            <pre v-if="responseData" style="white-space: pre-wrap">
              {{ responseData }}
            </pre>
          </div>
        </el-tab-pane>
        <el-tab-pane label="SDK调用" name="fourth">
          <div style="margin: 20px">
            <a href="https://www.bilibili.com" target="_blank" style="color: #409eff" >SDK参考文档</a>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<style scoped lang="scss">
.info {
  width: 80%;
  margin: 0 auto;

  .box-card {
    .card-header {
      font-size: 25px;
      font-weight: bold;
    }

    .interfaceInfo {
      width: 80%;
      display: flex;
      justify-content: space-between;

      .ap {
        color: dodgerblue;
      }
    }
  }

  .api {
    margin-top: 30px;

    .returnExample {
      margin-top: 20px;

      pre {
        border: 1px solid #999;
        page-break-inside: avoid;
        display: block;
        padding: 3px 20px;
        margin: 0 0 10px;
        font-size: 13px;
        line-height: 20px;
        word-break: break-all;
        word-wrap: break-word;
        background-color: #f5f5f5;
        border: 1px solid rgba(0, 0, 0, 0.15);
        -webkit-border-radius: 4px;
        -moz-border-radius: 4px;
        border-radius: 4px;
        font-family: Monaco, Menlo, Consolas, "Courier New", monospace;
        color: #333333;
      }

    }
  }


}
</style>
