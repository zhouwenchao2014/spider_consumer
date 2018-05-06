package cn.myhomespace.zhou.spider_consumer.utils;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhouw on 2018/5/6.
 */
public class ThreadConcurrentHashMap {
    private static ConcurrentHashMap<String,List<Thread>> threadMap=null;

    public synchronized static ConcurrentHashMap<String,List<Thread>> instance(){
        if(threadMap==null){
            synchronized (ConcurrentHashMap.class){
                if(threadMap==null){
                    threadMap=new ConcurrentHashMap<>();
                }
            }
        }
        return threadMap;
    }
}
