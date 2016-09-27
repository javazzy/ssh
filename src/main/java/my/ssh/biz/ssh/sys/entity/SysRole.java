package my.ssh.biz.ssh.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.security.core.GrantedAuthority;

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
 * Entity: 系统 - 角色表
 */
@Entity
@Table(name="sys_role")
@org.hibernate.annotations.Table(appliesTo = "sys_role",comment = "系统 - 角色表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysRole  implements GrantedAuthority,java.io.Serializable {

 
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
 
    private Set<SysMenu> sysMenus = new HashSet<SysMenu>(0);
 
    private Set<SysUser> sysUsers = new HashSet<SysUser>(0);

    public SysRole() {
    }

	
    public SysRole(int id) {
        this.id = id;
    }
    public SysRole(int id, String name, String authority, Set<SysMenu> sysMenus, Set<SysUser> sysUsers) {
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
    public Set<SysMenu> getSysMenus() {
        return this.sysMenus;
    }
    
    public void setSysMenus(Set<SysMenu> sysMenus) {
        this.sysMenus = sysMenus;
    }
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="sys_user_role", joinColumns = { 
        @JoinColumn(name="ROLE_ID", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="USER_ID", nullable=false, updatable=false) })
    @JsonIgnore
    public Set<SysUser> getSysUsers() {
        return this.sysUsers;
    }
    
    public void setSysUsers(Set<SysUser> sysUsers) {
        this.sysUsers = sysUsers;
    }




}


