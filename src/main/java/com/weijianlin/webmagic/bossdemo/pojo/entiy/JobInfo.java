package com.weijianlin.webmagic.bossdemo.pojo.entiy;

import java.io.Serializable;

/**
 * job_info
 * @author 
 */
public class JobInfo implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 城市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 地点
     */
    private String address;

    /**
     * 最低年薪-单位 万
     */
    private Float yearlySalaryLow;

    /**
     * 最高年薪-单位 万
     */
    private Float yearlySalaryHigh;

    /**
     * 工作年限--3-5年 经验不限 1年以内 应届生 10年以上
     */
    private String workingSeniority;

    /**
     * 学历 大专 中专/中技
     */
    private String education;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getYearlySalaryLow() {
        return yearlySalaryLow;
    }

    public void setYearlySalaryLow(Float yearlySalaryLow) {
        this.yearlySalaryLow = yearlySalaryLow;
    }

    public Float getYearlySalaryHigh() {
        return yearlySalaryHigh;
    }

    public void setYearlySalaryHigh(Float yearlySalaryHigh) {
        this.yearlySalaryHigh = yearlySalaryHigh;
    }

    public String getWorkingSeniority() {
        return workingSeniority;
    }

    public void setWorkingSeniority(String workingSeniority) {
        this.workingSeniority = workingSeniority;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        JobInfo other = (JobInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getArea() == null ? other.getArea() == null : this.getArea().equals(other.getArea()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getYearlySalaryLow() == null ? other.getYearlySalaryLow() == null : this.getYearlySalaryLow().equals(other.getYearlySalaryLow()))
            && (this.getYearlySalaryHigh() == null ? other.getYearlySalaryHigh() == null : this.getYearlySalaryHigh().equals(other.getYearlySalaryHigh()))
            && (this.getWorkingSeniority() == null ? other.getWorkingSeniority() == null : this.getWorkingSeniority().equals(other.getWorkingSeniority()))
            && (this.getEducation() == null ? other.getEducation() == null : this.getEducation().equals(other.getEducation()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getArea() == null) ? 0 : getArea().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getYearlySalaryLow() == null) ? 0 : getYearlySalaryLow().hashCode());
        result = prime * result + ((getYearlySalaryHigh() == null) ? 0 : getYearlySalaryHigh().hashCode());
        result = prime * result + ((getWorkingSeniority() == null) ? 0 : getWorkingSeniority().hashCode());
        result = prime * result + ((getEducation() == null) ? 0 : getEducation().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", city=").append(city);
        sb.append(", area=").append(area);
        sb.append(", address=").append(address);
        sb.append(", yearlySalaryLow=").append(yearlySalaryLow);
        sb.append(", yearlySalaryHigh=").append(yearlySalaryHigh);
        sb.append(", workingSeniority=").append(workingSeniority);
        sb.append(", education=").append(education);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}