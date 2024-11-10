#Metro-API接口开放平台

> 希望为每个开发者和用户提供一个免费的接口

## 页面展示

**登录页面：**

![image-20240407171838330](http://wty.metronome.fun/1.png)



**首页欢迎页面：**

![image-20240407171838330](http://wty.metronome.fun/2.png)



**接口信息页面：**

![image-20240407171838330](http://wty.metronome.fun/6.png)



**具体接口页面：**

![image-20240407171838330](http://wty.metronome.fun/4.png)



**页面发起请求：**

![image-20240407171838330](http://wty.metronome.fun/5.png)



## 版本依赖

```
前端
  - node 18.16.0
  - 安装pnpm
后端
  - jdk 1.8
  - nacos 2.2.2
  - springboot 2.7.0
  - redis 5(win)
  - mysql 8.0.32
```



## 快速启动

```
前端
  - pnpm install && pnpm dev
后端(首先进行yml参数的配置)
  - 先单机启动nacos
  - 再启动主服务
  - 再启动网关服务
  - 模拟接口服务随时都可以启动
(SDK服务于开发者、主服务;common作为公共模块服务于主服务、网关)
```

