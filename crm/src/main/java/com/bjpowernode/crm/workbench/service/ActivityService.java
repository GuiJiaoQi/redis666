package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * 张凯
 * 2021/6/28
 */
public interface ActivityService {
    //创建新的市场活动
    int saveCreateActivity(Activity activity);
    //查询市场活动列表按多条件查询和分页
    List<Activity> queryActivityForPageByCondition(Map<String,Object> map);
    //根据条件查询市场活动的数量
    long queryCountOfActivityByCondition(Map<String,Object>map);
    //根据id查询市场活动
    Activity queryActivityById(String id);
    //保存修改的市场活动
    int saveEditActivity(Activity activity);
    //根据ids进行批量删除市场活动
    int deleteActivityByIds(String[] ids);
    //导出时要抓住市场表的所有数据
    List<Activity>queryActivityForDetailByIds(String[] ids);
    //导入时要把excel表中的多个市场活动导入到数据库中的市场活动表
    int saveCreatActivityByList(List<Activity>activityList);
    //进入详情页面
    Activity queryActivityForDetailById(String id);
    //在其他模块中需要市场模块的支持
    List<Activity>queryAllActivityForDetail();
    //根据市场活动名称查询所有的市场活动
    List<Activity>queryActivityForDetailByName(String name);
    //
    List<Activity>queryActivityForDetailByClueId(String clueId);
    //与线索无关的市场活动
    List queryActivityNoBoundById(Map<String,Object>map);
}
