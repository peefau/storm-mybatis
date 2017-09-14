package com.lc.tax.service;

import com.lc.tax.pojo.hx_zs.ZsSrths;

public interface IZsSrthsMapperService {
    int deleteByPrimaryKey(String srthsuuid);

    int insert(ZsSrths record);

    int insertSelective(ZsSrths record);

    ZsSrths selectByPrimaryKey(String srthsuuid);

    int updateByPrimaryKeySelective(ZsSrths record);

    int updateByPrimaryKey(ZsSrths record);
}
