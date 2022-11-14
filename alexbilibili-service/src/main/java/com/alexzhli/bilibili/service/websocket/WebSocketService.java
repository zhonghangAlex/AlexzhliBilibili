package com.alexzhli.bilibili.service.websocket;

import com.alexzhli.bilibili.domain.Danmu;
import com.alexzhli.bilibili.domain.constant.UserMomentsConstant;
import com.alexzhli.bilibili.service.DanmuService;
import com.alexzhli.bilibili.service.util.RocketMQUtil;
import com.alexzhli.bilibili.service.util.TokenUtil;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
// 类似于api中写的接口类，指的就是如何找到这个websocket
@ServerEndpoint("/imserver/{token}")
public class WebSocketService {

    // 日志信息
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 记录socket在线人数，需要全局创建
    // AtomicInteger 是java提供的原子性操作的类，因为在高并发下线程不一定安全
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    // ConcurrentHashMap可以保证线程安全
    // 当每一个客户端进来一个，可以高效记录每一个客户端websocket服务
    // bean是单例的，但是传入的各个websocket是多个的，因此需要map存储
    // 例如使用autowire注入在多例下是行不通的
    public static final ConcurrentHashMap<String, WebSocketService> WEBSOCKET_MAP = new ConcurrentHashMap<>();

    // 服务端和客户端的会话，可以通过这个session进行消息推送
    // 从map中取到websocket service 然后再取到session就可以和客户端进行通信了，可以直接调用session相关的方法
    private Session session;

    private String sessionId;

    private Long userId;

    // 多例模式下使用Autowire进行注入，只会注入一次，会存在问题
    // 而Spring boot启动文件中的ApplicationContext上下文会存储所有的Bean
    // 全局上下文设置
    private static ApplicationContext APPLICATION_CONTEXT;

    // 在Spring boot的入口文件进行使用
    // 即可解决多例模式下Bean注入的问题
    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketService.APPLICATION_CONTEXT = applicationContext;
    }

    // 当连接成功的时候就需要调用由websocket提供的open标识
    @OnOpen
    public void openConnection(Session session, @PathParam("token") String token) {
        // 获取userId，但是支持游客
        try {
            this.userId = TokenUtil.verifyToken(token);
        } catch (Exception e) {}
        // 当连接建立之后，传入的session就可以赋值到本地的session变量中
        this.sessionId = session.getId();
        this.session = session;
        // 如果map中存储过这个session，就删掉，存储一个新的session，然后增加在线人数
        if (WEBSOCKET_MAP.containsKey(sessionId)) {
            // 会话被保存过，不是第一次连接
            WEBSOCKET_MAP.remove(sessionId);
            WEBSOCKET_MAP.put(sessionId, this);
        } else {
            // 会话是第一次连接的没有被保存过
            WEBSOCKET_MAP.put(sessionId, this);
            // 在线人数+1
            ONLINE_COUNT.getAndIncrement();
        }
        // 日志记录
        logger.info("用户连接成功：sessionId-" + sessionId + "，当前在线人数为：" + ONLINE_COUNT.get());
        // 通知成功
        try {
            // 请求成功返回0
            this.sendMessage("0");
        } catch (Exception e) {
            logger.error("连接异常");
        }
    }

    // 主动关闭，页面关闭，都会关闭socket，关闭时候就需要调用OnClose
    @OnClose
    public void closeConnection() {
        if (WEBSOCKET_MAP.containsKey(sessionId)) {
            // 要退出了，所以如果存在的话，就删掉，同时在线人数-1
            WEBSOCKET_MAP.remove(sessionId);
            ONLINE_COUNT.getAndDecrement();
        }
        // 日志记录
        logger.info("用户退出：sessionId-" + sessionId + "，当前在线人数为：" + ONLINE_COUNT.get());
    }

    // 当前端有消息进来的时候，需要调用OnMessage标识
    @OnMessage
    public void onMessage(String message) {
        logger.info("用户信息：sessionId-" + sessionId + "，报文：" + message);
        if (!StringUtils.isNullOrEmpty(message)) {
            try {
                // 群发消息
                for (Map.Entry<String, WebSocketService> entry : WEBSOCKET_MAP.entrySet()) {
                    WebSocketService webSocketService = entry.getValue();
                    // 优化：使用MQ
                    // 弹幕消息的生产者
                    DefaultMQProducer danmusProducer = (DefaultMQProducer) APPLICATION_CONTEXT.getBean("danmusProducer");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("message", message);
                    jsonObject.put("session", webSocketService.getSessionId());
                    // 创建一个message
                    Message msg = new Message(UserMomentsConstant.TOPIC_DANMUS, jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8));
                    RocketMQUtil.asyncSendMsg(danmusProducer, msg);
                }
                if (this.userId != null) {
                    // 保存弹幕到数据库
                    Danmu danmu = JSONObject.parseObject(message, Danmu.class);
                    danmu.setUserId(userId);
                    danmu.setCreateTime(new Date());
                    DanmuService danmuService = (DanmuService) APPLICATION_CONTEXT.getBean("danmuService");
                    // 优化：异步保存数据库
                    danmuService.asyncAddDanmu(danmu);
                    // 保存弹幕到redis
                    danmuService.addDanmusToRedis(danmu);
                }
            } catch (Exception e) {
                logger.error("弹幕接收出现问题");
                e.printStackTrace();
            }
        }
    }

    // 当有错误异常发生的时候，需要调用OnError标识
    @OnError
    public void onError(Throwable error) {

    }

    public void sendMessage(String message) throws Exception {
        // 通过这个方法可以发送一个字符串的值
        this.session.getBasicRemote().sendText(message);
    }

    // 统计在线人数
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate=5000)
    private void noticeOnlineCount() throws Exception {
        for(Map.Entry<String, WebSocketService> entry : WebSocketService.WEBSOCKET_MAP.entrySet()){
            WebSocketService webSocketService = entry.getValue();
            if(webSocketService.session.isOpen()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("onlineCount", ONLINE_COUNT.get());
                jsonObject.put("msg", "当前在线人数为" + ONLINE_COUNT.get());
                webSocketService.sendMessage(jsonObject.toJSONString());
            }
        }
    }

    public Session getSession() {
        return session;
    }

    public String getSessionId() {
        return sessionId;
    }
}
