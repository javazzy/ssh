package my.ssh.biz.ssh.sys.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Entity: 测试表
 */
@Entity
@Table(name="test", uniqueConstraints = @UniqueConstraint(columnNames="name"))
@org.hibernate.annotations.Table(appliesTo = "test",comment = "测试表")
public class Test  implements java.io.Serializable {

 
    /**
     * 主键
     */
    private int id;
 
    /**
     * 名称
     */
    private String name;
 
    /**
     * 年龄
     */
    private Integer age;
 
    /**
     * 生日
     */
    private Date birthday;
 
    /**
     * 个人简介
     */
    private String content;
 
    /**
     * 创建时间
     */
    private Date lastTime;
 
    /**
     * 身高
     */
    private Float height;
 
    /**
     * 头像
     */
    private byte[] img;
 
    /**
     * 是否已删除
     */
    private Byte isDelete;
 
    /**
     * 资产
     */
    private Double money;

    public Test() {
    }

	
    public Test(int id, Date lastTime) {
        this.id = id;
        this.lastTime = lastTime;
    }
    public Test(int id, String name, Integer age, Date birthday, String content, Date lastTime, Float height, byte[] img, Byte isDelete, Double money) {
       this.id = id;
       this.name = name;
       this.age = age;
       this.birthday = birthday;
       this.content = content;
       this.lastTime = lastTime;
       this.height = height;
       this.img = img;
       this.isDelete = isDelete;
       this.money = money;
    }
   
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name="id", columnDefinition= "int comment '主键' not null")
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="name", columnDefinition= "varchar(45) comment '名称' null")
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="age", columnDefinition= "int comment '年龄' null")
    public Integer getAge() {
        return this.age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="birthday", columnDefinition= "date comment '生日' null")
    public Date getBirthday() {
        return this.birthday;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    @Column(name="content", columnDefinition= "longtext comment '个人简介' null")
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_time", columnDefinition= "timestamp comment '创建时间' not null")
    public Date getLastTime() {
        return this.lastTime;
    }
    
    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
    
    @Column(name="height", columnDefinition= "float(12,0) comment '身高' null")
    public Float getHeight() {
        return this.height;
    }
    
    public void setHeight(Float height) {
        this.height = height;
    }
    
    @Column(name="img", columnDefinition= "blob comment '头像' null")
    public byte[] getImg() {
        return this.img;
    }
    
    public void setImg(byte[] img) {
        this.img = img;
    }
    
    @Column(name="is_delete", columnDefinition= "tinyint comment '是否已删除' null")
    public Byte getIsDelete() {
        return this.isDelete;
    }
    
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
    
    @Column(name="money", columnDefinition= "double(6,4) comment '资产' null")
    public Double getMoney() {
        return this.money;
    }
    
    public void setMoney(Double money) {
        this.money = money;
    }




}


