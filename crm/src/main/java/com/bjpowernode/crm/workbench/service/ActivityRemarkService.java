package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 张大帅
 * 2021/7/1
 */
@Service
public interface ActivityRemarkService {

      //保存创建的市场活动备注

    int saveCreateActivityRemark(ActivityRemark remark);


      //根据id删除市场活动备注

    int deleteActivityRemarkById(String id);


      //保存修改的市场活动备注

    int saveEditActivityRemark(ActivityRemark remark);

    //
    List<ActivityRemark>queryActivityRemarkForDetailByActivityId(String activityId);
}
