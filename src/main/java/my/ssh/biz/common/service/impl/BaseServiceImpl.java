package my.ssh.biz.common.service.impl;

import my.ssh.biz.common.entity.Page;
import my.ssh.biz.common.service.BaseService;
import my.ssh.util.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzy on 16/2/21.
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Override
    @Transactional
    public Serializable save(T entity) throws Exception {
        return getDao().save(entity);
    }


    @Override
    @Transactional
    public List<T> saveAll(List<T> entities) throws Exception {
        for(T entity : entities){
            this.save(entity);
        }
        return entities;
    }


    @Override
    @Transactional
    public void update(T entity) throws Exception {
        getDao().update(entity);
    }

    @Override
    @Transactional
    public void updateDynamic(T entity) throws Exception {
        getDao().updateDynamic(entity);
    }

    @Override
    @Transactional
    public void saveOrUpdate(T entity) throws Exception {
        getDao().saveOrUpdate(entity);
    }

    @Override
    @Transactional
    public void deleteById(Serializable...id) throws Exception {
        getDao().deleteById(id);
    }

    @Override
    public T get(Serializable id) throws Exception {
        return getDao().get(id);
    }

    @Override
    public long count(T entity) throws Exception {
        return getDao().count(entity);
    }

    @Override
    public long searchCount(T entity) throws Exception {
        return getDao().searchCount(entity);
    }

    @Override
    public T getOne(T entity) {
        return getDao().getOne(entity);
    }

    @Override
    public List<T> list(T entiry) {
        return getDao().list(entiry);
    }

    @Override
    public List<T> pageList(T entiry, Page<T> page) {
        return getDao().pageList(entiry, page);
    }

    @Override
    public List<T> listAll() throws Exception {
        return getDao().listAll();
    }

    @Override
    public List<T> searchList(T entity, Page<T> page) throws Exception {
        return getDao().searchList(entity, page);
    }

    @Override
    public Page<T> searchPage(T entity, Page<T> page) throws Exception {
        return getDao().searchPage(entity, page);
    }

    @Override
    public T putChche(T entity) throws Exception {
        return entity;
    }
    @Override
    public void evictChche(T entity) throws Exception {}
}
