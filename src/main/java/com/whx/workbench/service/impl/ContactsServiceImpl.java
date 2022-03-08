package com.whx.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whx.excep.CountWrongException;
import com.whx.settings.dao.UserDao;
import com.whx.settings.domain.User;
import com.whx.utils.DateTimeUtil;
import com.whx.utils.UUIDUtil;
import com.whx.vo.PageVo;
import com.whx.workbench.dao.*;
import com.whx.workbench.domain.*;
import com.whx.workbench.service.ContactsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class ContactsServiceImpl implements ContactsService {
    @Resource
    ContactsDao contactsDao;
    @Resource
    CustomerDao customerDao;
    @Resource
    ContactsActivityRelationDao contactsActivityRelationDao;
    @Resource
    ContactsRemarkDao contactsRemarkDao;
    @Resource
    UserDao userDao;
    @Resource
    ActivityDao activityDao;
    @Resource
    TranDao tranDao;


    @Override
    public PageInfo<Contacts> pageList(Contacts contacts, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Contacts> list=contactsDao.pageListInfo(contacts);
        PageInfo<Contacts> contactsInfo=new PageInfo<>(list);
        return contactsInfo;

    }

    @Override
    public List<String> getCustomerNames(String name) {
        List<String> customerNames=customerDao.queryCustomerNames(name);
        return customerNames;

    }

    @Override
    @Transactional
    public String save(Contacts contacts, String customerName,User user) {
        String msg="创建成功";
        String dateTime = DateTimeUtil.getSysTime();
        Customer customer=customerDao.selectByName(customerName);
        if(customer==null){//要新建一个客人
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            customer.setCreateTime(dateTime);
            customer.setCreateBy(user.getName());
            customer.setOwner(contacts.getOwner());
            customer.setAddress(contacts.getAddress());
            customer.setContactSummary(contacts.getContactSummary());
            customer.setDescription(contacts.getDescription());
            customer.setNextContactTime(contacts.getNextContactTime());
            int count1=customerDao.addByCustomer(customer);
            if(count1!=1){
                msg="创建联系人失败";
                throw new CountWrongException("添加新客户失败");
            }
        }
        contacts.setCustomerId(customer.getId());
        contacts.setId(UUIDUtil.getUUID());
        contacts.setCreateBy(user.getName());
        contacts.setCreateTime(dateTime);
        int count2=contactsDao.addContacts(contacts);
        if(count2!=1){
            msg="创建联系人失败";
            throw new CountWrongException("创建联系人失败");
        }

        return msg;
    }

    @Override
    @Transactional
    public String deleteByIds(String[] ids) {
        String msg="删除成功";
        //1从contacts表中删除
        int count1=contactsDao.deleteByIds(ids);
        if(count1!=ids.length){
            msg="删除contacts表失败";
            throw new CountWrongException(msg);
        }

        //2从tbl_contacts_activity_relation删除
        int count2=contactsActivityRelationDao.deleteByCIds(ids);

        //3从tbl_contacts_remark表格删除
        int count3=contactsRemarkDao.deleteByCIds(ids);



        return msg;
    }

    @Override
    public Contacts edit(String id) {
        Contacts contacts=contactsDao.selectById(id);
        return contacts;
    }

    @Override
    @Transactional
    public boolean update(Contacts contacts, String customerName, User user) {
        Customer customer=customerDao.selectByName(customerName);
        String dateTime=DateTimeUtil.getSysTime();
        //此时要添加客人
        if(customer==null){//要新建一个客人
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            customer.setCreateTime(dateTime);
            customer.setCreateBy(user.getName());
            customer.setOwner(contacts.getOwner());
            customer.setAddress(contacts.getAddress());
            customer.setContactSummary(contacts.getContactSummary());
            customer.setDescription(contacts.getDescription());
            customer.setNextContactTime(contacts.getNextContactTime());
            int count1=customerDao.addByCustomer(customer);
            if(count1!=1){
                throw new CountWrongException("添加新客户失败");
            }
        }
        contacts.setCustomerId(customer.getId());
        contacts.setEditBy(user.getName());
        contacts.setEditTime(dateTime);
        int cout2=contactsDao.updateContacts(contacts);
        if(cout2!=1)  throw new CountWrongException("更新联系人失败");
        return true;
    }

    @Override
    public HashMap<Object,Object> detail(String id) {
        HashMap<Object,Object> hashMap=new HashMap<>();
        Contacts contacts=contactsDao.selectById(id);
        hashMap.put("contacts",contacts);
        ArrayList<Activity> activityList=activityDao.selectByContactRelation(id);
        hashMap.put("activityList",activityList);
        ArrayList<Tran> tranList=tranDao.selectByContactsId(id);
        hashMap.put("tranList",tranList);
        ArrayList<ContactsRemark> remarkList=contactsRemarkDao.selectByContactsId(id);
        hashMap.put("remarkList",remarkList);

        return hashMap;
    }

    @Override
    public String getOwnerName(String ownerId) {
        String ownerName=userDao.getUserNameById(ownerId);
        return ownerName;
    }

    @Override
    @Transactional
    public Integer saveRemark(ContactsRemark contactsRemark) {
        int count=contactsRemarkDao.addContactsRemark(contactsRemark);
        if(count!=1) throw new CountWrongException("添加备注失败");

        return count;
    }

    @Override
    public List<ContactsRemark> loadContactsRemark(String contactsId) {
        List<ContactsRemark> remarkList=contactsRemarkDao.selectByContactsId(contactsId);
        return remarkList;
    }

    @Override
    @Transactional
    public int updateRemark(ContactsRemark remark) {
        int count=contactsRemarkDao.update(remark);
        if(count!=1) throw new CountWrongException("修改备注出错");
        return 0;
    }

    @Override
    @Transactional
    public int deleteRemarkById(String id) {
        int  count=contactsRemarkDao.deleteRemarkById(id);
        if(count!=1) throw new CountWrongException("删除备注出错");
        return count;


    }


}
