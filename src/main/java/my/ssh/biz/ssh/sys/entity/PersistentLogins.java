package my.ssh.biz.ssh.sys.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 系统 - 免登录用户记录表
 */
@Entity
@Table(name="persistent_logins")
@org.hibernate.annotations.Table(appliesTo = "persistent_logins",comment = "系统 - 免登录用户记录表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersistentLogins  implements java.io.Serializable {

 
    /**
     * 唯一标志
     */
    private String series;
 
    /**
     * 用户名
     */
    private String username;
 
    /**
     * 安全码
     */
    private String token;
 
    /**
     * 最后登录时间
     */
    private Date lastUsed;

    public PersistentLogins() {
    }

    public PersistentLogins(String series, String username, String token, Date lastUsed) {
       this.series = series;
       this.username = username;
       this.token = token;
       this.lastUsed = lastUsed;
    }
   
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name="series", columnDefinition= "varchar(64) comment '唯一标志' not null")
    public String getSeries() {
        return this.series;
    }
    
    public void setSeries(String series) {
        this.series = series;
    }
    
    @Column(name="username", columnDefinition= "varchar(64) comment '用户名' not null")
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Column(name="token", columnDefinition= "varchar(64) comment '安全码' not null")
    public String getToken() {
        return this.token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_used", columnDefinition= "timestamp comment '最后登录时间' not null")
    public Date getLastUsed() {
        return this.lastUsed;
    }
    
    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }




}


