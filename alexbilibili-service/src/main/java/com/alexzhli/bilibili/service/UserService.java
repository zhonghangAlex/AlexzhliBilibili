package com.alexzhli.bilibili.service;

import com.alexzhli.bilibili.dao.UserDao;
import com.alexzhli.bilibili.domain.PageResult;
import com.alexzhli.bilibili.domain.RefreshTokenDetail;
import com.alexzhli.bilibili.domain.User;
import com.alexzhli.bilibili.domain.UserInfo;
import com.alexzhli.bilibili.domain.constant.UserConstant;
import com.alexzhli.bilibili.domain.exception.ConditionException;
import com.alexzhli.bilibili.service.util.MD5Util;
import com.alexzhli.bilibili.service.util.RSAUtil;
import com.alexzhli.bilibili.service.util.TokenUtil;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

// 表示服务层的类，在sp构建的时候回自动注入
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAuthService userAuthService;

    public void addUser(User user) {
        // 1. 验证逻辑
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            // 如果手机号如果为空或者为null不正确的话，就要提示前端
            throw new ConditionException("手机号不能为空！");
        }
        User dbUser = this.getUserByPhone(phone);
        if (dbUser != null) {
            // 手机号已经存在了
            throw new ConditionException("该手机号已经注册了！");
        }
        // 2. 手机号注册逻辑
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        // 数据库中存储的加密密码
        String password = user.getPassword();
        // 明文密码
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解析失败！");
        }
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        user.setSalt(salt);
        user.setPassword(md5Password);
        user.setCreateTime(now);
//        user.setUpdateTime(now);
        userDao.addUser(user);
        // 3. 添加用户信息逻辑
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_MALE);
        userInfo.setCreateTime(now);
        userDao.addUserInfo(userInfo);
        // 4. 添加用户默认权限角色
        userAuthService.addUserDefaultRole(user.getId());
    }

    // 检查当前是否已经存在了这个手机号
    public User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    // 根据id获取user信息
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    // 登录
    public String login(User user) throws Exception {
        // 1. 用户账号校验
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            // 如果手机号如果为空或者为null不正确的话，就要提示前端
            throw new ConditionException("手机号不能为空！");
        }
        User dbUser = this.getUserByPhone(phone);
        if (dbUser == null) {
            // 手机号不存在
            throw new ConditionException("当前用户不存在！");
        }
        // 2. 密码校验
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败！");
        }
        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        if (!md5Password.equals(dbUser.getPassword())) {
            // 密码不一致
            throw new ConditionException("密码错误！");
        }
        return TokenUtil.generateToken(dbUser.getId());
    }

    // 通过用户ID查询用户信息
    public User getUserInfo(Long userId) {
        // 拿到原始的user
        User user = userDao.getUserById(userId);
        // 拿到详情的userInfo
        UserInfo userInfo = userDao.getUserInfoByUserId(userId);
        // 整合两者的信息
        user.setUserInfo(userInfo);
        return user;
    }

    // 更新user的信息
    public void updateUser(User user) throws Exception {
        Long id = user.getId();
        User dbUser = userDao.getUserById(id);
        // 用户合法性校验
        if (dbUser == null) {
            throw new ConditionException("用户不存在！");
        }
        // 密码
        if (!StringUtils.isNullOrEmpty(user.getPassword())) {
            String rawPassword = RSAUtil.decrypt(user.getPassword());
            String md5Password = MD5Util.sign(rawPassword, dbUser.getSalt(), "UTF-8");
            user.setPassword(md5Password);
        }
        // 用户修改时间
        user.setUpdateTime(new Date());
        userDao.updateUser(user);
    }

    // 更新userinfo信息
    public void updateUserInfos(UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        userDao.updateUserInfo(userInfo);
    }

    // 更具userids批量查找用户信息
    public List<UserInfo> getUserInfoByUserIds(Set<Long> userIdList) {
        return userDao.getUserInfoByUserIds(userIdList);
    }

    // 分页查询用户数据
    public PageResult<UserInfo> pageListUserInfos(JSONObject params) {
        Integer pageNumber = params.getInteger("pageNumber");
        Integer pageSize = params.getInteger("pageSize");
        // 起始数据
        params.put("start", (pageNumber - 1) * pageSize);
        // 单次获取数据
        params.put("limit", pageSize);
        // 查询总数
        Integer total = userDao.pageCountUserInfos(params);
        List<UserInfo> userList = new ArrayList<>();
        if (total > 0) {
            userList = userDao.pageListUserInfos(params);
        }
        return new PageResult<>(total, userList);
    }

    // 双token登录
    public Map<String, Object> loginForDts(User user) throws Exception {
        // 1. 用户账号校验
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            // 如果手机号如果为空或者为null不正确的话，就要提示前端
            throw new ConditionException("手机号不能为空！");
        }
        User dbUser = this.getUserByPhone(phone);
        if (dbUser == null) {
            // 手机号不存在
            throw new ConditionException("当前用户不存在！");
        }
        // 2. 密码校验
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败！");
        }
        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        if (!md5Password.equals(dbUser.getPassword())) {
            // 密码不一致
            throw new ConditionException("密码错误！");
        }
        Long userId = dbUser.getId();
        // 创建两种token
        String accessToken = TokenUtil.generateToken(userId);
        String refreshToken = TokenUtil.generateRefreshToken(userId);
        // 保存refreshToken到数据库
        userDao.deleteRefreshTokenByUserId(userId);
        userDao.addRefreshToken(refreshToken, userId, new Date());
        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);
        return result;
    }

    // 双令牌登出
    public void logout(String refreshToken, Long userId) {
        userDao.deleteRefreshToken(refreshToken, userId);
    }

    // 更新accessToken
    public String refreshAccessToken(String refreshToken) throws Exception {
        RefreshTokenDetail refreshTokenDetail = userDao.getRefreshTokenDetail(refreshToken);
        if (refreshTokenDetail == null) {
            throw new ConditionException("555", "token过期！");
        }
        Long userId = refreshTokenDetail.getUserId();
        return TokenUtil.generateToken(userId);
    }

    // 批量获取用户信息
    public List<UserInfo> batchGetUserInfoByUserIds(Set<Long> userIdList) {
        return userDao.batchGetUserInfoByUserIds(userIdList);
    }

    public String getRefreshTokenByUserId(Long userId) {
        return userDao.getRefreshTokenByUserId(userId);
    }
}
