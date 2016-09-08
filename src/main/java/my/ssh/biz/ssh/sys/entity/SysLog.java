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
 * Entity: 系统 - 日志表
 */
@Entity
@Table(name="sys_log")
@org.hibernate.annotations.Table(appliesTo = "sys_log",comment = "系统 - 日志表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysLog  implements java.io.Serializable {

 
    /**
     * 日志编号
     */
    private int id;
 
    /**
     * 用户编号
     */
    private Integer userid;
 
    /**
     * 用户名称
     */
    private String username;
 
    /**
     * 类
     */
    private String class_;
 
    /**
     * 方法
     */
    private String method;
 
    /**
     * 产生时间
     */
    private Date createTime;
 
    /**
     * 日志级别
     */
    private String logLevel;
 
    /**
     * 日志内容
     */
    private String message;

    public SysLog() {
    }

	
    public SysLog(int id) {
        this.id = id;
    }
    public SysLog(int id, Integer userid, String username, String class_, String method, Date createTime, String logLevel, String message) {
       this.id = id;
       this.userid = userid;
       this.username = username;
       this.class_ = class_;
       this.method = method;
       this.createTime = createTime;
       this.logLevel = logLevel;
       this.message = message;
    }
   
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name="id", columnDefinition= "int comment '日志编号' not null")
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="userid", columnDefinition= "int comment '用户编号' null")
    public Integer getUserid() {
        return this.userid;
    }
    
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    
    @Column(name="username", columnDefinition= "varchar(45) comment '用户名称' null")
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Column(name="class", columnDefinition= "varchar(45) comment '类' null")
    public String getClass_() {
        return this.class_;
    }
    
    public void setClass_(String class_) {
        this.class_ = class_;
    }
    
    @Column(name="method", columnDefinition= "varchar(45) comment '方法' null")
    public String getMethod() {
        return this.method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time", columnDefinition= "timestamp comment '产生时间' null")
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="log_level", columnDefinition= "varchar(5) comment '日志级别' null")
    public String getLogLevel() {
        return this.logLevel;
    }
    
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }
    
    @Column(name="message", columnDefinition= "longtext comment '日志内容' null")
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }




}


