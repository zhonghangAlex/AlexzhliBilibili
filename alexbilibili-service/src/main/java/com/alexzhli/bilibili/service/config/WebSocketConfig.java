package com.alexzhli.bilibili.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        // ServerEndpointExporter是服务器端点的导出者，将websocket endpoint进行发现导出
        return new ServerEndpointExporter();
    }
}
