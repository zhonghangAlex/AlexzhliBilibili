package com.alexzhli.bilibili.api.aspect;

import com.alexzhli.bilibili.api.support.UserSupport;
import com.alexzhli.bilibili.domain.UserMoment;
import com.alexzhli.bilibili.domain.auth.UserRole;
import com.alexzhli.bilibili.domain.constant.AuthRoleConstant;
import com.alexzhli.bilibili.domain.exception.ConditionException;
import com.alexzhli.bilibili.service.UserRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// 优先级
@Order(1)
@Component
@Aspect
public class DataLimitedAspect {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserRoleService userRoleService;

    // 切面编程：切点标注,使用正则
    @Pointcut("@annotation(com.alexzhli.bilibili.domain.annotation.DataLimited)")
    public void check() {

    }

    // 切面编程：流程中的一个节点
    @Before("check()")
    // joinPoint 切入时候的参数
    public void doBefore(JoinPoint joinPoint) {
        Long userId = userSupport.getCurrentUserId();
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        Set<String> roleCodeSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof UserMoment) {
                UserMoment userMoment = (UserMoment) arg;
                String type = userMoment.getType();
                if (roleCodeSet.contains(AuthRoleConstant.ROLE_LV0) && "0".equals(type)) {
                    throw new ConditionException("参数异常！");
                }
            }
        }
    }

}
