package com.qmy.dao.upload;

import com.qmy.domain.upload.UploadLog;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * Create by qiemengyan on 2019/5/23
 */
@Repository("UploadDao")
public interface UploadDao {
    @Insert("insert into tbl_log (name,type,place,time,port,data) values (#{name},#{type},#{place},#{time},#{port},#{data})")
    void add(UploadLog uploadLog);
}
