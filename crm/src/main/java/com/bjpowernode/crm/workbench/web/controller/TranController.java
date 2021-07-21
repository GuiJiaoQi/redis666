package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.DicValueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.domain.TranRemark;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranHistoryService;
import com.bjpowernode.crm.workbench.service.TranRemarkService;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * 张凯
 * 2021/7/7
 */
@Controller
public class TranController {

        @Autowired
        private UserService userService;

        @Autowired
        private DicValueService dicValueService;

        @Autowired
        private CustomerService customerService;

       @Autowired
       private TranService tranService;

       @Autowired
       private TranRemarkService tranRemarkService;

       @Autowired
       private TranHistoryService tranHistoryService;


    @RequestMapping("/workbench/transaction/typeahead.do")
    public @ResponseBody Object typeahead(String customerName){
        List<Customer>customerList = new ArrayList<>();
        Customer customer = new Customer();

        customer.setId("001");
        customer.setName("动力节俭");
        customerList.add(customer);

        customer = new Customer();
        customer.setId("002");
        customer.setName("季节跳动");
        customerList.add(customer);

        customer = new Customer();
        customer.setId("003");
        customer.setName("国庆节");
        customerList.add(customer);

        return customerList;
    }

    @RequestMapping("/workbench/transaction/index.do")
    public String index(Model model){

        return "workbench/transaction/index";
    }

    @RequestMapping("/workbench/transaction/createTran.do")
    public String createTran(Model model){
        List<User>userList = userService.queryAllUser();
        //阶段
        List<DicValue>stageList = dicValueService.queryDicValueByTypeCode("stage");
        //来源
        List<DicValue>sourceList = dicValueService.queryDicValueByTypeCode("source");
        //类型
        List<DicValue>transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");

        model.addAttribute("userList",userList);
        model.addAttribute("stageList",stageList);
        model.addAttribute("sourceList",sourceList);
        model.addAttribute("transactionTypeList",transactionTypeList);
        return "workbench/transaction/save";
    }

    @RequestMapping("/workbench/transaction/queryCustomerByName.do")
    public @ResponseBody Object queryCustomerByName(String customerName){
        List<Customer>customerList = customerService.queryCustomerByName(customerName);
        return customerList;
    }

    @RequestMapping("/workbench/transaction/getPossibilityByStageValue.do")
    public @ResponseBody Object getPossibilityByStageValue(String stageValue){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("possibility");
        String possibility = resourceBundle.getString(stageValue);
        return possibility;
    }

    @RequestMapping("workbench/transaction/saveCreateTran.do")
    public @ResponseBody Object saveCreateTran(Tran tran, String customerName, HttpSession session){
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        tran.setId(UUIDUtils.getUUID());
        tran.setCreateBy(user.getId());
        tran.setCreateTime(DateUtils.formatDateTime(new Date()));

        Map<String,Object>map = new HashMap<>();
        map.put("tran",tran);
        map.put("customerName",customerName);
        map.put("sessionUser",user);

        ReturnObject returnObject = new ReturnObject();
        try {
            //调用交易的业务层,完成创建交易的过程
            tranService.saveCreateTran(map);
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAILO);
            returnObject.setMessage("创建交易失败");
        }
        return returnObject;
    }

    //交易详情
    @RequestMapping("/workbench/transaction/detailTran.do")
    public String detail(String id,Model model){
        /*Tran tran = tranService.queryTranForDetailById(id);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("possibility");
        String possiblity = resourceBundle.getString(tran.getStage());
        tran.setPossibility(possiblity);
        //交易备注
        List<TranRemark>tranRemarkList = tranRemarkService.queryTranRemarkForDetailByTranId(id);
        //获取阶段历史
        List<TranHistory>tranHistoryList = tranHistoryService.queryTranHistoryForDetailByTranId(id);
        //交易阶段
        List<DicValue>stageList = dicValueService.queryDicValueByTypeCode("stage");
        model.addAttribute("tran",tran);
        model.addAttribute("tranRemarkList",tranRemarkList);
        model.addAttribute("tranHistoryList",tranHistoryList);
        model.addAttribute("stageList",stageList);

        //最后的成交阶段
        TranHistory tranHistory = tranHistoryList.get(tranHistoryList.size()-1);
        model.addAttribute("theOrderNo",tranHistory.getOrderNo());
        return "workbench/transaction/detail";*/

        //交易的详情
        Tran tran=tranService.queryTranForDetailById(id);
        ResourceBundle bundle=ResourceBundle.getBundle("possibility");
        String possiblity=bundle.getString(tran.getStage());
        tran.setPossibility(possiblity);
        //交易备注
        List<TranRemark> tranRemarkList=tranRemarkService.queryTranRemarkForDetailByTranId(id);
        //阶段历史
        List<TranHistory> tranHistoryList=tranHistoryService.queryTranHistoryForDetailByTranId(id);
        //交易阶段
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");


        model.addAttribute("tran",tran);
        model.addAttribute("tranRemarkList",tranRemarkList);
        model.addAttribute("tranHistoryList",tranHistoryList);
        model.addAttribute("stageList",stageList);

        //最后成交的阶段
        // TranHistory tranHistory=null;
        TranHistory tranHistory=tranHistoryList.get(tranHistoryList.size()-1);
        model.addAttribute("theOrderNo",tranHistory.getOrderNo());

        return "workbench/transaction/detail";
    }
}
