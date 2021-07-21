package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.TblUser;
import com.bjpowernode.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 张大帅
 * 2021/6/22
 */
public interface UserService {
    //根据id获取用户对象
    public TblUser selectUserById(String id);
    //根据用户名和密码查询用户
    User queryUserByLoginActAndPwd(Map<String,Object>map);
    //查询所有的用户
    List<User>queryAllUser();
}
