package my.ssh.biz.common.dao;

import my.ssh.biz.common.entity.Page;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzy on 16/2/21.
 */
public interface BaseDao<T> {
    Serializable save(T entity);

    void update(T entity);
    void updateDynamic(T entity) throws Exception;
    void saveOrUpdate(T entity);

    void deleteById(Serializable...id) throws Exception;

    T get(Serializable id);
    T load(Serializable id);

    T getOne(T entiry);
    List<T> list(T entiry);
    List<T> pageList(T entiry, Page<T> page);

    List<T> listAll();
    List<T> searchList(T entiry, Page<T> page);
    Page<T> searchPage(T entiry, Page<T> page);

    long count(T entiry);
    long searchCount(T entiry);

    Class<T> getClassType();

    DetachedCriteria initDetachedCriteriaByPage(Page<T> page);
    DetachedCriteria getDetachedCriteria(T entity, Page<T> page);
}
