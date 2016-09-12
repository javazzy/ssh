package my.ssh.biz.ssh.dic.service.impl;

import my.ssh.biz.common.dao.BaseDao;
import my.ssh.biz.common.service.impl.*;
import my.ssh.biz.ssh.dic.dao.DicSexDao;
import my.ssh.biz.ssh.dic.entity.DicSex;
import my.ssh.biz.ssh.dic.service.DicSexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *  服务实现： 
 */
@Service
public class DicSexServiceImpl extends CacheServiceImpl<DicSex> implements DicSexService{

    @Resource
    private DicSexDao dicSexDao;

    @Override
    public BaseDao<DicSex> getDao() {
        return dicSexDao;
    }
}
