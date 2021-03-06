package my.ssh.biz.common.controller;

import com.alibaba.fastjson.JSON;
import my.ssh.biz.common.entity.Page;
import my.ssh.biz.common.entity.Result;
import my.ssh.biz.common.service.BaseService;
import my.ssh.biz.ssh.dic.entity.DicSex;
import my.ssh.util.ConvertUtils;
import my.ssh.util.WebUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 公共抽象控制层
 *
 * @param <T>
 */
public abstract class BaseController<T> {

    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     * 初始化controller
     *
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        //注册时间格式转换器
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
        binder.registerCustomEditor(Timestamp.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:dd"), true));
    }

    /**
     * 访问请求时资源初始化
     *
     * @param request
     * @param response
     */
    @ModelAttribute
    public void setAttribute(HttpServletRequest request, HttpServletResponse response) {
        WebUtils.setRequest(request);
        WebUtils.setResponse(response);
    }

    /**
     * 单条数据新增，例：
     * url:'api/sysUser'（数据为form表单数据，或ajax请求的data参数）
     * type:'POST'
     * data:{id:1,name:"张三"} //（或数据为form表单数据）
     *
     * @param entity
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public final Object add(T entity) {
        try {
            if(logger.isDebugEnabled()){
                logger.debug("method:add entry:"+JSON.toJSONString(entity));
            }
            getService().save(entity);
            return getService().putChche(entity);
        } catch (Exception e) {
            catchException(e);
            return error(e);
        }
    }

    /**
     * 批量新增。（这个方法需要自类接受前台参数，然后调用此方法！）例：
     * url:'api/sysUser/addAll'（数据为form表单数据，或ajax请求的data参数）
     * contentType : 'application/json;charset=utf-8', //设置请求头信息
     * data:[{name:"张三"},{name:"李四"}],// 如果是form表单，可直接使用 $("#form1").serializeJson(),注意，这个方法依赖于我从网上扒的form2json.js
     *
     * @param list
     * @return
     */
    @RequestMapping("/addAll")
    @ResponseBody
    public final Result addAll(@RequestBody List<T> list) {
        try {
            if(logger.isDebugEnabled()){
                logger.debug("method:addAll list:"+JSON.toJSONString(list));
            }
            getService().saveAll(list);
            for (T entity : list) {
                getService().putChche(entity);
            }
            return success();
        } catch (Exception e) {
            catchException(e);
            return error(e);
        }
    }


    /**
     * 单条数据修改，忽略空值,例：
     * url:'api/sysUser'
     * type:'PUT'
     * data:{id:1,name:'张三'}
     *
     * @param entity
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public final Result update(T entity, @RequestParam(defaultValue = "false") boolean full) {
        try {
            if(logger.isDebugEnabled()){
                logger.debug("method:updateDynamic entity:"+JSON.toJSONString(entity)+" full:"+full);
            }
            if (full) {
                getService().update(entity);
            } else {
                getService().updateDynamic(entity);
            }
            getService().putChche(entity);
            return success();
        } catch (Exception e) {
            catchException(e);
            return error(e);
        }
    }

    /**
     * 数据删除，例：
     * url:'api/sysUser/{id}'
     * type:'DELETE'
     *
     * @param keys
     * @return
     */
    @RequestMapping(value = "/{keys}", method = RequestMethod.DELETE)
    @ResponseBody
    public final Object delete(@PathVariable String keys, T t) {
        try {
            if(logger.isDebugEnabled()){
                logger.debug("method:delete keys:"+JSON.toJSONString(keys));
            }
            String[] keyArr = keys.split(",");
            if(keyArr.length == 0){
                return error("没有删除主键！");
            }

            Serializable[] ids = new Serializable[keyArr.length];
            for(int i = 0; i < keyArr.length; i++){
                ids[i] = ConvertUtils.toPrimaryData(t.getClass(), keyArr[i]);
                getService().evictChche(ids[i]);
            }
            getService().deleteById(ids);
            return success();
        } catch (Exception e) {
            catchException(e);
            return error(e);
        }
    }

    /**
     * 根据主键单条数据查询，例：
     * url:'api/sysUser/1'
     * type:'GET'
     * data:null
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    @ResponseBody
    public final T getByKey(@PathVariable String key, T t) {
        try {
            if(logger.isDebugEnabled()){
                logger.debug("method:getByKey key:"+key);
            }
            Serializable id = ConvertUtils.toPrimaryData(t.getClass(), key);
            return getService().get(id);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 根据非主键单条数据查询（注：本查询方法主键字段值不参与条件查询），例：
     * url:'api/sysUser'
     * type:'GET'
     * data:{name:'张三'}
     *
     * @param entity
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public final T row(T entity) {
        try {
            if(logger.isDebugEnabled()){
                logger.debug("method:row entity:"+JSON.toJSONString(entity));
            }
            return getService().getOne(entity);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 精确条件查询,但不包括主键，例：
     * url:'api/sysUser/list'
     * type:'GET'
     * data:{name:'张三'}
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public final List<T> list(T entity, Page<T> page, @RequestParam(defaultValue = "false") boolean all) {
        try {
            if(logger.isDebugEnabled()){
                logger.debug("method:list entity:"+JSON.toJSONString(entity)+" page:"+JSON.toJSONString(page)+" all:"+all);
            }
            if(all){
                return getService().listAll();
            }
            return getService().pageList(entity, page);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 搜索分页查询，例
     * url:'api/sysUsers/searchPage'
     * type:'GET'
     * data:{
     * name:"张", age:26, // 搜索内容
     * start:0, length:10, //分页内容
     * order:"id asc,name asc" // 排序内容
     * }
     *
     * @param entity
     * @param page
     * @return
     */
    @RequestMapping(value = "/searchPage", method = RequestMethod.GET)
    @ResponseBody
    public final Page searchPage(T entity, Page page) {
        try {
            if(logger.isDebugEnabled()){
                logger.debug("method:searchPage entity:"+JSON.toJSONString(entity)+" page:"+JSON.toJSONString(page));
            }
            return getService().searchPage(entity, page);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 搜索列表查询，例
     * url:'api/sysUsers/searchList'
     * type:'GET'
     * data:{
     * name:"张", age:26, // 搜索内容
     * }
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/searchList", method = RequestMethod.GET)
    @ResponseBody
    public final List<T> searchList(T entity, Page page) {
        try {
            if(logger.isDebugEnabled()){
                logger.debug("method:searchList entity:"+JSON.toJSONString(entity)+" page:"+JSON.toJSONString(page));
            }
            return getService().searchList(entity, page);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 精确匹配列表条数查询，例
     * url:'api/sysUsers/count'
     * type:'GET'
     * data:{
     * name:"张三" // 搜索内容
     * }
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ResponseBody
    public final Long count(T entity) {
        try {
            if(logger.isDebugEnabled()){
                logger.debug("method:count entity:"+JSON.toJSONString(entity));
            }
            return this.getService().count(entity);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 搜索列表条数查询，例
     * url:'api/sysUsers/searchCount'
     * type:'GET'
     * data:{
     * name:"张三" // 搜索内容
     * }
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/searchCount", method = RequestMethod.GET)
    @ResponseBody
    public final Long searchCount(T entity) {
        try {
            if(logger.isDebugEnabled()){
                logger.debug("method:searchCount entity:"+JSON.toJSONString(entity));
            }
            return this.getService().searchCount(entity);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 根据父级树节点的ID查询所有子节点
     *
     * @param rootEntity 根节点
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public final List<T> tree(T rootEntity) throws Exception {
        try {
            if(logger.isDebugEnabled()){
                logger.debug("method:tree entity:"+JSON.toJSONString(rootEntity));
            }
            //获取实体参数中的ID
            Method idMethod = rootEntity.getClass().getMethod("getId");
            Object parentIdObj = idMethod.invoke(rootEntity);
            //获取实体参数中的TITLE
            Method titleMethod = rootEntity.getClass().getMethod("getTitle");
            Object titleObj = titleMethod.invoke(rootEntity);

            String parentId = "0";
            if (null != parentIdObj) {
                parentId = parentIdObj.toString(); // 如果没有参数，就表示查根节点
            }

            //先将表中的所有数据查询出来
            List<T> dataList = getService().listAll();

            //再将所有数据利用递归封装成树形实体结构
            List<T> treeNodeList = new ArrayList<T>();
            for (T data : dataList) {
                Method _m = data.getClass().getMethod("getParent");
                Object pid = _m.invoke(data);
                if (parentId.equals(pid.toString())) {
                    treeNodeList.add(data);
                    appendTreeNode(dataList, data); // 树形实体结构递归方法
                }
            }

            if (null != titleObj) {
                Map<String, Object> rootNode = new HashMap<String, Object>();
                rootNode.put("key", "");
                rootNode.put("title", titleObj);
                rootNode.put("children", treeNodeList);
                rootNode.put("expanded", true);
                List rootNodeList = new ArrayList();
                rootNodeList.add(rootNode);
                return rootNodeList;
            }

            return treeNodeList;
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 树形实体结构递归方法
     *
     * @param dataList
     * @param parentNode
     * @throws Exception
     */
    public final void appendTreeNode(List<T> dataList, T parentNode) throws Exception {
        if (null == dataList) {
            return;
        }

        Method parentNodeMethod = parentNode.getClass().getMethod("getId");
        String parentId = parentNodeMethod.invoke(parentNode).toString();

        List<T> children = new ArrayList<>();
        for (T data : dataList) {
            Method m = data.getClass().getMethod("getParent");
            Object pid = m.invoke(data);

            if (parentId.equals(pid.toString())) {
                children.add(data);
                appendTreeNode(dataList, data);
            }
        }
        if (children.size() > 0) {
            Method m = parentNode.getClass().getMethod("setChildren", List.class);
            m.invoke(parentNode, children);
        }
    }

    public abstract BaseService<T> getService();

    public final void catchException(Exception e) {
        if (null != e.getCause())
            logger.error(e.getCause().getMessage(), e.getCause());
        else
            logger.error(e.getMessage(), e);
    }

    public final Result success() {
        return new Result(true);
    }

    public final Result success(String msg) {
        return new Result(true, msg);
    }

    public final Result error(String msg) {
        return new Result(false, msg);
    }

    public final Result error(Exception e) {
        return new Result(false, null != e.getCause() ? e.getCause().getMessage() : e.getMessage());
    }

}
