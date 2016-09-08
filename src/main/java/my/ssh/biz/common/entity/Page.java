package my.ssh.biz.common.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Page<T> {

    private int draw;

    private int start = -1;
    private int length = -1;//每页显示记录
    private int pageNo = 1;//当前页

    private String order;

    private List<T> data;
    private Long recordsTotal;//不加任何条件，总记录条数
    private Long recordsFiltered;//条件筛选后，总记录条数

    private String propertys; //配置查询列
    private String excludePropertys; //排除条件对比列，与结果列表无关。list查询才会有效。内容以英文逗号","隔开

    private Boolean isAll = false;//如果为true，数据将从缓存里拿

    @JsonIgnore
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    @JsonIgnore
    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @JsonIgnore
    public int getStart() {
        if (start > -1) {
            if (pageNo > 0) {
                return (pageNo - 1) * length;
            } else {
                return 0;
            }

        }
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    @JsonIgnore
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    @JsonIgnore
    public String getExcludePropertys() {
        return excludePropertys;
    }

    public void setExcludePropertys(String excludePropertys) {
        this.excludePropertys = excludePropertys;
    }

    @JsonIgnore
    public String getPropertys() {
        return propertys;
    }

    public void setPropertys(String propertys) {
        this.propertys = propertys;
    }

    @JsonIgnore
    public Boolean getIsAll() {
        return isAll;
    }

    public void setIsAll(Boolean isAll) {
        this.isAll = isAll;
    }
}