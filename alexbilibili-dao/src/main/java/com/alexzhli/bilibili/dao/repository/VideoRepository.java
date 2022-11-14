package com.alexzhli.bilibili.dao.repository;

import com.alexzhli.bilibili.domain.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface VideoRepository extends ElasticsearchRepository<Video, Long> {
    // repository通过层层继承，可以直接使用很多CURD方法，参数是操作的数据类型和主键的类型

    // find by title like  spring data 提供的能力
    Video findByTitleLike(String keyword);
}
