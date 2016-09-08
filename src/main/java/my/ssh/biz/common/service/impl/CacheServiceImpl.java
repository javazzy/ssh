package my.ssh.biz.common.service.impl;

import my.ssh.biz.common.service.BaseService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzy on 16/2/21.
 */
public abstract class CacheServiceImpl<T> extends BaseServiceImpl<T> implements BaseService<T> {

    @Override
    @CacheEvict(value = "service", key = "#root.targetClass")
    @Transactional
    public Serializable save(T entity) throws Exception {
        return getDao().save(entity);
    }

    @Override
    @CacheEvict(value = "service", key = "#root.targetClass")
    @Transactional
    public void update(T entity) throws Exception {
        getDao().update(entity);
    }

    @Override
    @CacheEvict(value = "service", key = "#root.targetClass")
    @Transactional
    public void updateDynamic(T entity) throws Exception {
        getDao().updateDynamic(entity);
    }
    @Override
    @CachePut(value = "service", key = "#root.targetClass + '_' + #entity.id")
    public T putChche(T entity) throws Exception {
        return entity;
    }

    @Override
    @CacheEvict(value = "service", key = "#root.targetClass")
    @Transactional
    public void saveOrUpdate(T entity) throws Exception {
        getDao().saveOrUpdate(entity);
    }

    @Override
    @CacheEvict(value = "service", key = "#root.targetClass")
    @Transactional
    public void delete(T entity) throws Exception {
        getDao().delete(entity);
    }

    @Override
    @CacheEvict(value = "service", key = "#root.targetClass")
    @Transactional
    public void deleteAll(List<T> entities) throws Exception {
        for(T entity : entities){
            this.delete(entity);
        }
    }

    @Override
    @CacheEvict(value = "service", key = "#root.targetClass + '_' + #entity.id")
    public void evictChche(T entity) throws Exception {}

    @Override
    @Cacheable(value = "service", key = "#root.targetClass + '_' + #id")
    public T get(Serializable id) throws Exception {
        return getDao().get(id);
    }

    @Override
    @Cacheable(value = "service", key = "#root.targetClass")
    public List<T> listAll() throws Exception {
        return getDao().listAll();
    }
}
