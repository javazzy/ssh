package my.ssh.biz.ssh.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    private Byte enabled;
 
    /**
     * 账号是否没有过期
     */
    private Byte accountNonExpired;
 
    /**
     * 账号是否没有锁定
     */
    private Byte accountNonLocked;
 
    /**
     * 密码是否没有过期
     */
    private Byte credentialsNonExpired;
 
    /**
     * 性别
     */
    private String sex;
 
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
    public SysUser(int id, String username, String password, Byte enabled, Byte accountNonExpired, Byte accountNonLocked, Byte credentialsNonExpired, String sex, Date birthday, String email, String phone, String address, String photo, Date createTime, Set<SysRole> sysRoles) {
       this.id = id;
       this.username = username;
       this.password = password;
       this.enabled = enabled;
       this.accountNonExpired = accountNonExpired;
       this.accountNonLocked = accountNonLocked;
       this.credentialsNonExpired = credentialsNonExpired;
       this.sex = sex;
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
    public Byte getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }
    
    @Column(name="Account_Non_Expired", columnDefinition= "tinyint comment '账号是否没有过期' null")
    public Byte getAccountNonExpired() {
        return this.accountNonExpired;
    }
    
    public void setAccountNonExpired(Byte accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
    
    @Column(name="Account_Non_Locked", columnDefinition= "tinyint comment '账号是否没有锁定' null")
    public Byte getAccountNonLocked() {
        return this.accountNonLocked;
    }
    
    public void setAccountNonLocked(Byte accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
    
    @Column(name="Credentials_Non_Expired", columnDefinition= "tinyint comment '密码是否没有过期' null")
    public Byte getCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
    
    public void setCredentialsNonExpired(Byte credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
    
    @Column(name="sex", columnDefinition= "varchar(1) comment '性别' null")
    public String getSex() {
        return this.sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
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


