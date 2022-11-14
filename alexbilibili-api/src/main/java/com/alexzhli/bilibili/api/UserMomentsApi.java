package com.alexzhli.bilibili.api;

import com.alexzhli.bilibili.api.support.UserSupport;
import com.alexzhli.bilibili.domain.JsonResponse;
import com.alexzhli.bilibili.domain.UserMoment;
import com.alexzhli.bilibili.domain.annotation.ApiLimitedRole;
import com.alexzhli.bilibili.domain.auth.AuthRole;
import com.alexzhli.bilibili.domain.constant.AuthRoleConstant;
import com.alexzhli.bilibili.service.UserMomentsService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserMomentsApi {

    @Autowired
    private UserMomentsService userMomentsService;

    @Autowired
    private UserSupport userSupport;

    // 新建一条用户动态
    // api权限注解
    @ApiLimitedRole(limitedRoleCodeList = {AuthRoleConstant.ROLE_LV0})
    // 数据权限注解

    @PostMapping("/user-moments")
    public JsonResponse<String> addUserMoments(@RequestBody UserMoment userMoment) throws Exception {
        Long userId = userSupport.getCurrentUserId();
        userMoment.setUserId(userId);
        userMomentsService.addUserMoments(userMoment);
        return JsonResponse.success();
    }

    // 查询用户订阅的动态
    @GetMapping("/user-subscribed-moments")
    public JsonResponse<List<UserMoment>> getUserSubscribedMoments() {
        Long userId = userSupport.getCurrentUserId();
        List<UserMoment> result = userMomentsService.getUserSubscribedMoments(userId);
        return new JsonResponse<>(result);
    }

}
