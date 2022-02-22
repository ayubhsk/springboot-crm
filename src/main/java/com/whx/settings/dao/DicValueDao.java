package com.whx.settings.dao;

import com.whx.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getDicValueByCode(String typeCode);
}
