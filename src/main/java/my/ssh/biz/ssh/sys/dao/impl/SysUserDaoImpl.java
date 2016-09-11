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
 *  持久层实现： 系统 - 用户表
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
            /**
             * 用户编号
             */
            if (entity.getId() > 0) {
               dc.add(Restrictions.eq("id", entity.getId()));
            }
            /**
             * 用户姓名
             */
            if (StringUtils.isNotBlank(entity.getUsername())) {
                dc.add(Restrictions.like("username", entity.getUsername(), MatchMode.ANYWHERE));
            }
            /**
             * 密码
             */
            if (StringUtils.isNotBlank(entity.getPassword())) {
                dc.add(Restrictions.like("password", entity.getPassword(), MatchMode.ANYWHERE));
            }
            /**
             * 是否启用
             */
            if (null != entity.getEnabled()) {
               dc.add(Restrictions.eq("enabled", entity.getEnabled()));
            }
            /**
             * 账号是否没有过期
             */
            if (null != entity.getAccountNonExpired()) {
               dc.add(Restrictions.eq("accountNonExpired", entity.getAccountNonExpired()));
            }
            /**
             * 账号是否没有锁定
             */
            if (null != entity.getAccountNonLocked()) {
               dc.add(Restrictions.eq("accountNonLocked", entity.getAccountNonLocked()));
            }
            /**
             * 密码是否没有过期
             */
            if (null != entity.getCredentialsNonExpired()) {
               dc.add(Restrictions.eq("credentialsNonExpired", entity.getCredentialsNonExpired()));
            }
            /**
             * 性别
             */
            if (StringUtils.isNotBlank(entity.getSex())) {
                dc.add(Restrictions.like("sex", entity.getSex(), MatchMode.ANYWHERE));
            }
            /**
             * 生日
             */
            if (null != entity.getBirthday()) {
                dc.add(Restrictions.between("birthday", entity.getBirthday(), Calendar.getInstance().getTime()));
            }
            /**
             * 邮箱
             */
            if (StringUtils.isNotBlank(entity.getEmail())) {
                dc.add(Restrictions.like("email", entity.getEmail(), MatchMode.ANYWHERE));
            }
            /**
             * 手机
             */
            if (StringUtils.isNotBlank(entity.getPhone())) {
                dc.add(Restrictions.like("phone", entity.getPhone(), MatchMode.ANYWHERE));
            }
            /**
             * 住址
             */
            if (StringUtils.isNotBlank(entity.getAddress())) {
                dc.add(Restrictions.like("address", entity.getAddress(), MatchMode.ANYWHERE));
            }
            /**
             * 头像
             */
            if (StringUtils.isNotBlank(entity.getPhoto())) {
                dc.add(Restrictions.like("photo", entity.getPhoto(), MatchMode.ANYWHERE));
            }
            /**
             * 注册时间
             */
            if (null != entity.getCreateTime()) {
                dc.add(Restrictions.between("createTime", entity.getCreateTime(), Calendar.getInstance().getTime()));
            }
        }
        return dc;
    }
}