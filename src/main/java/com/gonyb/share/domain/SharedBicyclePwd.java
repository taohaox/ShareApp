package com.gonyb.share.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Gonyb on 2017/5/5.
 */
@Entity
@Table(name = "shared_bicycle_pwd")
public class SharedBicyclePwd {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer shared_bicycle_id;
    private String password;
    private int use_count;
    private int right_count;
    private Date create_time;
    private Date update_time;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUse_count() {
        return use_count;
    }

    public void setUse_count(int use_count) {
        this.use_count = use_count;
    }

    public int getRight_count() {
        return right_count;
    }

    public void setRight_count(int right_count) {
        this.right_count = right_count;
    }

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
}
