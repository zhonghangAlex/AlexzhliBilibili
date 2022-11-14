package com.alexzhli.bilibili.api;

import com.alexzhli.bilibili.api.support.UserSupport;
import com.alexzhli.bilibili.domain.FollowingGroup;
import com.alexzhli.bilibili.domain.JsonResponse;
import com.alexzhli.bilibili.domain.User;
import com.alexzhli.bilibili.domain.UserFollowing;
import com.alexzhli.bilibili.domain.exception.ConditionException;
import com.alexzhli.bilibili.service.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserFollowingApi {

    @Autowired
    private UserFollowingService userFollowingService;

    @Autowired
    private UserSupport userSupport;

    // 添加粉丝
    @PostMapping("/user-followings")
    public JsonResponse<String> addUserFollowings(@RequestBody UserFollowing userFollowing) {
        Long userId = userSupport.getCurrentUserId();
        if (userFollowing.getUserId() == null || userFollowing.getFollowingId() == null || userFollowing.getGroupId() == null) {
            throw new ConditionException("请求参数错误");
        }
        if (userFollowing.getFollowingId() == userId) {
            throw new ConditionException("不可以关注自己");
        }
        if (userId != userFollowing.getUserId()) {
            throw new ConditionException("请求参数错误，只可以对自己的信息进行操作");
        }
        userFollowingService.addUserFollowings(userFollowing);
        return JsonResponse.success();
    }

    // 获取用户关注列表
    @GetMapping("/user-followings")
    public JsonResponse<List<FollowingGroup>> getUserFollowings() {
        Long userId = userSupport.getCurrentUserId();
        List<FollowingGroup> result = userFollowingService.getUserFollowings(userId);
        return new JsonResponse<>(result);
    }

    // 获取粉丝列表
    @GetMapping("/user-fans")
    public JsonResponse<List<UserFollowing>> getUserFans() {
        Long userId = userSupport.getCurrentUserId();
        List<UserFollowing> result = userFollowingService.getUserFans(userId);
        return new JsonResponse<>(result);
    }

    // 新建用户关注分组
    @PostMapping("/user-following-groups")
    public JsonResponse<Long> addUserFollowingGroups(@RequestBody FollowingGroup followingGroup) {
        Long userId = userSupport.getCurrentUserId();
        followingGroup.setUserId(userId);
        Long groupId = userFollowingService.addUserFollowingGroups(followingGroup);
        return new JsonResponse<>(groupId);
    }

    // 获取用户关注分组
    @GetMapping("/user-following-groups")
    public JsonResponse<List<FollowingGroup>> getUserFollowingGroups() {
        Long userId = userSupport.getCurrentUserId();
        List<FollowingGroup> list = userFollowingService.getUserFollowingGroups(userId);
        return new JsonResponse<>(list);
    }
}
