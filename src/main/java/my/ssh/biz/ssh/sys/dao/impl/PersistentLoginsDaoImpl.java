package my.ssh.biz.ssh.sys.dao.impl;

import my.ssh.biz.common.entity.Page;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;
import my.ssh.biz.common.dao.impl.BaseDaoImpl;
import my.ssh.biz.ssh.sys.dao.PersistentLoginsDao;
import my.ssh.biz.ssh.sys.entity.PersistentLogins;

import java.util.Calendar;

/**
 *  持久层实现： 系统 - 免登录用户记录表
 */
@Repository
public class PersistentLoginsDaoImpl extends BaseDaoImpl<PersistentLogins> implements PersistentLoginsDao {

    public Class<PersistentLogins> getClassType() {
        return PersistentLogins.class;
    }

    @Override
    public DetachedCriteria getDetachedCriteria(PersistentLogins entity, Page<PersistentLogins> page) {
        DetachedCriteria dc = initDetachedCriteriaByPage(page);

        if(null != entity) {
            /**
             * 唯一标志
             */
            if (StringUtils.isNotBlank(entity.getSeries())) {
                dc.add(Restrictions.eq("series", entity.getSeries()));
            }
            /**
             * 用户名
             */
            if (StringUtils.isNotBlank(entity.getUsername())) {
                dc.add(Restrictions.eq("username", entity.getUsername()));
            }
            /**
             * 安全码
             */
            if (StringUtils.isNotBlank(entity.getToken())) {
                dc.add(Restrictions.eq("token", entity.getToken()));
            }
            /**
             * 最后登录时间
             */
            if (null != entity.getLastUsed()) {
                dc.add(Restrictions.between("lastUsed", entity.getLastUsed(), Calendar.getInstance().getTime()));
            }
        }
        return dc;
    }
}