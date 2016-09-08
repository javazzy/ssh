package my.ssh.biz.common.controller;

import my.ssh.biz.common.entity.Page;
import my.ssh.biz.common.entity.Result;
import my.ssh.biz.common.service.BaseService;
import my.ssh.util.ConvertUtils;
import my.ssh.util.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 公共抽象控制层
 *
 * @param <T>
 */
public abstract class RestController<T> {

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
     * url:'api/sysUser/1'
     * data:null
     * type:'get'
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    @ResponseBody
    public Object getByPrimaryKey(@PathVariable String key, T t) {
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
     * type:'get'
     *
     * @param t
     * @return
     */
    @RequestMapping(value = "/row", method = RequestMethod.GET)
    @ResponseBody
    public Object row(T t) {
        try {
            return getService().getOne(t);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 精确条件查询,但不包括主键，例：
     * url:'api/sysUser'
     * data:{name:'张三'}
     * type:'get'
     *
     * @param t
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(T t,Page<T> page) {
        try {
            return getService().pageList(t,page);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 单条数据新增，例：
     * url:'api/sysUser'（数据为form表单数据，或ajax请求的data参数）
     * data:{id:1,name:"张三"} //（或数据为form表单数据）
     * type:'post'
     *
     * @param t
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object add(T t) {
        try {
            getService().save(t);
            return getService().putChche(t);
        } catch (Exception e) {
            catchException(e);
            return error(e);
        }
    }

    /**
     * 单条数据修改，例：
     * url:'api/sysUser'
     * data:{id:1,name:'张三'}
     * type:'put'
     *
     * @param t
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Object update(T t) {
        try {
            getService().update(t);
            getService().putChche(t);
            return success();
        } catch (Exception e) {
            catchException(e);
            return error(e);
        }
    }


    /**
     * 单条数据修改，忽略空值,例：
     * url:'api/sysUser'
     * data:{id:1,name:'张三'}
     * type:'patch'
     *
     * @param t
     * @return
     */
    @RequestMapping(method = RequestMethod.PATCH)
    @ResponseBody
    public Object updateDynamic(T t) {
        try {
            getService().updateDynamic(t);
            getService().putChche(t);
            return success();
        } catch (Exception e) {
            catchException(e);
            return error(e);
        }
    }

    /**
     * 数据删除，例：
     * url:'api/sysUser/1' //方式一，条件为单主键
     * url:'api/sysUser/1,2,3' //方式二，条件为主键集合
     * url:'api/sysUser/[1,2,3]' //方式三，条件为主键集合
     * url:'api/sysUser/{id:"1",name:"张三"}' //方式四，条件为实体对象
     * url:'api/sysUser/[{id:"1",name:"张三"},{id:"2",name:"李四"}]' //方式五，条件为实体集合
     * data:null
     * type:'delete'
     *
     * @param keys
     * @param t
     * @return
     */
    @RequestMapping(value = "/{keys}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable String keys, T t) {
        if (StringUtils.isBlank(keys)) {
            return error("参数获取失败!");
        }

        try {
            List<T> entityList;
            if (keys.startsWith("[{")) { // 实体集合，例：[{id:'1',name:'张三'},{id:'2',name:'李四'}]
                entityList = JSONUtils.json2list(keys,(Class<T>)t.getClass());
            } else if (keys.startsWith("[")) { // 主键集合，例：[1,2,3]（内容格式根据实体对应字段类型而定）
                List<String> keysArr = JSONUtils.json2list(keys,String.class);
                entityList = new ArrayList<T>();
                for (String key : keysArr) {
                    entityList.add(getService().get(ConvertUtils.toPrimaryData(t.getClass(), key)));
                }
            } else if (keys.startsWith("{")) { // 实体对象，例：{id:'1',name:'张三'}
                t = JSONUtils.json2bean(keys, (Class<T>)t.getClass());
                entityList = new ArrayList<T>();
                entityList.add(t);
            } else if (keys.contains(",")) { // 主键集合，例：1,2,3
                List<String> keysArr = Arrays.asList(keys.split(","));
                entityList = new ArrayList<T>();
                for (String key : keysArr) {
                    entityList.add(getService().get(ConvertUtils.toPrimaryData(t.getClass(), key)));
                }
            } else { // 单主键，例：1
                entityList = new ArrayList<T>();
                entityList.add(getService().get(ConvertUtils.toPrimaryData(t.getClass(), keys)));
            }
            for(T entity : entityList){
                getService().evictChche(entity);
            }
            getService().deleteAll(entityList);
            return success();
        } catch (Exception e) {
            catchException(e);
            return error(e);
        }
    }

    /**
     * 搜索分页查询，例
     * url:'api/sysUsers/page'
     * data:{
     * name:"张", age:26, // 搜索内容
     * start:0, length:10, //分页内容
     * order:"id asc,name asc" // 排序内容
     * }
     * type:'get'
     *
     * @param t
     * @param page
     * @return
     */
    @RequestMapping(value = "/searchPage", method = RequestMethod.GET)
    @ResponseBody
    public Page searchForPage(T t, Page page) {
        try {
            return getService().searchPage(t, page);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 搜索列表查询，例
     * url:'api/sysUsers/searchForList'
     * data:{
     * name:"张", age:26, // 搜索内容
     * }
     * type:'get'
     *
     * @param t
     * @return
     */
    @RequestMapping(value = "/searchList", method = RequestMethod.GET)
    @ResponseBody
    public List<T> searchForList(T t, Page page) {
        try {
            return getService().searchList(t, page);
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
     * type:'get'
     *
     * @param t
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ResponseBody
    public Long count(T t) {
        try {
            return this.getService().count(t);
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
     * type:'get'
     *
     * @param t
     * @return
     */
    @RequestMapping(value = "/searchCount", method = RequestMethod.GET)
    @ResponseBody
    public Long searchCount(T t) {
        try {
            return this.getService().searchCount(t);
        } catch (Exception e) {
            catchException(e);
            return null;
        }
    }

    /**
     * 根据父级树节点的ID查询所有子节点
     *
     * @param t 父节点
     * @param root 根节点名称
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public Object tree(T t, String root) throws Exception {
        try {
            //获取实体参数中的ID
            Method m = t.getClass().getMethod("getId");
            Object parentIdObj = m.invoke(t);
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

            if (null == parentIdObj && StringUtils.isNotBlank(root)) {
                Map<String, Object> rootNode = new HashMap<String, Object>();
                rootNode.put("key", "");
                rootNode.put("title", root);
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
