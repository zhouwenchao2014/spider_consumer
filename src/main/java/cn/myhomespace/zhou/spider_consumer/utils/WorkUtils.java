package cn.myhomespace.zhou.spider_consumer.utils;

import cn.myhomespace.zhou.spider_consumer.consumer.Consumer;
import cn.myhomespace.zhou.spider_consumer.consumer.Monitor;
import cn.myhomespace.zhou.spider_consumer.consumer.Producer;
import cn.myhomespace.zhou.spider_consumer.dao.PageDao;
import cn.myhomespace.zhou.spider_consumer.dao.SpiderProjectManageDao;
import cn.myhomespace.zhou.spider_consumer.object.Page;
import cn.myhomespace.zhou.spider_consumer.object.SpiderProjectManage;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by zhouw on 2018/5/6.
 */
public class WorkUtils {

    public static void startWork(SpiderProjectManageDao spiderProjectManageDao, PageDao pageDao,SpiderProjectManage spiderProjectManage){
        ConcurrentHashMap<String, BlockingQueue<String>> noSpiderUrlsMap = DataConcurrentHashMap.instanceNoSpiderUrlsMap();
        ConcurrentHashMap<String, BlockingQueue<String>> spiderUrlsMap = DataConcurrentHashMap.instanceSpiderUrlsMap();
        ConcurrentHashMap<String, BlockingQueue<Page>> pagesMap = DataConcurrentHashMap.instancePagesMap();

        ConcurrentHashMap<String, List<Thread>> threadMaps = ThreadConcurrentHashMap.instance();
        List<Thread> threads = Collections.synchronizedList(new ArrayList<Thread>());
        threadMaps.put(spiderProjectManage.getName(),threads);
        BlockingQueue<String> spiderUrls=new LinkedBlockingDeque<>();
        BlockingQueue<Page> pages=new LinkedBlockingDeque<>();
        BlockingQueue<String> noSpiderUrls = new LinkedBlockingDeque<>();
        noSpiderUrlsMap.put(spiderProjectManage.getName(),noSpiderUrls);
        spiderUrlsMap.put(spiderProjectManage.getName(),spiderUrls);
        pagesMap.put(spiderProjectManage.getName(),pages);


        noSpiderUrls.add(spiderProjectManage.getRootUrl());

        Producer producer = new Producer(noSpiderUrls,spiderUrls,pages,spiderProjectManage);
        Thread thread = new Thread(producer);
        thread.start();
        threads.add(thread);

        for(int i=0;i<3;i++){
            Consumer consumer = new Consumer(pages,pageDao);
            Thread consumer_thread = new Thread(consumer);
            consumer_thread.start();
            threads.add(consumer_thread);
        }

        Monitor monitor = new Monitor(noSpiderUrlsMap,spiderUrls,pages,spiderProjectManage.getName()+"_Monitor");
        Thread monitor_thread = new Thread(monitor);
        monitor_thread.start();
        threads.add(monitor_thread);
    }
}
