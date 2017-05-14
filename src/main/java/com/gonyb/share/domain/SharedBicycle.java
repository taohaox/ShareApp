package com.gonyb.share.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;

/**
 * 单车信息
 * Created by Gonyb on 2017/5/4.
 */
@Entity
@Table(name = "shared_bicycle")
@Document(collection = "bicycle")
public class SharedBicycle {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "share_code",nullable = false, unique = true)
    private String shareCode;
    private String password;
    @Column(name = "createTime")
    private Date createTime;
    @Column(name = "updateTime")
    private Date updateTime;

    
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
