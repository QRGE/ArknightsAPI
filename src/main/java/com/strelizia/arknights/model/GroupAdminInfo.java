package com.strelizia.arknights.model;

import java.io.Serializable;

/**
 * @author wangzy
 * @Date 2021/3/17 17:50
 **/
public class GroupAdminInfo  implements Serializable {
    private Long groupId;
    private Integer found;
    private Integer picture;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getFound() {
        return found;
    }

    public void setFound(Integer found) {
        this.found = found;
    }

    public Integer getPicture() {
        return picture;
    }

    public void setPicture(Integer picture) {
        this.picture = picture;
    }
}
