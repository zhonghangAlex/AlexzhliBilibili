package com.alexzhli.bilibili.api;

import com.alexzhli.bilibili.api.support.UserSupport;
import com.alexzhli.bilibili.domain.JsonResponse;
import com.alexzhli.bilibili.domain.auth.UserAuthorities;
import com.alexzhli.bilibili.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthApi {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserAuthService userAuthService;

    // 获取用户权限的接口
    @GetMapping("/user-authorities")
    public JsonResponse<UserAuthorities> getUserAuthorities() {
        Long userId = userSupport.getCurrentUserId();
        UserAuthorities userAuthorities = userAuthService.getUserAuthorities(userId);
        return new JsonResponse<>(userAuthorities);
    }
}
