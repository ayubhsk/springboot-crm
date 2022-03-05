package com.whx.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whx.excep.CountWrongException;
import com.whx.settings.domain.User;
import com.whx.utils.DateTimeUtil;
import com.whx.utils.UUIDUtil;
import com.whx.vo.PageVo;
import com.whx.workbench.dao.ContactsActivityRelationDao;
import com.whx.workbench.dao.ContactsDao;
import com.whx.workbench.dao.ContactsRemarkDao;
import com.whx.workbench.dao.CustomerDao;
import com.whx.workbench.domain.Activity;
import com.whx.workbench.domain.Contacts;
import com.whx.workbench.domain.Customer;
import com.whx.workbench.service.ContactsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
}
