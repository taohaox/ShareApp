package com.gonyb.share.domain;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by Gonyb on 2017/5/4.
 */
@Entity
@Table(name = "shared_bicycle_logs")
public class SharedBicycleLogs {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer shared_bicycle_id;
    private String province;
    private String city;
    private String area;
    private Double longitude;
    private Double latitude;
    private Date create_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShared_bicycle_id() {
        return shared_bicycle_id;
    }

    public void setShared_bicycle_id(Integer shared_bicycle_id) {
        this.shared_bicycle_id = shared_bicycle_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
