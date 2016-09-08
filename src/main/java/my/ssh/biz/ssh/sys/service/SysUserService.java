package my.ssh.biz.ssh.sys.service;

import my.ssh.biz.common.service.BaseService;
import my.ssh.biz.ssh.sys.entity.SysUser;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *  服务接口： 
 */
public interface SysUserService extends BaseService<SysUser>,UserDetailsService {
    void setUserCache(UserCache userCache);

    void evictUserChche(String username);
}
