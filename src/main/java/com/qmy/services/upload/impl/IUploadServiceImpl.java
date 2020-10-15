package com.qmy.services.upload.impl;

import com.qmy.dao.upload.UploadDao;
import com.qmy.domain.upload.UploadLog;
import com.qmy.services.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by qiemengyan on 2019/5/23
 */
@Service("uploadService")
public class IUploadServiceImpl implements UploadService {
    @Autowired
    private UploadDao uploadDao;
    @Override
    public void add(UploadLog uploadLog) {
        this.uploadDao.add(uploadLog);
    }
}
