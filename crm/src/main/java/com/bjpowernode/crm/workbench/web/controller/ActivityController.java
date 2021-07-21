package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.mapper.ActivityRemarkMapper;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 张凯
 * 2021/6/28
 */
@Controller
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @Autowired
    UserService userService;

    @Autowired
    ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(Model model){
       List<User>userList= userService.queryAllUser();
       model.addAttribute("userList",userList);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/queryActivityForPageByCondition.do")
    public @ResponseBody Object queryActivityForPageByCondition(int pageNo,int pageSize,String name,String owner,String startDate,String endDate){
        Map<String,Object>map = new HashMap<>();
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        List<Activity>activityList = activityService.queryActivityForPageByCondition(map);
        //得到总条数
        long totalRows = activityService.queryCountOfActivityByCondition(map);
        //因为要返回两个对象,所以new一个map对象
        Map<String,Object>retMap = new HashMap<>();
        retMap.put("activityList",activityList);
        retMap.put("totalRows",totalRows);
        return retMap;
    }

    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    public @ResponseBody Object saveCreateActivity(Activity activity, Model model, HttpSession session){
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formatDateTime(new Date()));
        activity.setCreateBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        int num = activityService.saveCreateActivity(activity);
        if (num>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else{
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("保存失败");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/editActivity.do")
    public @ResponseBody Object editActivity(String id){
        Activity activity = activityService.queryActivityById(id);
        return activity;
    }

    @RequestMapping("/workbench/activity/saveEditActivity.do")
    public @ResponseBody Object saveEditActivity(Activity activity,HttpSession session){
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        activity.setEditBy(user.getId());
        activity.setEditTime(DateUtils.formatDateTime(new Date()));
        ReturnObject returnObject = new ReturnObject();
        int num = activityService.saveEditActivity(activity);
        if (num>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("更新失败");
        }
        return returnObject;
    }

    @RequestMapping("workbench/activity/deleteActivityByIds.do")
    public @ResponseBody Object deleteActivityByIds(String[] id){
        ReturnObject returnObject = new ReturnObject();
        int num = activityService.deleteActivityByIds(id);
        if (num>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else{
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("删除失败");
        }
        return returnObject;
    }

    /*//进入详情页面
    @RequestMapping("/workbench/activity/detail.do")
    public String detail(String id,Model model){
        Activity activity = activityService.queryActivityForDetailById(id);
        List<ActivityRemark>remarkList = activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
        model.addAttribute("activity",activity);
        model.addAttribute("remarkList",remarkList);
        return "workbench/activity/detail";
    }*/
    @RequestMapping("/workbench/activity/detailActivity.do")
    public String detailActivity(String id,Model model){
        //调用service层方法，查询数据
        Activity activity=activityService.queryActivityForDetailById(id);
        List<ActivityRemark> remarkList=activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
        //把数据保存到request中
        model.addAttribute("activity",activity);
        model.addAttribute("remarkList",remarkList);
        //请求转发
        return "workbench/activity/detail";
    }

    //批量导出
    @RequestMapping("/workbench/activity/exportAllActivity.do")
    public void exportAllActivity(HttpServletRequest request, HttpServletResponse response)throws Exception{
        //从数据库获取所有的市场活动
        List<Activity>activityList = activityService.queryAllActivityForDetail();
        //创建Excel
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("市场活动列表");
        //第一行
        HSSFRow row = sheet.createRow(0);
        //第一列
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        //第二列
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        //第三列
        cell = row.createCell(2);
        cell.setCellValue("名称");
        //第四列
        cell = row.createCell(3);
        cell.setCellValue("开始日期");
        //第五列
        cell = row.createCell(4);
        cell.setCellValue("结束日期");
        //第六列
        cell = row.createCell(5);
        cell.setCellValue("成本");
        //第七列
        cell = row.createCell(6);
        cell.setCellValue("喵叔");
        //样式对象
        HSSFCellStyle style = wb.createCellStyle();
        //居中对齐
        style.setAlignment(HorizontalAlignment.CENTER);

        //遍历集合,将集合中的每一个市场活动对象取出放到Excel中的每一行
        if (activityList!=null){
            Activity activity = null;
            //从集合中第0个元素开始
            for (int i = 0;i<activityList.size();i++){
                activity = activityList.get(i);
                //因为要从第二行开始,所以+1
                row = sheet.createRow(i+1);
                //第二行第一列
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                //第二行第二列
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                //第二行第三列
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                //第二行第四列
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                //第二行第五列
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                //第二行第六列
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                //第二行第七列
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
            }
        }
        //下载  设置响应类型,浏览器默认服务器返回html  文件   流
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition","attachment;filename=activity.xls");

        OutputStream os = response.getOutputStream();
        //输出的位置是浏览器决定的
        wb.write(os);
        //清空缓存
        os.flush();
        //关闭流通道
        wb.close();
        os.close();
    }

    @RequestMapping("/workbench/activity/fileUpload.do")
    public @ResponseBody Object fileUpload(String username, MultipartFile myFile)throws Exception{
        //System.out.println(myFile.getName());
        String filename = myFile.getOriginalFilename();
        File file = new File("d:\\testDir",filename);
        //将文件保存到指定的目录
        myFile.transferTo(file);

        ReturnObject returnObject = new ReturnObject();
        returnObject.setMessage("上传成功");
        return returnObject;
    }

    @RequestMapping("workbench/activity/importActivity.do")
    public @ResponseBody Object importActivity(MultipartFile activityFile,HttpSession session,String username)throws Exception {
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        Map<String, Object> retMap = new HashMap<>();
        List<Activity> activityList = new ArrayList<>();
        try {

            //把上传的Excel文件转成输入流
            InputStream is = activityFile.getInputStream();
            HSSFWorkbook wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = null;
            //读取行  不读取标题,从第二行开始读取
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                //创建市场活动对象
                activity = new Activity();
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateBy(user.getId());
                activity.setCreateTime(DateUtils.formatDateTime(new Date()));
                //读取列
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    String cellValue = getCellValue(cell);
                    if (j == 0) {
                        activity.setName(cellValue);
                    } else if (j == 1) {
                        activity.setStartDate(cellValue);
                    } else if (j == 2) {
                        activity.setEndDate(cellValue);
                    } else if (j == 3) {
                        activity.setCost(cellValue);
                    } else if (j == 4) {
                        activity.setDescription(cellValue);
                    }
                }
                //把市场活动对象放在市场活动的集合中
                activityList.add(activity);
            }
            int num = activityService.saveCreatActivityByList(activityList);
            retMap.put("code", Contants.RETURN_OBJECT_CODE_SUCCESS);
            retMap.put("count", num);
        } catch (IOException e) {
            e.printStackTrace();
            retMap.put("code",Contants.RETURN_OBJECT_CODE_FAILO);
            retMap.put("massage","导入文件失败");
        }
        return retMap;
    }
    public static String getCellValue(HSSFCell cell) {
        String str = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                str = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                str = cell.getBooleanCellValue() + "";
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                str = cell.getNumericCellValue() + "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                str = cell.getCellFormula() + "";
                break;
            default:
                str = "";
        }
        return str;
    }
}