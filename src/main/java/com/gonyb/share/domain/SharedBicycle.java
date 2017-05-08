package com.gonyb.share.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 单车信息
 * Created by Gonyb on 2017/5/4.
 */
@Entity
@Table(name = "shared_bicycle")
public class SharedBicycle {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String share_code;
    private String password;
    private Date create_time;
    private Date update_time;

    
    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShare_code() {
        return share_code;
    }

    public void setShare_code(String share_code) {
        this.share_code = share_code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
