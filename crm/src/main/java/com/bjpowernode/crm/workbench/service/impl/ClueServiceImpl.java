package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.mapper.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.sun.tools.doclets.internal.toolkit.util.ClassUseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 张凯
 * 2021/7/3
 */
@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;

    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;

    @Autowired
    private TranMapper tranMapper;

    @Autowired
    private TranRemarkMapper tranRemarkMapper;

    @Override
    public int saveCreate(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public Clue queryClueForDetailById(String id) {
        return clueMapper.selectClueForDetailById(id);
    }

    @Override
    public void saveConvert(Map<String, Object> map) {
        User user = (User) map.get("sessionUser");
        String isCreateTran = (String) map.get("isCreateTran");

        //1.
        String clueId = (String) map.get("clueId");
        Clue clue = clueMapper.selectClueById(clueId);
        //2.
        Customer customer = new Customer();
        customer.setId(UUIDUtils.getUUID());
        customer.setOwner(user.getId());
        customer.setName(clue.getCompany());
        customer.setWebsite(clue.getWebsite());
        customer.setPhone(clue.getPhone());
        customer.setCreateBy(clue.getCreateBy());
        customer.setCreateTime(DateUtils.formatDateTime(new Date()));
        customer.setContactSummary(clue.getContactSummary());
        customer.setAddress(clue.getAddress());
        customer.setDescription(clue.getDescription());
        customer.setNextContactTime(clue.getNextContactTime());

        customerMapper.insertCustomer(customer);
        //3.
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtils.getUUID());
        contacts.setOwner(user.getId());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullName(clue.getFullName());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setCreateBy(clue.getCreateBy());
        contacts.setCreateTime(DateUtils.formatDateTime(new Date()));
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAddress(clue.getAddress());
        contacts.setDescription(clue.getDescription());
        contacts.setNextContactTime(clue.getNextContactTime());

        contactsMapper.insertContacts(contacts);

        //4.
        List<ClueRemark>remarkList = clueRemarkMapper.selectClueRemarkByClueId(clueId);
        if (remarkList != null && remarkList.size()>=0){
            CustomerRemark cur = null;
            //联系人的备注
            ContactsRemark cor = null;
            //用集合存放客户备注对象  和联系人备注对象
            List<CustomerRemark>curList = new ArrayList<>();
            List<ContactsRemark>corList = new ArrayList<>();
            //遍历循环获取线索备注
            for (ClueRemark cr : remarkList){
                //新客户备注对象
                cur = new CustomerRemark();
                cur.setId(UUIDUtils.getUUID());
                cur.setNoteContent(cr.getNoteContent());
                cur.setCreateBy(cr.getCreateBy());
                cur.setCreateTime(cr.getCreateTime());
                cur.setCustomerId(customer.getId());

                curList.add(cur);
                //新联系人备注对象
                cor = new ContactsRemark();
                cor.setId(UUIDUtils.getUUID());
                cor.setNoteContent(cr.getNoteContent());
                cor.setCreateBy(cr.getCreateBy());
                cor.setCreateTime(cr.getCreateTime());
                cor.setContactsId(customer.getId());

                corList.add(cor);
            }
            //批量插入
            customerRemarkMapper.insertCustomerRemarkByList(curList);
            contactsRemarkMapper.insertContactsRemarkByList(corList);
        }

        //5.
        List<ClueActivityRelation> carList = clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);
        if (carList != null && carList.size() > 0){
            //联系人和市场活动的对象
            ContactsActivityRelation coar = null;
            List<ContactsActivityRelation>coarList = new ArrayList<>();
            for (ClueActivityRelation car : carList){
                coar = new ContactsActivityRelation();
                coar.setId(UUIDUtils.getUUID());
                coar.setContactsId(contacts.getId());
                coar.setActivityId(car.getActivityId());
                coarList.add(coar);
            }
            contactsActivityRelationMapper.insertContactsActivityRelationByList(coarList);
        }
        //6.
        if ("true".equals(isCreateTran)){
            Tran tran = new Tran();
            tran.setId(UUIDUtils.getUUID());
            tran.setOwner(user.getId());
            tran.setMoney((String) map.get("amountOfMoney"));
            tran.setName((String) map.get("tradeName"));
            tran.setExpectedDate((String) map.get("expectedClosingDate"));
            tran.setCustomerId(customer.getId());
            tran.setStage((String) map.get("stage"));

            //执行插入
            tranMapper.insertTran(tran);

            if (remarkList != null && remarkList.size() > 0){
                TranRemark tr = null;
                List<TranRemark> tranList = new ArrayList<>();
                for (ClueRemark cr : remarkList){
                    tr = new TranRemark();
                    tr.setId(UUIDUtils.getUUID());
                    tr.setNoteContent(cr.getNoteContent());
                    tr.setCreateBy(cr.getCreateBy());
                    tr.setCreateTime(cr.getCreateTime());
                    tr.setTranId(cr.getId());

                    tranList.add(tr);
                }
                tranRemarkMapper.insertTranRemarkByList(tranList);
            }
        }

        //8.
        clueRemarkMapper.deleteClueRemarkByClueId(clueId);
        //9.
        clueActivityRelationMapper.deleteClueActivityRelationByClueId(clueId);
        //10.
        clueMapper.deleteClueById(clueId);
    }
}
