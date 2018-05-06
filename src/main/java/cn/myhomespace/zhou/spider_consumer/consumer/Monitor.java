package cn.myhomespace.zhou.spider_consumer.consumer;

import cn.myhomespace.zhou.spider_consumer.object.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

/**
 * Created by zhouw on 2018/5/3.
 */
public class Monitor implements Runnable {

    private Logger logger = LoggerFactory.getLogger(Monitor.class);

    private Map<String,BlockingQueue<String>> noSpiderUrlsMap;

    private BlockingQueue<String> spiderUrls;

    private BlockingQueue<Page> pages;

    private String name;

    public Monitor( Map<String, BlockingQueue<String>> noSpiderUrlsMap, BlockingQueue<String> spiderUrls, BlockingQueue<Page> pages, String name) {
        this.noSpiderUrlsMap = noSpiderUrlsMap;
        this.spiderUrls = spiderUrls;
        this.pages = pages;
        this.name = name;
    }

    @Override
    public void run() {
        while (true){
            Set<String> keys = noSpiderUrlsMap.keySet();
            for(String key : keys){
                BlockingQueue<String> key_Queue = noSpiderUrlsMap.get(key);
                int size = key_Queue.size();
                logger.info("未消费{}队列长度：{}",key,size);
            }
            logger.info("已消费队列长度：{}",spiderUrls.size());
            logger.info("页面队列长度：{}",pages.size());
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
