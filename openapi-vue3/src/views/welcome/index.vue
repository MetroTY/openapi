<script setup lang="ts">
import "vue3-emoji/dist/style.css";

const items = [
  {
    header: "1️⃣ 多样化的接口选择", content: "平台上提供丰富多样的接口供您选择，涵盖了各个领域的功能和服务，满足不同需求。"
  },
  {
    header: "2️⃣ 在线调试功能", content: "您可以在平台上进行接口在线调试，快速验证接口的功能和效果，节省了开发调试的时间和工作量。"
  },
  {
    header: "3️⃣ 客户端SDK支持", content: "为了方便开发者集成接口到自己的代码中，平台提供了客户端SDK，使调用接口变得更加简单和便捷。"
  },
  {
    header: "4️⃣ 开发者文档和技术支持",
    content: "平台提供了详细的开发者文档和技术支持，帮助开发者快速接入和发布接口，解决遇到的问题和困难。"
  }
];
</script>

<template>
  <div class="wel">
    <el-card class="card">
      <div class="title">
        <h1>欢迎使用 Metro-API 接口开放平台😀</h1>
      </div>
      <div class="sub-title"><h2> Metro-API 接口开放平台是一个为用户和开发者程供全面API接口调用服务的平台 ⚒️</h2></div>
      <div class="content">
        <h3>
          🙃
          目前是测试阶段，当前登录的是管理员账号，可以<span
          style="color: dodgerblue;">在线进行接口调试</span>，快速验证接口的功能和效果。
        </h3>
        <h3>🧑‍💻 作为开发者我们提供了客户端SDK,通过用户详情的<span style="color: dodgerblue;">开发者凭证</span>即可将轻松集成接口到您的项目中，实现更高效的开发和调用。
        </h3>
        <h3>😏 我们还提供了<span style="color: dodgerblue;">开发者在线文档和技术支持</span>，帮助您快速接入和发布接口。
        </h3>
        <h3>
          🙂‍
          无论您是用户还是开发者，Metro-API接口开放平台都致力于提供稳定、安全、高效的接口调用服务，帮助您实现更快速、便捷的开发和调用体验。
        </h3>
      </div>
      <div class="boxes">
        <el-card v-for="item in items" class="box">
          <template #header>
            <div class="card-header">
              <div>{{ item.header }}</div>
            </div>
          </template>
          <div class="content">
            <div>{{ item.content }}</div>
          </div>
        </el-card>
      </div>
    </el-card>
  </div>
</template>

<style scoped lang="scss">
.wel {
  width: 100%;
  display: flex;
  justify-items: center;
  font-weight: bold;

  .card {
    width: 80%;
    margin: 0 auto;

    .boxes {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 10px 10px;

      .content {
        height: 100px;
      }

      .card-header {
        display: flex;
        align-items: center;
      }

      .box {
        margin-left: 10px;
        border-radius: 3%;
      }
    }
  }
}
</style>
