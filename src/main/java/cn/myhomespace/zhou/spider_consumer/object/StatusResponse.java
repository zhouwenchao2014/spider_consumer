package cn.myhomespace.zhou.spider_consumer.object;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhouw on 2018/5/6.
 */
public class StatusResponse extends Response {
    private List<String> keys;
    private ConcurrentHashMap<String, BlockingQueue<String>> noSpiderUrlsMap;
    private ConcurrentHashMap<String, BlockingQueue<String>> spiderUrlsMap;
    private ConcurrentHashMap<String,BlockingQueue<Page>> pagesMap;
    private List<Double> onePre;
    private List<Double> twoPre;

    public List<Double> getOnePre() {
        return onePre;
    }

    public void setOnePre(List<Double> onePre) {
        this.onePre = onePre;
    }

    public List<Double> getTwoPre() {
        return twoPre;
    }

    public void setTwoPre(List<Double> twoPre) {
        this.twoPre = twoPre;
    }

    public ConcurrentHashMap<String, BlockingQueue<Page>> getPagesMap() {
        return pagesMap;
    }

    public void setPagesMap(ConcurrentHashMap<String, BlockingQueue<Page>> pagesMap) {
        this.pagesMap = pagesMap;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public ConcurrentHashMap<String, BlockingQueue<String>> getNoSpiderUrlsMap() {
        return noSpiderUrlsMap;
    }

    public void setNoSpiderUrlsMap(ConcurrentHashMap<String, BlockingQueue<String>> noSpiderUrlsMap) {
        this.noSpiderUrlsMap = noSpiderUrlsMap;
    }

    public ConcurrentHashMap<String, BlockingQueue<String>> getSpiderUrlsMap() {
        return spiderUrlsMap;
    }

    public void setSpiderUrlsMap(ConcurrentHashMap<String, BlockingQueue<String>> spiderUrlsMap) {
        this.spiderUrlsMap = spiderUrlsMap;
    }
}
