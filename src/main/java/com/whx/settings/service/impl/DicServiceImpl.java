package com.whx.settings.service.impl;

import com.whx.settings.dao.DicTypeDao;
import com.whx.settings.dao.DicValueDao;
import com.whx.settings.domain.DicType;
import com.whx.settings.domain.DicValue;
import com.whx.settings.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


//处理字典用的service
@Service
public class DicServiceImpl implements DicService {
    @Resource
    DicTypeDao dicTypeDao;
    @Resource
    DicValueDao dicValueDao;

    @Override
    public List<DicType> getAllDicType() {
        List<DicType> dicTypeList=dicTypeDao.getAllDicType();
        return dicTypeList;
    }

    @Override
    public List<DicValue> getDicValueByCode(String typeCode) {
        List<DicValue> dicValueList=dicValueDao.getDicValueByCode(typeCode);
        return dicValueList;
    }
}
