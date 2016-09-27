package my.ssh.biz.ssh.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Entity: 系统 - 菜单表
 */
@Entity
@Table(name="sys_menu")
@org.hibernate.annotations.Table(appliesTo = "sys_menu",comment = "系统 - 菜单表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysMenu  implements java.io.Serializable {

 
    /**
     * 菜单编号
     */
    private int id;
 
    /**
     * 菜单标题
     */
    private String title;
 
    /**
     * 菜单层级编码
     */
    private String levelCode;
 
    /**
     * 父级菜单编号
     */
    private Integer parent;
 
    /**
     * 展示顺序
     */
    private Integer sort;
 
    /**
     * 菜单类型
     */
    private String type;
 
    /**
     * 页面链接
     */
    private String url;
 
    /**
     * 页面链接访问方式
     */
    private String method;
 
    /**
     * 菜单标志
     */
    private String authority;
 
    private Set<SysRole> sysRoles = new HashSet<SysRole>(0);

    public SysMenu() {
    }

	
    public SysMenu(int id, String title) {
        this.id = id;
        this.title = title;
    }
    public SysMenu(int id, String title, String levelCode, Integer parent, Integer sort, String type, String url, String method, String authority, Set<SysRole> sysRoles) {
       this.id = id;
       this.title = title;
       this.levelCode = levelCode;
       this.parent = parent;
       this.sort = sort;
       this.type = type;
       this.url = url;
       this.method = method;
       this.authority = authority;
       this.sysRoles = sysRoles;
    }
   
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name="id", columnDefinition= "int comment '菜单编号' not null")
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="title", columnDefinition= "varchar(32) comment '菜单标题' not null")
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column(name="level_code", columnDefinition= "varchar(300) comment '菜单层级编码' null")
    public String getLevelCode() {
        return this.levelCode;
    }
    
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }
    
    @Column(name="parent", columnDefinition= "int comment '父级菜单编号' null")
    public Integer getParent() {
        return this.parent;
    }
    
    public void setParent(Integer parent) {
        this.parent = parent;
    }
    
    @Column(name="sort", columnDefinition= "int comment '展示顺序' null")
    public Integer getSort() {
        return this.sort;
    }
    
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    
    @Column(name="type", columnDefinition= "varchar(10) comment '菜单类型' null")
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="url", columnDefinition= "varchar(400) comment '页面链接' null")
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Column(name="method", columnDefinition= "varchar(10) comment '页面链接访问方式' null")
    public String getMethod() {
        return this.method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    @Column(name="Authority", columnDefinition= "varchar(100) comment '菜单标志' null")
    public String getAuthority() {
        return this.authority;
    }
    
    public void setAuthority(String authority) {
        this.authority = authority;
    }
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="sys_role_menu", joinColumns = { 
        @JoinColumn(name="MENU_ID", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="ROLE_ID", nullable=false, updatable=false) })
    @JsonIgnore
    public Set<SysRole> getSysRoles() {
        return this.sysRoles;
    }
    
    public void setSysRoles(Set<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }




}


