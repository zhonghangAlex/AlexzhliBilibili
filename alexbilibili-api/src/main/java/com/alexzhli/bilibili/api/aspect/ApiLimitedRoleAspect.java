package com.alexzhli.bilibili.api.aspect;

import com.alexzhli.bilibili.api.support.UserSupport;
import com.alexzhli.bilibili.domain.annotation.ApiLimitedRole;
import com.alexzhli.bilibili.domain.auth.UserRole;
import com.alexzhli.bilibili.domain.exception.ConditionException;
import com.alexzhli.bilibili.service.UserRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// 优先级
@Order(1)
@Component
@Aspect
public class ApiLimitedRoleAspect {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserRoleService userRoleService;

    // 切面编程：切点标注,使用正则
    @Pointcut("@annotation(com.alexzhli.bilibili.domain.annotation.ApiLimitedRole)")
    public void check() {

    }

    // 切面编程：流程中的一个节点
    @Before("check() && @annotation(apiLimitedRole)")
    // joinPoint 切入时候的参数
    public void doBefore(JoinPoint joinPoint, ApiLimitedRole apiLimitedRole) {
        Long userId = userSupport.getCurrentUserId();
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        String[] limitedRoleCodeList = apiLimitedRole.limitedRoleCodeList();
        Set<String> limitedRoleCodeSet = Arrays.stream(limitedRoleCodeList).collect(Collectors.toSet());
        Set<String> roleCodeSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        // set 取交集
        roleCodeSet.retainAll(limitedRoleCodeSet);
        // 如果存在限制重合
        if (roleCodeSet.size() > 0) {
            throw new ConditionException("用户限不足！");
        }
    }

}
