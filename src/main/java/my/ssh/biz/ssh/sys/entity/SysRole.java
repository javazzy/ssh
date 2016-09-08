package my.ssh.biz.ssh.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

/**
 * Entity: 系统 - 角色表
 */
@Entity
@Table(name="sys_role")
@org.hibernate.annotations.Table(appliesTo = "sys_role",comment = "系统 - 角色表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysRole  implements GrantedAuthority {

 
    /**
     * 角色编号
     */
    private int id;
 
    /**
     * 角色名称
     */
    private String name;
 
    /**
     * 角色标志
     */
    private String authority;
 
    private List<SysMenu> sysMenus;
 
    private List<SysUser> sysUsers;

    public SysRole() {
    }

	
    public SysRole(int id) {
        this.id = id;
    }
    public SysRole(int id, String name, String authority, List<SysMenu> sysMenus, List<SysUser> sysUsers) {
       this.id = id;
       this.name = name;
       this.authority = authority;
       this.sysMenus = sysMenus;
       this.sysUsers = sysUsers;
    }
   
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name="ID", columnDefinition= "int comment '角色编号' not null")
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="NAME", columnDefinition= "varchar(32) comment '角色名称' null")
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="Authority", columnDefinition= "varchar(100) comment '角色标志' null")
    public String getAuthority() {
        return this.authority;
    }
    
    public void setAuthority(String authority) {
        this.authority = authority;
    }
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="sys_role_menu", joinColumns = { 
        @JoinColumn(name="ROLE_ID", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="MENU_ID", nullable=false, updatable=false) })
    @JsonIgnore
    public List<SysMenu> getSysMenus() {
        return this.sysMenus;
    }
    
    public void setSysMenus(List<SysMenu> sysMenus) {
        this.sysMenus = sysMenus;
    }
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="sys_user_role", joinColumns = { 
        @JoinColumn(name="ROLE_ID", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="USER_ID", nullable=false, updatable=false) })
    @JsonIgnore
    public List<SysUser> getSysUsers() {
        return this.sysUsers;
    }
    
    public void setSysUsers(List<SysUser> sysUsers) {
        this.sysUsers = sysUsers;
    }




}


