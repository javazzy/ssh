package my.ssh.biz.ssh.sys.service.impl;

import my.ssh.biz.common.dao.BaseDao;
import my.ssh.biz.common.service.impl.CacheServiceImpl;
import my.ssh.biz.ssh.sys.dao.SysMenuDao;
import my.ssh.biz.ssh.sys.entity.SysMenu;
import my.ssh.biz.ssh.sys.entity.SysRole;
import my.ssh.biz.ssh.sys.entity.SysUser;
import my.ssh.biz.ssh.sys.service.SysMenuService;
import my.ssh.biz.ssh.sys.service.SysRoleService;
import my.ssh.biz.ssh.sys.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 服务实现：
 */
@Service
public class SysMenuServiceImpl extends CacheServiceImpl<SysMenu> implements SysMenuService {

    @Resource
    private SysMenuDao sysMenuDao;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysUserService sysUserService;

    @Override
    public BaseDao<SysMenu> getDao() {
        return sysMenuDao;
    }

    @Override
    public void evictChche(SysMenu entity) throws Exception {
        SysMenu sysMenu = sysMenuDao.get(entity.getId());
        if (null != sysMenu) {
            for (SysRole sysRole : sysMenu.getSysRoles()) {
                sysRole = sysRoleService.get(sysRole.getId());
                for (SysUser sysUser : sysRole.getSysUsers()) {
                    sysUserService.evictChche(sysUser);
                }
            }
        }

    }

    @Override
    public SysMenu putChche(SysMenu entity) throws Exception {
        SysMenu sysMenu = sysMenuDao.load(entity.getId());
        if (null != sysMenu) {
            for (SysRole sysRole : sysMenu.getSysRoles()) {
                sysRole = sysRoleService.get(sysRole.getId());
                for (SysUser sysUser : sysRole.getSysUsers()) {
                    sysUserService.evictChche(sysUser);
                }
            }
        }

        return entity;
    }
}
