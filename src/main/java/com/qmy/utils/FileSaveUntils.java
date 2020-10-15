package com.qmy.utils;

import java.io.*;

public class FileSaveUntils {

    public static void saveFile(InputStream inputStream, String path, String name) {
        OutputStream os = null;
        try {

            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs1 = new byte[1024];
            // 读取到的数据长度
            int len1;
            // 输出的文件流保存到本地文件

            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            System.out.println();
            os = new FileOutputStream(tempFile.getPath() + File.separator + name);
            // 开始读取
            while ((len1 = inputStream.read(bs1)) != -1) {
                os.write(bs1, 0, len1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
