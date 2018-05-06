package cn.myhomespace.zhou.spider_consumer.utils;

import cn.myhomespace.zhou.spider_consumer.object.Page;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhouw on 2018/5/6.
 */
public class DataConcurrentHashMap {
    private static ConcurrentHashMap<String,BlockingQueue<String>> noSpiderUrlsMap=null;
    private static ConcurrentHashMap<String,BlockingQueue<String>> spiderUrlsMap=null;
    private static ConcurrentHashMap<String,BlockingQueue<Page>> pagesMap=null;


    public synchronized static ConcurrentHashMap<String,BlockingQueue<String>> instanceNoSpiderUrlsMap(){
        if(noSpiderUrlsMap==null){
            synchronized (ConcurrentHashMap.class){
                if(noSpiderUrlsMap==null){
                    noSpiderUrlsMap=new ConcurrentHashMap<>();
                }
            }
        }
        return noSpiderUrlsMap;
    }

    public synchronized static ConcurrentHashMap<String,BlockingQueue<String>> instanceSpiderUrlsMap(){
        if(spiderUrlsMap==null){
            synchronized (ConcurrentHashMap.class){
                if(spiderUrlsMap==null){
                    spiderUrlsMap=new ConcurrentHashMap<>();
                }
            }
        }
        return spiderUrlsMap;
    }

    public synchronized static ConcurrentHashMap<String,BlockingQueue<Page>> instancePagesMap(){
        if(pagesMap==null){
            synchronized (ConcurrentHashMap.class){
                if(pagesMap==null){
                    pagesMap=new ConcurrentHashMap<>();
                }
            }
        }
        return pagesMap;
    }
}
