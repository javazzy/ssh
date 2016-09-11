package my.ssh.biz.ssh.sys.dao.impl;

import my.ssh.biz.common.entity.Page;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;
import my.ssh.biz.common.dao.impl.BaseDaoImpl;
import my.ssh.biz.ssh.sys.dao.SysLogDao;
import my.ssh.biz.ssh.sys.entity.SysLog;

import java.util.Calendar;

/**
 *  持久层实现： 系统 - 日志表
 */
@Repository
public class SysLogDaoImpl extends BaseDaoImpl<SysLog> implements SysLogDao {

    public Class<SysLog> getClassType() {
        return SysLog.class;
    }

    @Override
    public DetachedCriteria getDetachedCriteria(SysLog entity, Page<SysLog> page) {
        DetachedCriteria dc = initDetachedCriteriaByPage(page);

        if(null != entity) {
            /**
             * 日志编号
             */
            if (entity.getId() > 0) {
               dc.add(Restrictions.eq("id", entity.getId()));
            }
            /**
             * 用户编号
             */
            if (null != entity.getUserid()) {
               dc.add(Restrictions.eq("userid", entity.getUserid()));
            }
            /**
             * 用户名称
             */
            if (StringUtils.isNotBlank(entity.getUsername())) {
                dc.add(Restrictions.like("username", entity.getUsername(), MatchMode.ANYWHERE));
            }
            /**
             * 类
             */
            if (StringUtils.isNotBlank(entity.getClass_())) {
                dc.add(Restrictions.like("class_", entity.getClass_(), MatchMode.ANYWHERE));
            }
            /**
             * 方法
             */
            if (StringUtils.isNotBlank(entity.getMethod())) {
                dc.add(Restrictions.like("method", entity.getMethod(), MatchMode.ANYWHERE));
            }
            /**
             * 产生时间
             */
            if (null != entity.getCreateTime()) {
                dc.add(Restrictions.between("createTime", entity.getCreateTime(), Calendar.getInstance().getTime()));
            }
            /**
             * 日志级别
             */
            if (StringUtils.isNotBlank(entity.getLogLevel())) {
                dc.add(Restrictions.like("logLevel", entity.getLogLevel(), MatchMode.ANYWHERE));
            }
            /**
             * 日志内容
             */
            if (StringUtils.isNotBlank(entity.getMessage())) {
                dc.add(Restrictions.like("message", entity.getMessage(), MatchMode.ANYWHERE));
            }
        }
        return dc;
    }
}