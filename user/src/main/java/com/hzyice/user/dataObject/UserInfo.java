package com.hzyice.user.dataObject;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class UserInfo implements Serializable{
    private static final long serialVersionUID = -5455654900915130618L;

    @Id
    private String id;
    private String username;
    private String password;
    private String openid;
    private Integer role;
    private Date createTime;
    private Date updateTime;

    public UserInfo() {
    }

    public UserInfo(String username, String password, String openid) {
        this.username = username;
        this.password = password;
        this.openid = openid;
    }

    public UserInfo(String id, String username, String password, String openid, Integer role, Date createTime, Date updateTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.openid = openid;
        this.role = role;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", openid='" + openid + '\'' +
                ", role=" + role +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
