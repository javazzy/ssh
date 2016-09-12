package my.ssh.biz.ssh.dic.dao.impl;

import my.ssh.biz.common.entity.Page;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;
import my.ssh.biz.common.dao.impl.BaseDaoImpl;
import my.ssh.biz.ssh.dic.dao.DicSexDao;
import my.ssh.biz.ssh.dic.entity.DicSex;

import java.util.Calendar;

/**
 *  持久层实现： 
 */
@Repository
public class DicSexDaoImpl extends BaseDaoImpl<DicSex> implements DicSexDao {

    public Class<DicSex> getClassType() {
        return DicSex.class;
    }

    @Override
    public DetachedCriteria getDetachedCriteria(DicSex entity, Page<DicSex> page) {
        DetachedCriteria dc = initDetachedCriteriaByPage(page);

        if(null != entity) {
            /**
             * 性别编号
             */
            if (entity.getId() > 0) {
               dc.add(Restrictions.eq("id", entity.getId()));
            }
            /**
             * 性别
             */
            if (StringUtils.isNotBlank(entity.getName())) {
                dc.add(Restrictions.like("name", entity.getName(), MatchMode.ANYWHERE));
            }
        }
        return dc;
    }
}