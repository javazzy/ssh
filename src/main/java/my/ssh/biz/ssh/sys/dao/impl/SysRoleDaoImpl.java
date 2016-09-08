package my.ssh.biz.ssh.sys.dao.impl;

import my.ssh.biz.common.entity.Page;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;
import my.ssh.biz.common.dao.impl.BaseDaoImpl;
import my.ssh.biz.ssh.sys.dao.SysRoleDao;
import my.ssh.biz.ssh.sys.entity.SysRole;

import java.util.Calendar;

/**
 *  持久层实现： 系统 - 角色表
 */
@Repository
public class SysRoleDaoImpl extends BaseDaoImpl<SysRole> implements SysRoleDao {

    public Class<SysRole> getClassType() {
        return SysRole.class;
    }

    @Override
    public DetachedCriteria getDetachedCriteria(SysRole entity, Page<SysRole> page) {
        DetachedCriteria dc = initDetachedCriteriaByPage(page);

        if(null != entity) {
            /**
             * 角色编号
             */
            if (entity.getId() > 0) {
               dc.add(Restrictions.eq("id", entity.getId()));
            }
            /**
             * 角色名称
             */
            if (StringUtils.isNotBlank(entity.getName())) {
                dc.add(Restrictions.eq("name", entity.getName()));
            }
            /**
             * 角色标志
             */
            if (StringUtils.isNotBlank(entity.getAuthority())) {
                dc.add(Restrictions.eq("authority", entity.getAuthority()));
            }
        }
        return dc;
    }
}