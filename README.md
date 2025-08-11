# JakartaOne 2025 中文直播技术分享示例项目

主题: 基于 Azure 的 MicroProfile Telemetry 实践应用与基于 MCP 的性能优化

摘要: 使用 Open Liberty MicroProfile Telemetry 2.0 在 Azure 上实现分布式链路追踪、指标收集和日志聚合。开发 MCP（Model Context Protocol）工具来读取遥测数据，并与 VS Code GitHub Copilot 集成，实现智能代码性能分析和优化建议，构建从监控到代码优化的完整闭环。

---

## 项目结构

```
jakartaone-2025-demo/
├── liberty-app-monitoring/         # Open Liberty 应用监控示例
│   ├── src/                        # Java 应用源码
│   │   └── main/
│   │       ├── java/cafe/          # Cafe 应用业务逻辑
│   │       ├── liberty/config/     # Liberty 服务器配置
│   │       ├── resources/          # 应用资源文件
│   │       └── webapp/             # Web 应用界面
│   ├── otel-collector/             # OpenTelemetry Collector 配置
│   ├── Dockerfile                  # 容器化配置
│   ├── pom.xml                     # Maven 项目配置
│   └── README.md                   # Liberty 应用说明
├── mcp-server/                     # MCP 服务器实现
│   ├── azure_app_insights.py       # Azure Application Insights 集成
│   ├── pyproject.toml              # Python 项目配置
│   └── uv.lock                     # 依赖锁定文件
└── .vscode/                        # VS Code 工作区配置
    └── mcp.json                    # MCP 服务器连接配置
```

## 技术栈

### Liberty 应用监控部分
- **Open Liberty**: 轻量级 Java 应用服务器
- **MicroProfile Telemetry 2.0**: 微服务遥测规范
- **OpenTelemetry**: 可观测性框架
- **Jakarta EE**: 企业级 Java 平台
- **Azure Application Insights**: Azure 应用性能监控服务

### MCP 服务器部分  
- **Python**: MCP 服务器开发语言
- **Model Context Protocol (MCP)**: 与 AI 工具集成的协议
- **Azure SDK**: Azure 服务集成
- **VS Code GitHub Copilot**: AI 代码助手集成

## 功能特性

### 🔍 分布式追踪
- 使用 MicroProfile Telemetry 2.0 实现跨服务链路追踪
- 自动捕获 HTTP 请求、数据库查询等操作的追踪信息
- 将追踪数据发送到 Azure Application Insights

### 📊 指标收集
- 收集应用性能指标（响应时间、吞吐量、错误率等）
- 自定义业务指标监控
- 实时指标可视化和告警

### 📝 日志聚合
- 结构化日志输出
- 日志关联追踪信息
- 集中化日志管理和分析

### 🤖 智能优化
- MCP 服务器读取 Azure Application Insights 数据
- 与 VS Code GitHub Copilot 集成
- 基于遥测数据的智能代码优化建议
- 性能瓶颈自动识别和修复建议

## 快速开始

### 前提条件
- Azure 订阅
- Java 17+
- Maven 3.9.8+
- Azure CLI 2.70.0+
- Git
- VS Code 和 GitHub Copilot 扩展

### 运行 Liberty 应用

详细的 Liberty 应用设置、构建、运行和部署说明，请参考：

👉 **[Liberty 应用监控详细指南](liberty-app-monitoring/README.md)**

该指南包含：
- Azure 资源创建步骤
- OpenTelemetry Collector 构建和部署
- Open Liberty 实例应用构建和部署
- 使用 Azure Application Insights 观察Open Telemetry 数据

### 设置 MCP 服务器

1. **登录 Azure CLI**

```bash
az login
```

2. **在 VS Code 中启动 MCP 服务器**
   - 打开 VS Code 工作区
   - 打开 `.vscode/mcp.json` 文件
   - 点击文件中的 "Start" 图标启动 MCP 服务器
   - 可选：点击 "More > Show Output" 查看 MCP 服务器日志

## 演示场景

### 1. 应用性能监控
- 访问 Cafe 应用，触发业务操作
- 在 Azure Portal 查看 Application Insights 数据
- 观察分布式追踪、指标和日志

### 2. 性能瓶颈识别
- 使用 MCP 工具分析性能数据
- GitHub Copilot 提供优化建议

### 3. 代码优化实践
- 根据遥测数据识别热点代码
- 应用 AI 建议的优化方案

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 相关链接

- [JakartaOne 2025](https://jakartaone.org/)
- [Open Liberty](https://openliberty.io/)
- [MicroProfile Telemetry](https://microprofile.io/specifications/microprofile-telemetry/)
- [Azure Application Insights](https://docs.microsoft.com/azure/azure-monitor/app/app-insights-overview)
- [Model Context Protocol](https://modelcontextprotocol.io/)
- [GitHub Copilot](https://github.com/features/copilot)
