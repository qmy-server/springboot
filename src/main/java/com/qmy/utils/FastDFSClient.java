package com.qmy.utils;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

/**
 * Created by winner_0715 on 2016/10/11.
 */
public class FastDFSClient {
    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageClient1 storageClient = null;
    private StorageServer storageServer = null;

    /*public FastDFSClient(String conf) throws Exception {
        if (conf.contains("classpath:")) {
            conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
        }
        ClientGlobal.init(conf);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = null;
        storageClient = new StorageClient1(trackerServer, storageServer);
    }*/

    public FastDFSClient(TrackerGroup g_tracker_group,int g_connect_timeout, int g_network_timeout,String g_charset) throws Exception {
        /*if (conf.contains("classpath:")) {
            conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
        }*/
        /*ClientGlobal.init(conf);*/
        ClientGlobal.setG_tracker_group(g_tracker_group);
        ClientGlobal.setG_connect_timeout(g_connect_timeout);
        ClientGlobal.setG_network_timeout(g_network_timeout);
        ClientGlobal.setG_charset(g_charset);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = null;
        storageClient = new StorageClient1(trackerServer, storageServer);

    }



    public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
        String result = storageClient.upload_file1(fileName, extName, metas);
        return result;
    }

    public String uploadFile(String fileName) throws Exception {
        String result = storageClient.upload_file1(fileName, null, null);
        return result;
    }

    public String uploadFile(String fileName, String extName) throws Exception {
        String result = storageClient.upload_file1(fileName, extName, null);
        return result;
    }

    public String uploadFile(byte[] fileContent,String extName) throws Exception {
        String result =storageClient.upload_file1(fileContent,extName,null);
        return result;
    }


}