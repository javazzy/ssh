package my.ssh.biz.ssh.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import my.ssh.biz.ssh.dic.entity.DicSex;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;
import javax.persistence.*;

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
     * 性别
     */
    private DicSex dicSex;
 
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
 
    /**
     * 生日
     */
    private Date birthday;
 
    /**
     * 邮箱
     */
    private String email;
 
    /**
     * 手机
     */
    private String phone;
 
    /**
     * 住址
     */
    private String address;
 
    /**
     * 头像
     */
    private String photo;
 
    /**
     * 注册时间
     */
    private Date createTime;
 
    private Set<SysRole> sysRoles = new HashSet<SysRole>(0);

    public SysUser() {
    }

	
    public SysUser(int id) {
        this.id = id;
    }
    public SysUser(int id, DicSex dicSex, String username, String password, Boolean enabled, Boolean accountNonExpired, Boolean accountNonLocked, Boolean credentialsNonExpired, Date birthday, String email, String phone, String address, String photo, Date createTime, Set<SysRole> sysRoles) {
       this.id = id;
       this.dicSex = dicSex;
       this.username = username;
       this.password = password;
       this.enabled = enabled;
       this.accountNonExpired = accountNonExpired;
       this.accountNonLocked = accountNonLocked;
       this.credentialsNonExpired = credentialsNonExpired;
       this.birthday = birthday;
       this.email = email;
       this.phone = phone;
       this.address = address;
       this.photo = photo;
       this.createTime = createTime;
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
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="sex_id")
    public DicSex getDicSex() {
        return this.dicSex;
    }
    
    public void setDicSex(DicSex dicSex) {
        this.dicSex = dicSex;
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
    @Temporal(TemporalType.DATE)
    @Column(name="birthday", columnDefinition= "date comment '生日' null")
    public Date getBirthday() {
        return this.birthday;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    @Column(name="email", columnDefinition= "varchar(45) comment '邮箱' null")
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name="phone", columnDefinition= "varchar(11) comment '手机' null")
    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Column(name="address", columnDefinition= "varchar(45) comment '住址' null")
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Column(name="photo", columnDefinition= "varchar(45) comment '头像' null")
    public String getPhoto() {
        return this.photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time", columnDefinition= "timestamp comment '注册时间' null")
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="sys_user_role", joinColumns = { 
        @JoinColumn(name="USER_ID", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="ROLE_ID", nullable=false, updatable=false) })
    @JsonIgnore
    public Set<SysRole> getSysRoles() {
        return this.sysRoles;
    }
    
    public void setSysRoles(Set<SysRole> sysRoles) {
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


