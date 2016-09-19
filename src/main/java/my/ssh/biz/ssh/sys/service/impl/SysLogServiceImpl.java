package my.ssh.biz.ssh.sys.service.impl;

import my.ssh.biz.common.dao.BaseDao;
import my.ssh.biz.common.service.impl.*;
import my.ssh.biz.ssh.sys.dao.SysLogDao;
import my.ssh.biz.ssh.sys.entity.SysLog;
import my.ssh.biz.ssh.sys.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *  服务实现�? ϵͳ - ��־��
 */
@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLog> implements SysLogService{

    @Resource
    private SysLogDao sysLogDao;

    @Override
    public BaseDao<SysLog> getDao() {
        return sysLogDao;
    }
}
