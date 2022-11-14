package com.alexzhli.bilibili.service;

import com.alexzhli.bilibili.dao.FollowingGroupDao;
import com.alexzhli.bilibili.domain.FollowingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowingGroupService {

    @Autowired
    private FollowingGroupDao followingGroupDao;

    // 通过type查找following group
    public FollowingGroup getByType(String type) {
        return followingGroupDao.getByType(type);
    }

    // 通过id查找following group
    public FollowingGroup getById(Long id) {
        return followingGroupDao.getById(id);
    }

    // 通过userId查找following group
    public List<FollowingGroup> getByUserId(Long userId) {
        return followingGroupDao.getByUserId(userId);
    }

    // 添加一个关注分组
    public void addFollowingGroup(FollowingGroup followingGroup) {
        followingGroupDao.addFollowingGroup(followingGroup);
    }

    // 获取关注分组
    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return followingGroupDao.getUserFollowingGroups(userId);
    }
}
