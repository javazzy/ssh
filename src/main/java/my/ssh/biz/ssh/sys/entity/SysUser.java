package my.ssh.biz.ssh.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Entity: 系统 - 用户表
 */
@Entity
@Table(name="sys_user")
@org.hibernate.annotations.Table(appliesTo = "sys_user",comment = "系统 - 用户表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUser  implements java.io.Serializable {

 
    /**
     * 用户编号
     */
    private int id;
 
    /**
     * 用户姓名
     */
    private String username;
 
    /**
     * 密码
     */
    private String password;
 
    /**
     * 是否启用
     */
    private Boolean enabled;
 
    /**
     * 账号是否没有过期
     */
    private Boolean accountNonExpired;
 
    /**
     * 账号是否没有锁定
     */
    private Boolean accountNonLocked;
 
    /**
     * 密码是否没有过期
     */
    private Boolean credentialsNonExpired;
 
    private List<SysRole> sysRoles;

    public SysUser() {
    }

	
    public SysUser(int id) {
        this.id = id;
    }
    public SysUser(int id, String username, String password, Boolean enabled, Boolean accountNonExpired, Boolean accountNonLocked, Boolean credentialsNonExpired, List<SysRole> sysRoles) {
       this.id = id;
       this.username = username;
       this.password = password;
       this.enabled = enabled;
       this.accountNonExpired = accountNonExpired;
       this.accountNonLocked = accountNonLocked;
       this.credentialsNonExpired = credentialsNonExpired;
       this.sysRoles = sysRoles;
    }
   
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name="ID", columnDefinition= "int comment '用户编号' not null")
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="USERNAME", columnDefinition= "varchar(50) comment '用户姓名' null")
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Column(name="PASSWORD", columnDefinition= "varchar(50) comment '密码' null")
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name="enabled", columnDefinition= "tinyint comment '是否启用' null")
    public Boolean getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    @Column(name="Account_Non_Expired", columnDefinition= "tinyint comment '账号是否没有过期' null")
    public Boolean getAccountNonExpired() {
        return this.accountNonExpired;
    }
    
    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
    
    @Column(name="Account_Non_Locked", columnDefinition= "tinyint comment '账号是否没有锁定' null")
    public Boolean getAccountNonLocked() {
        return this.accountNonLocked;
    }
    
    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
    
    @Column(name="Credentials_Non_Expired", columnDefinition= "tinyint comment '密码是否没有过期' null")
    public Boolean getCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
    
    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="sys_user_role", joinColumns = { 
        @JoinColumn(name="USER_ID", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="ROLE_ID", nullable=false, updatable=false) })
    @JsonIgnore
    public List<SysRole> getSysRoles() {
        return this.sysRoles;
    }
    
    public void setSysRoles(List<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }


    @JsonIgnore
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthoritys = new ArrayList<>();
        for(SysRole sysRole : sysRoles){
            grantedAuthoritys.add(sysRole);

            for(SysMenu sysMenu : sysRole.getSysMenus()){
                grantedAuthoritys.add(sysMenu);
            }

        }
        return grantedAuthoritys;
    }

}


