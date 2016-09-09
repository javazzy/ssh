package my.ssh.biz.common.controller;

import my.ssh.biz.common.entity.Page;
import my.ssh.biz.common.entity.Result;
import my.ssh.biz.common.service.BaseService;
import my.ssh.util.BeanUtils;
import my.ssh.util.ConvertUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 公共抽象控制层
 *
 * @param <T>
 */
public abstract class SimpleController<T> {

    private final Logger logger = Logger.getLogger(this.getClass());
    private ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<HttpServletRequest>();
    private ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<HttpServletResponse>();
    private ThreadLocal<HttpSession> sessionThreadLocal = new ThreadLocal<HttpSession>();
    private ThreadLocal<ServletContext> contextThreadLocal = new ThreadLocal<ServletContext>();

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
        requestThreadLocal.set(request);
        responseThreadLocal.set(response);
        sessionThreadLocal.set(request.getSession());
        contextThreadLocal.set(request.getServletContext());
    }

    /**
     * 根据主键单条数据查询，例：
     * url:'api/sysUser/get/1'
     * data:null
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/get/{key}")
    @ResponseBody
    public T getById(@PathVariable String key, T t) {
        try {
            return getService().get(ConvertUtils.toPrimaryData(t.getClass(), key));
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 根据非主键单条数据查询，例：
     * url:'api/sysUser/get'
     * data:{name:'张三'}
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/get")
    @ResponseBody
    public T get(T entity) {
        try {
            return getService().getOne(entity);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 查询所有记录，例：
     * url:'api/sysUser/all'
     *
     * @return
     */
    @RequestMapping(value = "/all")
    @ResponseBody
    public List<T> all() {
        try {
            return getService().listAll();
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 精确条件查询,但不包括主键，例：
     * url:'api/sysUser/list'
     * data:{name:'张三'}
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<T> list(T entity,Page<T> page) {
        try {
            return getService().pageList(entity, page);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 单条数据新增，例：
     * url:'api/sysUser/add'（数据为form表单数据，或ajax请求的data参数）
     * data:{id:1,name:"张三"} //（或数据为form表单数据）
     *
     * @param entity
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Object add(T entity) {
        try {
            getService().save(entity);
            return getService().putChche(entity);
        } catch (Exception e) {
            catchException(e);
            return error(e);
        }
    }

    /**
     * 多条数据新增。（这个方法需要自类接受前台参数，然后调用此方法！）例：
     * url:'api/sysUser/addAll'（数据为form表单数据，或ajax请求的data参数）
     * contentType : 'application/json;charset=utf-8', //设置请求头信息
     * data:[{name:"张三"},{name:"李四"}],// 如果是form表单，可直接使用 $("#form1").serializeJson(),注意，这个方法依赖于我从网上扒的form2json.js
     *
     * @param list
     * @return
     */
//    @RequestMapping("/addAll")
//    @ResponseBody
    public Object addAll(@RequestBody List<T> list) {
        try {
            getService().saveAll(list);
            for (T entity : list){
                getService().putChche(entity);
            }
            return success();
        } catch (Exception e) {
            catchException(e);
            return error(e);
        }
    }

    /**
     * 单条数据修改，例：
     * url:'api/sysUser/update'
     * data:{id:1,name:'张三'}
     *
     * @param entity
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Result update(T entity) {
        try {
            getService().update(entity);
            getService().putChche(entity);
            return success();
        } catch (Exception e) {
            catchException(e);
            return error(e);
        }
    }


    /**
     * 单条数据修改，忽略空值,例：
     * url:'api/sysUser/updateDynamic'
     * data:{id:1,name:'张三'}
     *
     * @param entity
     * @return
     */
    @RequestMapping("/updateDynamic")
    @ResponseBody
    public Result updateDynamic(T entity) {
        try {
            getService().updateDynamic(entity);
            getService().putChche(entity);
            return success();
        } catch (Exception e) {
            catchException(e);
            return error(e);
        }
    }

    /**
     * 数据删除，例：
     * url:'api/sysUser/delete'
     * data:{id:1}
     *
     * @param entity
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(T entity) {
        try {
            getService().evictChche(entity);
            getService().delete(entity);
            return success();
        } catch (Exception e) {
            catchException(e);
            return error(e);
        }
    }

    /**
     * 单条或多条数据删除。（这个方法需要自类接受前台参数，然后调用此方法！）例：
     * url: "/api/sysUsers/deleteAll",
     * contentType : 'application/json;charset=utf-8', //设置请求头信息
     * data: [{id:1},{id:2}],// 如果是form表单，可直接使用 $("#form1").serializeJson(),注意，这个方法依赖于我从网上扒的form2json.js
     *
     * @param list
     * @return
     */
//    @RequestMapping("/deleteAll")
//    @ResponseBody
    public Object deleteAll(@RequestBody List<T> list) {
        try {
            for(T entity : list){
                getService().evictChche(entity);
            }
            getService().deleteAll(list);
            return success();
        } catch (Exception e) {
            catchException(e);
            return error(e);
        }
    }

    /**
     * 搜索分页查询，例
     * url:'api/sysUsers/searchPage'
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
    @RequestMapping(value = "/searchPage")
    @ResponseBody
    public Page searchPage(T entity, Page page) {
        try {
            return getService().searchPage(entity, page);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 搜索列表查询，例
     * url:'api/sysUsers/searchList'
     * data:{
     * name:"张", age:26, // 搜索内容
     * }
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/searchList")
    @ResponseBody
    public List<T> searchList(T entity, Page page) {
        try {
            return getService().searchList(entity, page);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 精确匹配列表条数查询，例
     * url:'api/sysUsers/count'
     * data:{
     * name:"张三" // 搜索内容
     * }
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/count")
    @ResponseBody
    public Long count(T entity) {
        try {
            return this.getService().count(entity);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 搜索列表条数查询，例
     * url:'api/sysUsers/searchCount'
     * data:{
     * name:"张三" // 搜索内容
     * }
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/searchCount")
    @ResponseBody
    public Long searchCount(T entity) {
        try {
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
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<T> tree(T rootEntity) throws Exception {
        try {
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
    public void appendTreeNode(List<T> dataList, T parentNode) throws Exception {
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

    public void catchException(Exception e) {
        if (null != e.getCause())
            logger.error(e.getCause().getMessage(), e.getCause());
        else
            logger.error(e.getMessage(), e);
    }

    public Result success() {
        return new Result(true);
    }

    public Result success(String msg) {
        return new Result(true, msg);
    }

    public Result error(String msg) {
        return new Result(false, msg);
    }

    public Result error(Exception e) {
        return new Result(false, null != e.getCause() ? e.getCause().getMessage() : e.getMessage());
    }

    public HttpServletRequest getRequest() {
        return requestThreadLocal.get();
    }

    public HttpServletResponse getResponse() {
        return responseThreadLocal.get();
    }

    public HttpSession getSession() {
        return sessionThreadLocal.get();
    }

    public ServletContext getContext() {
        return contextThreadLocal.get();
    }
}
