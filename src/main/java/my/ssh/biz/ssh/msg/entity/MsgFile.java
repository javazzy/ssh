package my.ssh.biz.ssh.msg.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Entity: 消息附件表
 */
@Entity
@Table(name="msg_file")
@org.hibernate.annotations.Table(appliesTo = "msg_file",comment = "消息附件表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgFile  implements java.io.Serializable {

 
    /**
     * 附件编号
     */
    private int id;
 
    /**
     * 附件编号
     */
    private MsgRecord msgRecord;
 
    /**
     * 消息记录编号
     */
    private Integer recordId;
 
    /**
     * 文件名称
     */
    private String fileName;
 
    /**
     * 保存路径
     */
    private String path;
 
    /**
     * 文件大小
     */
    private Integer fileSize;

    public MsgFile() {
    }

	
    public MsgFile(MsgRecord msgRecord) {
        this.msgRecord = msgRecord;
    }
    public MsgFile(MsgRecord msgRecord, Integer recordId, String fileName, String path, Integer fileSize) {
       this.msgRecord = msgRecord;
       this.recordId = recordId;
       this.fileName = fileName;
       this.path = path;
       this.fileSize = fileSize;
    }
   
    @Id
    @GeneratedValue(generator="generator",strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition= "int comment '附件编号' not null")
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(fetch=FetchType.EAGER)@PrimaryKeyJoinColumn
    public MsgRecord getMsgRecord() {
        return this.msgRecord;
    }
    
    public void setMsgRecord(MsgRecord msgRecord) {
        this.msgRecord = msgRecord;
    }
    
    @Column(name="record_id", columnDefinition= "int comment '消息记录编号' null")
    public Integer getRecordId() {
        return this.recordId;
    }
    
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }
    
    @Column(name="file_name", columnDefinition= "varchar(50) comment '文件名称' null")
    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @Column(name="path", columnDefinition= "varchar(200) comment '保存路径' null")
    public String getPath() {
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    @Column(name="file_size", columnDefinition= "int comment '文件大小' null")
    public Integer getFileSize() {
        return this.fileSize;
    }
    
    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }




}


