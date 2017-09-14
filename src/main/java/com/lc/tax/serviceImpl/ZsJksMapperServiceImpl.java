package com.lc.tax.serviceImpl;

/**
 * @Author: pingfuli
 * @Description:
 * @Date:Created at 2017/8/10 下午10:22
 * @Modified By:
 */
import com.lc.tax.dao.hx_zs.ZsJksMapper;
import com.lc.tax.pojo.hx_zs.ZsJks;
import com.lc.tax.service.IZsJksMapperService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service("zsJksMapperService")
public class ZsJksMapperServiceImpl implements IZsJksMapperService {

    @Resource
    private ZsJksMapper zsJksMapper;

    public int deleteByPrimaryKey(String spuuid)
    {
        return zsJksMapper.deleteByPrimaryKey(spuuid);
    }

    public int insert(ZsJks record)
    {
        return zsJksMapper.insert(record);
    }

    public int insertSelective(ZsJks record)
    {
        return zsJksMapper.insertSelective(record);
    }

    public ZsJks selectByPrimaryKey(String spuuid)
    {
        return zsJksMapper.selectByPrimaryKey(spuuid);
    }

    public int updateByPrimaryKeySelective(ZsJks record)
    {
        return zsJksMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ZsJks record)
    {
        return zsJksMapper.updateByPrimaryKey(record);
    }
}