package com.example.chargemessenger;

public class User {
    private Long id;
    private String uuid;
    private int userid;

    public User(Long id, String uuid, int userid) {
        this.id = id;
        this.uuid = uuid;
        this.userid = userid;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", userid=" + userid +
                '}';
    }
}
