# JakartaOne 2025 Chinese Livestream Tech Talk Demo Project

Topic: Practical Application of MicroProfile Telemetry on Azure and Performance Optimization Based on MCP

Abstract: Implementing distributed tracing, metrics collection, and log aggregation on Azure using Open Liberty MicroProfile Telemetry 2.0. Developing MCP (Model Context Protocol) tools to read telemetry data and integrate with VS Code GitHub Copilot to enable intelligent code performance analysis and optimization recommendations, building a complete closed loop from monitoring to code optimization.

---

## Project Structure

```
jakartaone-2025-demo/
├── liberty-app-monitoring/         # Open Liberty application monitoring example
│   ├── src/                        # Java application source code
│   │   └── main/
│   │       ├── java/cafe/          # Cafe application business logic
│   │       ├── liberty/config/     # Liberty server configuration
│   │       ├── resources/          # Application resource files
│   │       └── webapp/             # Web application interface
│   ├── otel-collector/             # OpenTelemetry Collector configuration
│   ├── Dockerfile                  # Container configuration
│   ├── pom.xml                     # Maven project configuration
│   └── README.md                   # Liberty application documentation
├── mcp-server/                     # MCP server implementation
│   ├── azure_app_insights.py       # Azure Application Insights integration
│   ├── pyproject.toml              # Python project configuration
│   └── uv.lock                     # Dependency lock file
└── .vscode/                        # VS Code workspace configuration
    └── mcp.json                    # MCP server connection configuration
```

## Technology Stack

### Liberty Application Monitoring
- **Open Liberty**: Lightweight Java application server
- **MicroProfile Telemetry 2.0**: Microservice telemetry specification
- **OpenTelemetry**: Observability framework
- **Jakarta EE**: Enterprise Java platform
- **Azure Application Insights**: Azure application performance monitoring service

### MCP Server
- **Python**: MCP server development language
- **Model Context Protocol (MCP)**: Protocol for AI tool integration
- **Azure SDK**: Azure service integration
- **VS Code GitHub Copilot**: AI code assistant integration

## Features

### 🔍 Distributed Tracing
- Implement cross-service tracing using MicroProfile Telemetry 2.0
- Automatically capture trace information for HTTP requests, database queries, etc.
- Send trace data to Azure Application Insights

### 📊 Metrics Collection
- Collect application performance metrics (response time, throughput, error rate, etc.)
- Custom business metrics monitoring
- Real-time metrics visualization and alerting

### 📝 Log Aggregation
- Structured log output
- Correlate logs with trace information
- Centralized log management and analysis

### 🤖 Intelligent Optimization
- MCP server reads Azure Application Insights data
- Integration with VS Code GitHub Copilot
- Intelligent code optimization recommendations based on telemetry data
- Automatic performance bottleneck identification and fix suggestions

## Quick Start

### Prerequisites
- Azure subscription
- Java 17+
- Maven 3.9.8+
- Azure CLI 2.70.0+
- Git
- VS Code and GitHub Copilot extension

### Running the Liberty Application

For detailed Liberty application setup, build, run, and deployment instructions, please refer to:

👉 **[Liberty Application Monitoring Detailed Guide](liberty-app-monitoring/README.md)**

The guide includes:
- Azure resource creation steps
- OpenTelemetry Collector build and deployment
- Open Liberty instance application build and deployment
- Observing OpenTelemetry data using Azure Application Insights

### Setting Up the MCP Server

1. **Sign into Azure CLI**

```bash
az login
```

2. **Start MCP Server in VS Code**
   - Open VS Code workspace
   - Open the `.vscode/mcp.json` file
   - Click the "Start" icon in the file to start the MCP server
   - Optional: Click "More > Show Output" to view MCP server logs

## Demo Scenarios

### 1. Application Performance Monitoring
- Access the Cafe application and trigger business operations
- View Application Insights data in Azure Portal
- Observe distributed tracing, metrics, and logs

### 2. Performance Bottleneck Identification
- Use MCP tools to analyze performance data
- GitHub Copilot provides optimization suggestions

### 3. Code Optimization Practice
- Identify hotspot code based on telemetry data
- Apply AI-suggested optimization solutions

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Related Links

- [JakartaOne 2025](https://jakartaone.org/)
- [Open Liberty](https://openliberty.io/)
- [MicroProfile Telemetry](https://microprofile.io/specifications/microprofile-telemetry/)
- [Azure Application Insights](https://docs.microsoft.com/azure/azure-monitor/app/app-insights-overview)
- [Model Context Protocol](https://modelcontextprotocol.io/)
- [GitHub Copilot](https://github.com/features/copilot)
