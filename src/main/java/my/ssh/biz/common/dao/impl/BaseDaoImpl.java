package my.ssh.biz.common.dao.impl;

import my.ssh.biz.common.dao.BaseDao;
import my.ssh.biz.common.entity.Page;
import my.ssh.util.BeanUtils;
import my.ssh.util.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.annotation.Resource;
import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by zzy on 16/2/21.
 */
public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

    @Resource(name = "hibernateTemplate")
    public void setSuperHibernateTemplate(HibernateTemplate hibernateTemplate) {
        super.setHibernateTemplate(hibernateTemplate);
    }

    @Override
    public final Serializable save(T entity) {
        return this.getHibernateTemplate().save(entity);
    }

    @Override
    public final void update(T entity) {
        this.getHibernateTemplate().update(entity);
    }

    @Override
    public final void updateDynamic(T entity) throws Exception {
        T source = this.get(BeanUtils.getPrimaryKeyValue(entity));
        BeanUtils.copyPropertiesIgnoreNull(entity, source);
        this.update(source);
    }

    @Override
    public final void saveOrUpdate(T entity) {
        this.getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public final void deleteById(Serializable...ids) throws Exception {
        DetachedCriteria dc = DetachedCriteria.forClass(getClassType());
        dc.add(Restrictions.in(ConvertUtils.getPrimaryFieldName(getClassType()), ids));
        List<T> list = (List<T>) this.getHibernateTemplate().findByCriteria(dc);
        this.getHibernateTemplate().deleteAll(list);
    }

    @Override
    public final T get(Serializable id) {
        return this.getHibernateTemplate().get(getClassType(), id);
    }

    @Override
    public final T load(Serializable id) {
        return this.getHibernateTemplate().load(getClassType(), id);
    }

    @Override
    public final T getOne(T entity) {
        List<T> entityList = this.getHibernateTemplate().findByExample(entity, 0, 1);

        if (null != entityList && entityList.size() >= 1) {
            return entityList.get(0);
        }
        return null;
    }

    @Override
    public final List<T> list(T entiry) {
        return this.pageList(entiry, new Page());
    }

    @Override
    public final List<T> pageList(T entity, Page<T> page) {
        DetachedCriteria dc = initDetachedCriteriaByPage(page);

        Example example = Example.create(entity);
        if (StringUtils.isNotBlank(page.getExcludePropertys())) {
            for (String excludeProperty : page.getExcludePropertys().split(",")) {
                example.excludeProperty(excludeProperty);
            }
        }
        dc.add(example);

        return (List<T>) this.getHibernateTemplate().findByCriteria(dc);
    }

    @Override
    public final long count(T entity) {
        DetachedCriteria dc = DetachedCriteria.forClass(entity.getClass());
        dc.add(Example.create(entity));
        dc.setProjection(Projections.projectionList().add(Projections.rowCount()));
        return (Long) this.getHibernateTemplate().findByCriteria(dc).get(0);
    }

    @Override
    public final long searchCount(T entity) {
        DetachedCriteria dc = getDetachedCriteria(entity, null);
        if (null == dc) {
            dc = DetachedCriteria.forClass(entity.getClass());
        }
        dc.setProjection(Projections.projectionList().add(Projections.rowCount()));
        return (Long) this.getHibernateTemplate().findByCriteria(dc).get(0);
    }

    @Override
    public final List<T> listAll() {
        return this.getHibernateTemplate().loadAll(getClassType());
    }

    @Override
    public final List<T> searchList(T entity, Page<T> page) {
        return (List<T>) this.getHibernateTemplate().findByCriteria(this.getDetachedCriteria(entity, page));

    }

    @Override
    public final Page<T> searchPage(T entity, Page<T> page) {

        page.setRecordsTotal(this.searchCount(null));
        page.setRecordsFiltered(this.searchCount(entity));
        page.setData(this.searchList(entity, page));

        return page;
    }

    @Override
    public final DetachedCriteria initDetachedCriteriaByPage(Page<T> page) {
        DetachedCriteria dc = DetachedCriteria.forClass(this.getClassType());

        if (null != page) {

            if (StringUtils.isNotBlank(page.getOrder())) {
                String[] orderArr = page.getOrder().split(",");
                for (String order : orderArr) {
                    String[] columnDir = order.split(" ");
                    String[] orderColumnInfos = columnDir[0].split("\\.");
                    if (orderColumnInfos.length > 1) {
                        try {
                            Method[] orderPropClassMethods = getClassType().getDeclaredField(orderColumnInfos[0]).getType().getMethods();
                            for (Method opcm : orderPropClassMethods) {
                                if (null != opcm.getAnnotation(Id.class)) {
                                    String propName = opcm.getName().substring(3, 4).toLowerCase() + opcm.getName().substring(4);
                                    if (!propName.equals(orderColumnInfos[1])) {
                                        columnDir[0] = orderColumnInfos[0] + "." + propName;
                                    }
                                    break;
                                }
                            }
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }

                    }
                    if (columnDir.length == 1 || "ASC".equals(columnDir[1].toUpperCase())) {
                        dc.addOrder(Order.asc(columnDir[0]));
                    } else {
                        dc.addOrder(Order.desc(columnDir[0]));
                    }
                }
            }

            if (StringUtils.isNotBlank(page.getPropertys())) {
                ProjectionList projectionList = Projections.projectionList();
                for (String property : page.getPropertys().split(",")) {
                    projectionList.add(Projections.property(property).as(property));
                }
                dc.setProjection(projectionList);
                dc.setResultTransformer(Transformers.aliasToBean(getClassType()));
            }

            if (page.getStart() >= 0) {
                dc.getExecutableCriteria(this.getSessionFactory().getCurrentSession()).setFirstResult(page.getStart());
            }
            if (page.getLength() > 0) {
                dc.getExecutableCriteria(this.getSessionFactory().getCurrentSession()).setMaxResults(page.getLength());
            }

        }
        return dc;
    }

    @Override
    public DetachedCriteria getDetachedCriteria(T entity, Page<T> page) {
        return null;
    }
}
