package com.qmy.config.dataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//创建类DynamicRoutingDataSource继承AbstractRoutingDataSource类并且实现determineCurrentLookupKey()方法，设置数据源。
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        if(DynamicDataSourceContextHolder.get().equals(DataSourceKey.ds_slave)) {
            System.out.println("当前数据源：" + DynamicDataSourceContextHolder.get() + ",从数据源读数据");
        }else{
            System.out.println("当前数据源：" + DynamicDataSourceContextHolder.get() + ",主数据源写数据");
        }
        return DynamicDataSourceContextHolder.get();
    }
}