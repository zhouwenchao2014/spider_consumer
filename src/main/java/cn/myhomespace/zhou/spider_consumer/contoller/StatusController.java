package cn.myhomespace.zhou.spider_consumer.contoller;

import cn.myhomespace.zhou.spider_consumer.dao.PageDao;
import cn.myhomespace.zhou.spider_consumer.object.Page;
import cn.myhomespace.zhou.spider_consumer.object.Response;
import cn.myhomespace.zhou.spider_consumer.object.StatusResponse;
import cn.myhomespace.zhou.spider_consumer.utils.DataConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhouw on 2018/5/5.
 */
@Controller
public class StatusController {

    @Autowired
    private PageDao pageDao;

    @RequestMapping("/status")
    public String status(HttpServletRequest request, Model model){
        return "/status";
    }

    @RequestMapping("/statusMessage")
    @ResponseBody
    public StatusResponse message(HttpServletRequest request){
        ConcurrentHashMap<String, BlockingQueue<String>> noSpiderUrlsMap = DataConcurrentHashMap.instanceNoSpiderUrlsMap();
        ConcurrentHashMap<String, BlockingQueue<String>> spiderUrlsMap = DataConcurrentHashMap.instanceSpiderUrlsMap();
        ConcurrentHashMap<String, BlockingQueue<Page>> pagesMap = DataConcurrentHashMap.instancePagesMap();
        Enumeration<String> keysIt = noSpiderUrlsMap.keys();
        List<String> keys = new ArrayList<>();
        List<Double> onePre = new ArrayList<>();
        List<Double> twoPre = new ArrayList<>();
        while(keysIt.hasMoreElements()){
            String s = keysIt.nextElement();
            keys.add(s);
            int size = noSpiderUrlsMap.get(s).size();
            int size1 = spiderUrlsMap.get(s).size();
            int size2 = pagesMap.get(s).size();
            onePre.add(size1/(size+size1+0d));
            twoPre.add((size1-size2)/(size1+0d));
        }

        StatusResponse statusResponse = new StatusResponse();
        statusResponse.setNoSpiderUrlsMap(noSpiderUrlsMap);
        statusResponse.setSpiderUrlsMap(spiderUrlsMap);
        statusResponse.setPagesMap(pagesMap);
        statusResponse.setCode(200);
        statusResponse.setSuccess(true);
        statusResponse.setKeys(keys);
        statusResponse.setOnePre(onePre);
        statusResponse.setTwoPre(twoPre);
        return statusResponse;
    }



}
