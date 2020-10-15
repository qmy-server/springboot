package com.qmy.domain.upload;

import java.sql.Timestamp;

/**
 * Create by qiemengyan on 2019/5/23
 */
public class UploadBase {
    private int id;
    private String type;
    private String place;
    private Timestamp time;

    public UploadBase(int id, String type, String place, Timestamp time) {
        this.id = id;
        this.type = type;
        this.place = place;
        this.time = time;
    }

    public UploadBase() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
