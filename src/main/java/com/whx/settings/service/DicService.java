package com.whx.settings.service;


import com.whx.settings.domain.DicType;
import com.whx.settings.domain.DicValue;

import java.util.List;

public interface DicService {
    List<DicType> getAllDicType();

    List<DicValue> getDicValueByCode(String typeCode);
}
