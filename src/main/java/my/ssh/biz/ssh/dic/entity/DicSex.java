package my.ssh.biz.ssh.dic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import my.ssh.biz.ssh.sys.entity.SysUser;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity: 
 */
@Entity
@Table(name="dic_sex")
@org.hibernate.annotations.Table(appliesTo = "dic_sex",comment = "")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DicSex  implements java.io.Serializable {

 
    /**
     * 性别编号
     */
    private int id;
 
    /**
     * 性别
     */
    private String name;
 
    private Set<SysUser> sysUsers = new HashSet<SysUser>(0);

    public DicSex() {
    }

	
    public DicSex(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public DicSex(int id, String name, Set<SysUser> sysUsers) {
       this.id = id;
       this.name = name;
       this.sysUsers = sysUsers;
    }
   
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name="id", columnDefinition= "int comment '性别编号' not null")
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="name", columnDefinition= "varchar(2) comment '性别' not null")
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    @OneToMany(fetch=FetchType.LAZY, mappedBy="dicSex")
    @JsonIgnore
    public Set<SysUser> getSysUsers() {
        return this.sysUsers;
    }
    
    public void setSysUsers(Set<SysUser> sysUsers) {
        this.sysUsers = sysUsers;
    }




}


