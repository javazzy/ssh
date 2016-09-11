package my.ssh.biz.ssh.sys.dao.impl;

import my.ssh.biz.common.entity.Page;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;
import my.ssh.biz.common.dao.impl.BaseDaoImpl;
import my.ssh.biz.ssh.sys.dao.SysMenuDao;
import my.ssh.biz.ssh.sys.entity.SysMenu;

import java.util.Calendar;

/**
 *  持久层实现： 系统 - 菜单表
 */
@Repository
public class SysMenuDaoImpl extends BaseDaoImpl<SysMenu> implements SysMenuDao {

    public Class<SysMenu> getClassType() {
        return SysMenu.class;
    }

    @Override
    public DetachedCriteria getDetachedCriteria(SysMenu entity, Page<SysMenu> page) {
        DetachedCriteria dc = initDetachedCriteriaByPage(page);

        if(null != entity) {
            /**
             * 菜单编号
             */
            if (entity.getId() > 0) {
               dc.add(Restrictions.eq("id", entity.getId()));
            }
            /**
             * 菜单标题
             */
            if (StringUtils.isNotBlank(entity.getTitle())) {
                dc.add(Restrictions.like("title", entity.getTitle(), MatchMode.ANYWHERE));
            }
            /**
             * 菜单层级编码
             */
            if (StringUtils.isNotBlank(entity.getLevelCode())) {
                dc.add(Restrictions.like("levelCode", entity.getLevelCode(), MatchMode.ANYWHERE));
            }
            /**
             * 父级菜单编号
             */
            if (null != entity.getParent()) {
               dc.add(Restrictions.eq("parent", entity.getParent()));
            }
            /**
             * 展示顺序
             */
            if (null != entity.getSort()) {
               dc.add(Restrictions.eq("sort", entity.getSort()));
            }
            /**
             * 菜单类型
             */
            if (StringUtils.isNotBlank(entity.getType())) {
                dc.add(Restrictions.like("type", entity.getType(), MatchMode.ANYWHERE));
            }
            /**
             * 页面链接
             */
            if (StringUtils.isNotBlank(entity.getUrl())) {
                dc.add(Restrictions.like("url", entity.getUrl(), MatchMode.ANYWHERE));
            }
            /**
             * 页面链接访问方式
             */
            if (StringUtils.isNotBlank(entity.getMethod())) {
                dc.add(Restrictions.like("method", entity.getMethod(), MatchMode.ANYWHERE));
            }
            /**
             * 菜单标志
             */
            if (StringUtils.isNotBlank(entity.getAuthority())) {
                dc.add(Restrictions.like("authority", entity.getAuthority(), MatchMode.ANYWHERE));
            }
        }
        return dc;
    }
}