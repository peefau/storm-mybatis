package com.lc.tax.service;

import com.lc.tax.pojo.hx_zs.ZsJks;

public interface IZsJksMapperService {

    int deleteByPrimaryKey(String spuuid);

    int insert(ZsJks record);

    int insertSelective(ZsJks record);

    ZsJks selectByPrimaryKey(String spuuid);

    int updateByPrimaryKeySelective(ZsJks record);

    int updateByPrimaryKey(ZsJks record);
}
