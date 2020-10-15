package com.qmy.config.rabbitMQ;



import java.util.List;

/**
 * Create by qiemengyan on 2019/5/9
 */
public class MyDataProcess  implements  Runnable{
    private List<String> s;
    public MyDataProcess(List<String> strings){
        this.s=strings;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("开始向数据库插入数据");
        System.out.println("插入成功。。。。");
    }
}
