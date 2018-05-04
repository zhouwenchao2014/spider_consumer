package cn.myhomespace.zhou.spider_consumer.consumer;

import cn.myhomespace.zhou.spider_consumer.dao.PageDao;
import cn.myhomespace.zhou.spider_consumer.object.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by zhouw on 2018/5/3.
 */
public class Consumer implements Runnable{

    private BlockingQueue<Page> pages;

    private PageDao pageDao;

    public Consumer(BlockingQueue<Page> pages, PageDao pageDao) {
        this.pages = pages;
        this.pageDao = pageDao;
    }

    @Override
    public void run() {
        while (true){
            List<Page> page_list = new ArrayList<>();
            for(int i=0;i<10;i++){
                try {
                    Page take = pages.take();
                    page_list.add(take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int i = pageDao.insertBatch(page_list);
            try {
                Thread.sleep(6000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
