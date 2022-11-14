package com.alexzhli.bilibili.domain.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

// 规定政策阶段
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Component
public @interface DataLimited {
}
