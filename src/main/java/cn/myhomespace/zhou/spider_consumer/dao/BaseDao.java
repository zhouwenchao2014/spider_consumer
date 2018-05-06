package cn.myhomespace.zhou.spider_consumer.dao;


import cn.myhomespace.zhou.spider_consumer.object.SpiderProjectManage;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouw on 2018/5/6.
 */
public interface BaseDao<T> {
    int insertBatch(List<T> ts);
    int insert(T t);
    List<T> queryByParams(Map<String,Object> params);
    List<T> queryAll();
    int count(Map<String,Object> params);
    int update(Map<String,Object> params);
}
