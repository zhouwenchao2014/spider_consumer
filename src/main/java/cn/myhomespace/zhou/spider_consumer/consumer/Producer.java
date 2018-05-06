package cn.myhomespace.zhou.spider_consumer.consumer;

import cn.myhomespace.zhou.spider_consumer.object.Page;
import cn.myhomespace.zhou.spider_consumer.object.SpiderProjectManage;
import cn.myhomespace.zhou.spider_consumer.utils.HtmlAnalysis;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by zhouw on 2018/5/3.
 */
public class Producer implements Runnable{

    private BlockingQueue<String> noSpiderUrls;

    private BlockingQueue<String> spiderUrls;

    private BlockingQueue<Page> pages;

    private SpiderProjectManage spiderProjectManage;

    public Producer(BlockingQueue<String> noSpiderUrls, BlockingQueue<String> spiderUrls, BlockingQueue<Page> pages, SpiderProjectManage spiderProjectManage) {
        this.noSpiderUrls = noSpiderUrls;
        this.spiderUrls = spiderUrls;
        this.pages = pages;
        this.spiderProjectManage=spiderProjectManage;
    }

    @Override
    public void run() {
        int i=0;
        String rootUrl = spiderProjectManage.getRootUrl();
        String name = spiderProjectManage.getName();
        while (true){
            String take=null;
            try {
                take = noSpiderUrls.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!take.equals(rootUrl)&&spiderUrls.contains(take)){
                continue;
            }
            HtmlAnalysis.chooseAnalysisEngine(noSpiderUrls,spiderUrls,pages,take,spiderProjectManage,false);
            if(i==10){
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i=0;
            }else{
                i=i+1;
            }


        }
    }
}
