package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 张凯
 * 2021/6/28
 */
@Service
public class ActivityServiceImpl implements ActivityService{
    @Autowired
    private ActivityMapper mapper;

    @Override
    public List<Activity> queryActivityForDetailByClueId(String clueId) {
        return mapper.selectActivityForDetailByClueId(clueId);
    }

    @Override
    public List queryActivityNoBoundById(Map<String, Object> map) {
        List<Activity>aList = mapper.selectActivityNoBoundById(map);
        return aList;
    }

    @Override
    public int saveCreateActivity(Activity activity) {
        return mapper.insertActivity(activity);
    }

    @Override
    public List<Activity> queryActivityForPageByCondition(Map<String, Object> map) {
        return mapper.selectActivityForPageByCondition(map);
    }

    @Override
    public long queryCountOfActivityByCondition(Map<String, Object> map) {
        return mapper.selectCountOfActivityByCondition(map);
    }

    @Override
    public Activity queryActivityById(String id) {
        return mapper.selectActivityById(id);
    }

    @Override
    public int saveEditActivity(Activity activity) {
        return mapper.updateActivity(activity);
    }

    @Override
    public int deleteActivityByIds(String[] ids) {
        return mapper.deleteActivityByIds(ids);
    }

    @Override
    public List<Activity> queryActivityForDetailByIds(String[] ids) {
        return mapper.selectActivityForDetailByIds(ids);
    }

    @Override
    public int saveCreatActivityByList(List<Activity> activityList) {
        return mapper.insertActivityByList(activityList);
    }

    @Override
    public Activity queryActivityForDetailById(String id) {
        return mapper.selectActivityById(id);
    }

    @Override
    public List<Activity> queryAllActivityForDetail() {
        return mapper.selectAllActivityForDetail();
    }

    @Override
    public List<Activity> queryActivityForDetailByName(String name) {
        return mapper.selectActivityForDetailByName(name);
    }
}
