package my.ssh.biz.ssh.msg.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import my.ssh.biz.ssh.sys.entity.SysUser;

/**
 * Entity: 消息记录表
 */
@Entity
@Table(name="msg_record")
@org.hibernate.annotations.Table(appliesTo = "msg_record",comment = "消息记录表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgRecord  implements java.io.Serializable {

 
    /**
     * 消息编号
     */
    private int id;
 
    /**
     * 发送用户
     */
    private SysUser sysUserByFromUserId;
 
    /**
     * 接受用户
     */
    private SysUser sysUserByToUserId;
 
    /**
     * 发送时间
     */
    private Date sendTime;
 
    /**
     * 消息类型（message:富文本消息，file:附件传送）
     */
    private String type;
 
    /**
     * 消息体
     */
    private String content;
 
    /**
     * 附件编号
     */
    private MsgFile msgFile;

    public MsgRecord() {
    }

	
    public MsgRecord(int id, SysUser sysUserByFromUserId, Date sendTime) {
        this.id = id;
        this.sysUserByFromUserId = sysUserByFromUserId;
        this.sendTime = sendTime;
    }
    public MsgRecord(int id, SysUser sysUserByFromUserId, SysUser sysUserByToUserId, Date sendTime, String type, String content, MsgFile msgFile) {
       this.id = id;
       this.sysUserByFromUserId = sysUserByFromUserId;
       this.sysUserByToUserId = sysUserByToUserId;
       this.sendTime = sendTime;
       this.type = type;
       this.content = content;
       this.msgFile = msgFile;
    }
   
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name="id", columnDefinition= "int comment '消息编号' not null")
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="from_user_id", nullable=false)
    public SysUser getSysUserByFromUserId() {
        return this.sysUserByFromUserId;
    }
    
    public void setSysUserByFromUserId(SysUser sysUserByFromUserId) {
        this.sysUserByFromUserId = sysUserByFromUserId;
    }
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="to_user_id")
    public SysUser getSysUserByToUserId() {
        return this.sysUserByToUserId;
    }
    
    public void setSysUserByToUserId(SysUser sysUserByToUserId) {
        this.sysUserByToUserId = sysUserByToUserId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="send_time", columnDefinition= "timestamp comment '发送时间' not null")
    public Date getSendTime() {
        return this.sendTime;
    }
    
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
    
    @Column(name="type", columnDefinition= "varchar(10) comment '消息类型（message:富文本消息，file:附件传送）' null")
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="content", columnDefinition= "varchar(65,535) comment '消息体' null")
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    @OneToOne(fetch=FetchType.EAGER, mappedBy="msgRecord")
    public MsgFile getMsgFile() {
        return this.msgFile;
    }
    
    public void setMsgFile(MsgFile msgFile) {
        this.msgFile = msgFile;
    }




}


