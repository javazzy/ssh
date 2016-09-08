package my.ssh.biz.ssh.sys.dao.impl;

import my.ssh.biz.common.entity.Page;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;
import my.ssh.biz.common.dao.impl.BaseDaoImpl;
import my.ssh.biz.ssh.sys.dao.TestDao;
import my.ssh.biz.ssh.sys.entity.Test;

import java.util.Calendar;

/**
 *  持久层实现： 测试表
 */
@Repository
public class TestDaoImpl extends BaseDaoImpl<Test> implements TestDao {

    public Class<Test> getClassType() {
        return Test.class;
    }

    @Override
    public DetachedCriteria getDetachedCriteria(Test entity, Page<Test> page) {
        DetachedCriteria dc = initDetachedCriteriaByPage(page);

        if(null != entity) {
            /**
             * 主键
             */
            if (entity.getId() > 0) {
               dc.add(Restrictions.eq("id", entity.getId()));
            }
            /**
             * 名称
             */
            if (StringUtils.isNotBlank(entity.getName())) {
                dc.add(Restrictions.eq("name", entity.getName()));
            }
            /**
             * 年龄
             */
            if (null != entity.getAge()) {
               dc.add(Restrictions.eq("age", entity.getAge()));
            }
            /**
             * 创建日期
             */
//            if (null != entity.getDate()) {
//                dc.add(Restrictions.between("date", entity.getDate(), Calendar.getInstance().getTime()));
//            }
        }

        return dc;
    }
}