package com.appleyk.mcp;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

/**
 * @author appleyk
 * @version v1.0.0
 * @date created on 2025/3/20:15:53
 * @description MCP 客户端测试 （使用stdio传输，MCP服务器由客户端自动启动，但前提是先构建本地的服务器jar）
 */
@SpringBootTest
@ActiveProfiles("integration-test")
@Tag("integration")
public class McpClientTest {

    public static void main(String[] args) {
        var stdioParams = ServerParameters.builder("D:\\data\\graalvm-ce-java17-22.3.2\\bin\\java")
                .args("-Dspring.ai.mcp.server.stdio=true",
                        "-jar",
                        "D:\\gitee\\mcp\\mcp.jar")
                .build();
        var stdioTransport = new StdioClientTransport(stdioParams);
        var mcpClient = McpClient.sync(stdioTransport).build();
        mcpClient.initialize();
        McpSchema.ListToolsResult listToolsResult = mcpClient.listTools();
        List<McpSchema.Tool> tools = listToolsResult.tools();
        for (McpSchema.Tool tool : tools) {
            System.out.println("Tool name : "+tool.name() +","+tool.description());
        }
        /**调用基于经纬度获取天气的Tool并按到content*/
        McpSchema.CallToolResult weatherCallResult = mcpClient.callTool(
                new McpSchema.CallToolRequest("getWeatherForecastByLocation",
                        Map.of("latitude", "47.6062", "longitude", "-122.3321")));
        Boolean error = weatherCallResult.isError();
        if (!error){
            List<McpSchema.Content> content = weatherCallResult.content();
            for (McpSchema.Content result : content) {
                System.out.println("getWeatherForecastByLocation content: "+result.toString());
            }
        }
        McpSchema.CallToolResult alertCallResult = mcpClient.callTool(
                new McpSchema.CallToolRequest("getAlerts", Map.of("state", "NY")));
        error = weatherCallResult.isError();
        if (!error){
            List<McpSchema.Content> content = alertCallResult.content();
            for (McpSchema.Content result : content) {
                System.out.println("getAlerts content: "+result.toString());
            }
        }
        mcpClient.closeGracefully();
    }
}
