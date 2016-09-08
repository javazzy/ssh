package my.ssh.biz.ssh.sys.service.impl;

import my.ssh.biz.common.dao.BaseDao;
import my.ssh.biz.common.service.impl.*;
import my.ssh.biz.ssh.sys.dao.TestDao;
import my.ssh.biz.ssh.sys.entity.Test;
import my.ssh.biz.ssh.sys.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *  服务实现： 测试表
 */
@Service
public class TestServiceImpl extends CacheServiceImpl<Test> implements TestService{

    @Resource
    private TestDao testDao;

    @Override
    public BaseDao<Test> getDao() {
        return testDao;
    }
}
