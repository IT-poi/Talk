package com.cuit.talk.entity;

import java.util.List;

/**
 * Created by inori on 16/10/13.
 */

public class Group {
    //id,主键
    private int id;
    //分组名
    private String groupName;
    //所属Person id
    private int personId;
    //分组创建的时间
    private String createTime;

    private List<Person> personsList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<Person> getPersonsList() {
        return personsList;
    }

    public void setPersonsList(List<Person> personsList) {
        this.personsList = personsList;
    }
}
