package my.ssh.biz.ssh.sys.service.impl;

import my.ssh.biz.common.dao.BaseDao;
import my.ssh.biz.common.service.impl.BaseServiceImpl;
import my.ssh.biz.ssh.sys.dao.SysRoleDao;
import my.ssh.biz.ssh.sys.entity.SysRole;
import my.ssh.biz.ssh.sys.entity.SysUser;
import my.ssh.biz.ssh.sys.service.SysRoleService;
import my.ssh.biz.ssh.sys.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 服务实现：
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Resource
    private SysUserService sysUserService;

    @Override
    public BaseDao<SysRole> getDao() {
        return sysRoleDao;
    }

    @Override
    public void evictChche(SysRole entity) throws Exception {
        SysRole sysRole = sysRoleDao.get(entity.getId());
        if (null != sysRole) {
            for (SysUser sysUser : sysRole.getSysUsers()) {
                sysUserService.evictChche(sysUser);
            }
        }
    }

    @Override
    public SysRole putChche(SysRole entity) throws Exception {
        SysRole sysRole = sysRoleDao.get(entity.getId());
        if (null != sysRole) {
            for (SysUser sysUser : sysRole.getSysUsers()) {
                sysUserService.evictChche(sysUser);
            }
        }
        return entity;
    }
}
