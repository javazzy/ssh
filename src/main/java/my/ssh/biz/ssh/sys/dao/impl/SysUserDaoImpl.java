package my.ssh.biz.ssh.sys.dao.impl;

import my.ssh.biz.common.entity.Page;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;
import my.ssh.biz.common.dao.impl.BaseDaoImpl;
import my.ssh.biz.ssh.sys.dao.SysUserDao;
import my.ssh.biz.ssh.sys.entity.SysUser;

import java.util.Calendar;

/**
 *  持久层实现：
 */
@Repository
public class SysUserDaoImpl extends BaseDaoImpl<SysUser> implements SysUserDao {

    public Class<SysUser> getClassType() {
        return SysUser.class;
    }

    @Override
    public DetachedCriteria getDetachedCriteria(SysUser entity, Page<SysUser> page) {
        DetachedCriteria dc = initDetachedCriteriaByPage(page);

        if(null != entity) {
            if (entity.getId() > 0) {
               dc.add(Restrictions.eq("id", entity.getId()));
            }
            if (StringUtils.isNotBlank(entity.getUsername())) {
                dc.add(Restrictions.like("username", entity.getUsername(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(entity.getPassword())) {
                dc.add(Restrictions.eq("password", entity.getPassword()));
            }
        }
        return dc;
    }
}