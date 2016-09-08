package my.ssh.biz.ssh.sys.service.impl;

import my.ssh.biz.common.dao.BaseDao;
import my.ssh.biz.common.service.impl.BaseServiceImpl;
import my.ssh.biz.ssh.sys.dao.SysUserDao;
import my.ssh.biz.ssh.sys.entity.SysUser;
import my.ssh.biz.ssh.sys.service.SysUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 服务实现：
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;

    private UserCache userCache;

    @Override
    public BaseDao<SysUser> getDao() {
        return sysUserDao;
    }

    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser = sysUserDao.getOne(sysUser);

        if (null == sysUser) {
            throw new UsernameNotFoundException(String.format("用户：%s不存在！", username));
        }

        return new User(
                sysUser.getUsername(),
                sysUser.getPassword(),
                sysUser.getEnabled(),
                sysUser.getAccountNonExpired(),
                sysUser.getCredentialsNonExpired(),
                sysUser.getAccountNonLocked(),
                sysUser.getAuthorities());
    }

    @Override
    public void evictChche(SysUser entity) throws Exception {
        SysUser sysUser = this.getDao().get(entity.getId());
        if (null != sysUser) {

            evictUserChche(sysUser.getUsername());
        }
    }

    @Override
    public SysUser putChche(SysUser entity) throws Exception {
        evictUserChche(entity.getUsername());
        return entity;
    }

    public void evictUserChche(String username) {
        userCache.removeUserFromCache(username);
    }
}
