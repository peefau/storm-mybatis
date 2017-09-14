package com.lc.tax.dao.hx_zs;

import com.lc.tax.pojo.hx_zs.ZsJks;
import org.springframework.stereotype.Repository;

@Repository("zsJksMapper")
public interface ZsJksMapper {
    int deleteByPrimaryKey(String spuuid);

    int insert(ZsJks record);

    int insertSelective(ZsJks record);

    ZsJks selectByPrimaryKey(String spuuid);

    int updateByPrimaryKeySelective(ZsJks record);

    int updateByPrimaryKey(ZsJks record);
}