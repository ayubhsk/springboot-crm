package com.whx.workbench.service.impl;

import com.whx.excep.CountWrongException;
import com.whx.settings.domain.User;
import com.whx.utils.DateTimeUtil;
import com.whx.utils.UUIDUtil;
import com.whx.vo.PageVo;
import com.whx.workbench.dao.*;
import com.whx.workbench.domain.*;
import com.whx.workbench.service.ClueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.invoke.WrongMethodTypeException;
import java.util.List;


@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    ClueDao clueDao;

    @Resource
    ActivityDao activityDao;

    @Resource
    ClueActivityRelationDao clueActivityRelationDao;

    @Resource
    ClueRemarkDao clueRemarkDao;

    @Resource
    CustomerDao customerDao;

    @Resource
    ContactsDao contactsDao;

    @Resource
    ContactsRemarkDao contactsRemarkDao;

    @Resource
    CustomerRemarkDao customerRemarkDao;

    @Resource
    ContactsActivityRelationDao contactsActivityRelationDao;

    @Resource
    TranDao tranDao;
    @Resource
    TranHistoryDao tranHistoryDao;

    @Override
    public PageVo<Clue> pageList(Clue clue, Integer pageNo, Integer pageSize) {
        Integer skipCount=(pageNo-1)*pageSize;
        List<Clue> list=clueDao.pageList(clue,skipCount,pageSize);

        Integer total=clueDao.pageTotal(clue,skipCount,pageSize);
        PageVo<Clue> pageVo=new PageVo<>();
        pageVo.setList(list);
        pageVo.setTotal(total);
        return pageVo;

    }

    @Override
    public boolean save(Clue clue) {
        int count=clueDao.save(clue);
        if(count!=1) throw new CountWrongException("线索保存出错");
        return count==1;
    }

    @Override
    public Clue getById(String id) {
        Clue clue=clueDao.getById(id);
        return clue;
    }

    @Override
    @Transactional
    public boolean delete(String[] ids) {
        int count=0;
        for (int i = 0; i <ids.length ; i++) {
            String id=ids[i];
            count+=clueDao.deleteById(id);
        }
        if(count!=ids.length) throw new CountWrongException("删除失败");
        return true;
    }

    @Override
    public Clue update(String id) {
        Clue clue=clueDao.getByIdNoChange(id);
        return clue;

    }

    @Override
    @Transactional
    public boolean updateClue(Clue clue) {
        int count=clueDao.updateClue(clue);
        if(count!=1) throw new WrongMethodTypeException("更新出错");
        return count==1;
    }

    @Override
    public List<Activity> remarkList(String id) {
        List<Activity> list=activityDao.remarkList(id);
        return list;

    }

    @Override
    @Transactional
    public List<Activity> getActivityListByNameAndNotByClueId(String aname, String clueId) {
        List<Activity> list=activityDao.getActivityListByNameAndNotByClueId(aname, clueId);
        return list;
    }

    @Override
    @Transactional
    public boolean bundActivity(String[] ids, String clueId) {
        int count=0;
        for (int i = 0; i <ids.length ; i++) {
            ClueActivityRelation car=new ClueActivityRelation();
            car.setActivityId(ids[i]);
            car.setClueId(clueId);
            car.setId(UUIDUtil.getUUID());
            count+=clueActivityRelationDao.add(car);
        }
        if(count!=ids.length) throw new CountWrongException("关联出错");
        return true;
    }

    @Override
    @Transactional
    public boolean unbund(String activityId, String clueId) {
        int count=clueActivityRelationDao.unbund(activityId,clueId);
        if(count!=1) throw new WrongMethodTypeException("解除关联失败");
        return true;
    }

    @Override
    @Transactional
    public boolean deleteCAR(String id) {
        int count=clueDao.deleteById(id);
        if(count!=1) throw new WrongMethodTypeException("删除出错");

        String clueId=id;
        int count1=clueActivityRelationDao.getNumByClueId(clueId);
        int count2=clueActivityRelationDao.deleteByClueId(clueId);
        if (count1!=count2) throw new WrongMethodTypeException("删除出错");

        return true;
    }

    @Override
    public String getOwnerIdBy(String id) {
        String ownerId=clueDao.getOwnerIdById(id);
        return ownerId;
    }

    @Override
    public List<ClueRemark> showRemarkList(String clueId) {
        List<ClueRemark> list=clueRemarkDao.getListByClueId(clueId);
        return list;
    }

    @Override
    @Transactional
    public boolean deleteRemark(String id) {
        int count=clueRemarkDao.deleteById(id);
        if(count!=1) throw new CountWrongException("删除出错");
        return count==1;

    }

    @Override
    public boolean saveRemark(ClueRemark clueRemark) {
        int count=clueRemarkDao.insert(clueRemark);
        return count==1;

    }

    @Override
    public List<Activity> getActivityListByName(String aName) {
        List<Activity> list=activityDao.selectListLikeName(aName);
        return list;
    }



    @Override
    @Transactional
    public boolean convertCLue(String flag, String clueId, Tran tran, User user) {
        String dateTime= DateTimeUtil.getSysTime();
        String createBy=user.getName();
        //(1)通过线索id获取线索对象（线索对象当中封装了线索的信息）
        Clue clue=clueDao.getById(clueId);
        String ownerId=tran.getOwner();
        //(2) 通过线索对象提取客户信息，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该客户是否存在！）
        Customer customer=customerDao.selectByName(clue.getCompany());
        if(customer==null){//此时新建客人
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setOwner(ownerId);
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setName(clue.getCompany());
            customer.setDescription(clue.getDescription());
            customer.setCreateTime(dateTime);
            customer.setCreateBy(user.getName());
            customer.setAddress(clue.getAddress());
            customer.setContactSummary(clue.getContactSummary());
            int count=customerDao.addByCustomer(customer);
            if(count!=1) throw new CountWrongException("添加顾客出错");
        }

        //(3)通过线索对象提取联系人信息，保存联系人
        Contacts contacts=new Contacts();
        contacts.setSource(clue.getSource());
        contacts.setOwner(ownerId);
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setId(UUIDUtil.getUUID());
        contacts.setFullname(clue.getFullname());
        contacts.setEmail(clue.getEmail());
        contacts.setDescription(clue.getDescription());
        contacts.setCreateTime(clue.getCreateTime());
        contacts.setCreateBy(clue.getCreateBy());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAppellation(clue.getAppellation());
        contacts.setAddress(clue.getAddress());
        contacts.setDescription(clue.getDescription());
        contacts.setCustomerId(customer.getId());
        int count=contactsDao.addContacts(contacts);
        if(count!=1){
            throw new CountWrongException("保存联系人出错");
        }
        //(4) 线索备注转换到客户备注以及联系人备注
        List<ClueRemark> clueRemarkList = clueRemarkDao.getListByClueId(clueId);
        //取出每一条线索的备注
        for(ClueRemark clueRemark : clueRemarkList){

            //取出备注信息（主要转换到客户备注和联系人备注的就是这个备注信息）
            String noteContent = clueRemark.getNoteContent();

            //创建客户备注对象，添加客户备注
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(dateTime);
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setEditFlag("0");
            customerRemark.setNoteContent(noteContent);
            int count3 = customerRemarkDao.addCustomerRemark(customerRemark);
            if(count3!=1){
                throw new CountWrongException("保存顾客备注出错");
            }

            //创建联系人备注对象，添加联系人
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(dateTime);
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setEditFlag("0");
            contactsRemark.setNoteContent(noteContent);
            int count4 = contactsRemarkDao.addContactsRemark(contactsRemark);
            if(count4!=1){
                throw new CountWrongException("保存联系人备注出错");
            }
        }
        //(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
        List<ClueActivityRelation> carList =clueActivityRelationDao.selectByClueId(clueId);
        for(ClueActivityRelation car:carList){
            ContactsActivityRelation contactsActivityRelation=new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(car.getActivityId());
            contactsActivityRelation.setContactsId(contacts.getId());
            int count5=contactsActivityRelationDao.addRelation(contactsActivityRelation);
            if(count5!=1){
                throw new CountWrongException("关联市场活动和联系人出错");
            }

        }
        //(6)如果有创建交易需求，创建一条交易
        if("checked".equals(flag)){
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setId(UUIDUtil.getUUID());
            tran.setDescription(clue.getDescription());
            tran.setCustomerId(customer.getId());
            tran.setCreateTime(dateTime);
            tran.setCreateBy(createBy);
            tran.setContactSummary(clue.getContactSummary());
            tran.setContactsId(contacts.getId());
            tran.setSource(clue.getSource());
            int count6=tranDao.addTran(tran);
            if(count6!=1){
                throw new CountWrongException("添加交易出错");
            }
            //(7)如果创建了交易，则创建一条该交易下的交易历史
            TranHistory th=new TranHistory();
            th.setTranId(tran.getId());
            th.setStage(tran.getStage());
            th.setMoney(tran.getMoney());
            th.setId(UUIDUtil.getUUID());
            th.setExpectedDate(tran.getExpectedDate());
            th.setCreateTime(dateTime);
            th.setCreateBy(user.getName());
            int count7=tranHistoryDao.addTranHistory(th);
            if(count7!=1){
                throw new CountWrongException("添加交易备注出错");
            }

        }

        //(8)删除线索备注
        int count8=clueRemarkDao.deleteByClueId(clueId);
        if(count8!=clueRemarkList.size()){
            throw new CountWrongException("删除线索备注失败");
        }
        //(9) 删除线索和市场活动的关系
        int count9=clueActivityRelationDao.deleteByClueId(clueId);
        if(count9!=carList.size()){
            throw new CountWrongException("删除线索和市场活动的关系失败");
        }
        //(10) 删除线索
        int count10=clueDao.deleteById(clueId);
        if(count10!=1){
            throw new CountWrongException("删除线索出错");
        }

        return true;
    }
}
