# 东软环保公众监督平台

基于 Spring Boot + Vue3 的环保公众监督系统，包含系统管理员端、公众监督员端、网格员端、决策者端四个前端。

## 技术栈

- **后端**：Spring Boot 3.2 + MyBatis-Plus + MySQL 8.0
- **前端**：Vue 3 + TypeScript + Vite
- **移动端组件库**：Vant（公众监督员端）
- **可视化**：ECharts（决策者端）

## 项目结构

```
东软环保公众监督平台/
├── backend/                  # Spring Boot 后端
├── NEPM-系统管理员端/        # Vue2 管理员后台
├── NEPS公众监督员端/          # Vue3 移动端
├── NEPG-网格员端/             # Vue3 移动端
├── NEPV决策者端/              # Vue3 数据大屏
└── 数据库/nep.sql             # 数据库初始化脚本
```

## 环境准备

- JDK 17+
- Maven 3.8+
- MySQL 8.0
- Node.js 18+

## 数据库初始化

1. 创建数据库：

```sql
CREATE DATABASE nep CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 导入数据：

```bash
mysql -u root -p123456 nep < 数据库/nep.sql
```

## 后端启动

```bash
cd backend
mvn spring-boot:run
```

后端默认运行在 `http://localhost:8080/nepm`

## 前端启动

### 系统管理员端

```bash
cd NEPM-系统管理员端
npm install
npm run serve
```

### 公众监督员端

```bash
cd NEPS公众监督员端
npm install
npm run dev
```

默认端口：`5176`

### 网格员端

```bash
cd NEPG-网格员端
npm install
npm run dev
```

### 决策者端

```bash
cd NEPV决策者端
npm install
npm run dev
```

## 默认账号密码

### 系统管理员端

| 账号 | 密码 |
|------|------|
| admin | 123 |

### 网格员端

| 登录编码 | 姓名 | 密码 |
|----------|------|------|
| caocao | 曹操 | 123 |
| guanyu | 关羽 | 123 |
| machao | 马超 | 123 |

### 公众监督员端

| 账号（手机号） | 姓名 | 密码 |
|----------------|------|------|
| 13147859658 | 柯镇恶 | 123 |
| 13245871254 | 朱聪 | 123 |
| 13512345678 | 梅超风 | 123 |

## 接口文档

后端启动后访问：

- Knife4j 文档：`http://localhost:8080/nepm/doc.html`
- 本地接口文档：见 `backend/接口文档.md`

## 业务说明

- **公众监督员**：查看 AQI 等级说明、提交空气质量反馈、查看历史反馈
- **网格员**：接收指派任务、现场检测、提交检测结果
- **系统管理员**：管理反馈信息、指派网格员、查看统计数据
- **决策者**：查看 AQI 趋势、省份统计、分布图表、地图数据

## 常见问题

### 1. 数据库连接报错 Public Key Retrieval is not allowed

已在 `application.properties` 中配置：

```properties
allowPublicKeyRetrieval=true
```

### 2. 中文乱码

数据库连接已配置 UTF-8：

```properties
useUnicode=true&characterEncoding=UTF-8
```

同时确保 MySQL 数据库字符集为 `utf8mb4`。

### 3. 端口 8080 被占用

```bash
netstat -ano | findstr :8080
taskkill /F /PID <PID>
```
