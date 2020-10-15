package com.qmy.domain.upload;

import java.sql.Timestamp;

/**
 * Create by qiemengyan on 2019/5/23
 */
public class UploadLog extends UploadBase{
    private String name;
    private String port;
    private String data;

    public UploadLog() {
        super();
    }

    public UploadLog(int id, String type, String place, Timestamp time, String name, String port, String data) {
        super(id, type, place, time);
        this.name = name;
        this.port = port;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
