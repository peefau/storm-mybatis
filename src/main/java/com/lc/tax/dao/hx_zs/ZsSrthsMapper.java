package com.lc.tax.dao.hx_zs;

import com.lc.tax.pojo.hx_zs.ZsSrths;
import org.springframework.stereotype.Repository;

@Repository("zsSrthsMapper")
public interface ZsSrthsMapper {
    int deleteByPrimaryKey(String srthsuuid);

    int insert(ZsSrths record);

    int insertSelective(ZsSrths record);

    ZsSrths selectByPrimaryKey(String srthsuuid);

    int updateByPrimaryKeySelective(ZsSrths record);

    int updateByPrimaryKey(ZsSrths record);
}