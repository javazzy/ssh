package my.ssh.biz.common.service;

import my.ssh.biz.common.dao.BaseDao;
import my.ssh.biz.common.entity.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzy on 16/2/21.
 */
public interface BaseService<T> {

    Serializable save(T entity) throws Exception;
    List<T> saveAll(List<T> entities) throws Exception;

    void update(T entity) throws Exception;
    void updateDynamic(T entity) throws Exception;

    void saveOrUpdate(T entity) throws Exception;

    void deleteById(Serializable...id) throws Exception;

    T get(Serializable id) throws Exception;
    T load(Serializable id) throws Exception;
    T getOne(T entiry);
    List<T> list(T entiry);
    List<T> pageList(T entiry, Page<T> page);
    List<T> listAll() throws Exception;
    List<T> searchList(T entiry,Page<T> page) throws Exception;
    Page<T> searchPage(T entiry,Page<T> page) throws Exception;
    long count(T entiry) throws Exception;
    long searchCount(T entiry) throws Exception;

    BaseDao<T> getDao();

    T putChche(T entity) throws Exception;
    void evictChche(T entity) throws Exception;
    void evictChche(Serializable id) throws Exception;
}
