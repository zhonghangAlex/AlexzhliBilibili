package com.alexzhli.bilibili.service.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import sun.awt.geom.AreaOp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 注解：在sring-boot中用来标识是内部配置文件的，这个注释会将这个配置自动加载到上下文中使其生效
@Configuration
public class JsonHttpMessageConverterConfig {

    // 循环引用示例
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        Object o = new Object();
        // 重复添加
        list.add(o);
        list.add(o);
        System.out.println(list.size());
        System.out.println(JSONObject.toJSONString(list));
        System.out.println(JSONObject.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect));
        // 2
        // [{},{"$ref":"$[0]"}]
        // [{},{}]
    }

    // 注解：也是sring-boot中常用的注入类
    @Bean
    // 注解：标识优先级高
    @Primary
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // 这里引入了阿里巴巴业界效率最高的json处理包：fast-json
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // json中的日期格式配置
        fastJsonConfig.setDateFormat("yyyy-MM-DD HH:mm:ss");
        // json序列化配置
        fastJsonConfig.setSerializerFeatures(
                // json格式化
                SerializerFeature.PrettyFormat,
                // 如果输出的json数据中有的值为null或者不存在，正常系统下会直接去掉，但是前端需要看到，所以这个注解就可以使其转化为空字符串
                SerializerFeature.WriteNullStringAsEmpty,
                // 同上，针对list
                SerializerFeature.WriteNullListAsEmpty,
                // 同上，针对map
                SerializerFeature.WriteMapNullValue,
                // 数据排序，把map相关的键值对进行排列，默认升序
                SerializerFeature.MapSortField,
                // 禁用了循环引用(非常重要)
                SerializerFeature.DisableCircularReferenceDetect
        );
        // 给fast-json实例传入配置
        fastConverter.setFastJsonConfig(fastJsonConfig);
        // 如果使用feign进行微服务间的接口条用，则需要加上该配置
        fastConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        return new HttpMessageConverters(fastConverter);
    }
}
