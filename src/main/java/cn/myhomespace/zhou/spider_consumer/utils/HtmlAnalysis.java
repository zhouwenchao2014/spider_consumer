package cn.myhomespace.zhou.spider_consumer.utils;

import cn.myhomespace.zhou.spider_consumer.object.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by zhouw on 2018/5/4.
 */
public class HtmlAnalysis {
    private static Logger logger = LoggerFactory.getLogger(HtmlAnalysis.class);
    /**
     * {
     "type":"common",
     "analysis":[{
     "tag":"a","attr":"href","match":[]
     }],
     "opartation":"save"
     }

     {
     "type":"match",
     "analysis":[],
     "match":["[A-Za-z0-9]{40}","[A-Za-z0-9]{41}"],
     "opartation":"save"
     }

     {
     "type":"match",
     "analysis":[],
     "match":["[A-Za-z0-9]{40}","[A-Za-z0-9]{41}"],
     "origin":""
     "opartation":"append"
     }

     {
     "type":"match",
     "analysis":[],
     "match":["[A-Za-z0-9]{40}","[A-Za-z0-9]{41}"],
     "origin":""
     "opartation":"prepend"
     }
     */

    /**
     * 根据引擎版本号 选择执行的引擎版本
     * @param noSpiderUrls
     * @param spiderUrls
     * @param pages
     * @param url
     * @param spiderProjectManage
     */
    public static void chooseAnalysisEngine(BlockingQueue<String> noSpiderUrls,BlockingQueue<String> spiderUrls,BlockingQueue<Page> pages,String url, SpiderProjectManage spiderProjectManage,boolean isSimpleAnalysis){
        String sourceConfig = spiderProjectManage.getSpiderConfig();
        ResourceConfig resourceConfig = JSONObject.parseObject(sourceConfig, ResourceConfig.class);
        String engineVersion = resourceConfig.getEngineVersion();
        switch (engineVersion){
            case "1.0":
                analysisEngine(noSpiderUrls,spiderUrls,pages,url,resourceConfig,spiderProjectManage,isSimpleAnalysis);
                break;
            default:
                analysisEngine(noSpiderUrls,spiderUrls,pages,url,resourceConfig,spiderProjectManage,isSimpleAnalysis);
                break;
        }
    }

    /**
     * 根据引擎类型选择引擎
     * @param noSpiderUrls
     * @param spiderUrls
     * @param pages
     * @param url
     * @param resourceConfig
     */
    public static void analysisEngine(BlockingQueue<String> noSpiderUrls,BlockingQueue<String> spiderUrls,BlockingQueue<Page> pages,String url, ResourceConfig resourceConfig,SpiderProjectManage spiderProjectManage,boolean isSimpleAnalysis){
        if(StringUtils.isEmpty(url)){
            return;
        }
        String type = resourceConfig.getType();
        if(!StringUtils.isEmpty(type)){
            type=type.toLowerCase();
        }
        switch (type){
            case "common":
                buildCommonEngine(noSpiderUrls,spiderUrls,pages,url,resourceConfig,spiderProjectManage,isSimpleAnalysis);
                break;
            case "match":
                buildMatchEngine(noSpiderUrls,spiderUrls,pages,url,resourceConfig,spiderProjectManage,isSimpleAnalysis);
                break;
            default:
                buildCommonEngine(noSpiderUrls,spiderUrls,pages,url,resourceConfig,spiderProjectManage,isSimpleAnalysis);
                break;
        }

    }

    /**
     *
     *  {
     "type":"common",
     "analysis":[{
     "tag":"a","attr":"href","match":[]
     }],
     "opartation":"save"
     }
     * @param noSpiderUrls
     * @param spiderUrls
     * @param pages
     * @param url
     * @param resourceConfig
     */
    private static void buildCommonEngine(BlockingQueue<String> noSpiderUrls, BlockingQueue<String> spiderUrls, BlockingQueue<Page> pages, String url, ResourceConfig resourceConfig,SpiderProjectManage spiderProjectManage,boolean isSimpleAnalysis) {
        List<Analysis> analysises = resourceConfig.getAnalysises();
        String opertation = resourceConfig.getOpertation();
        String origin = resourceConfig.getOrigin();
        String name = spiderProjectManage.getName();
        String rootUrl = spiderProjectManage.getRootUrl();
        Connection connect=null;
        try {
            connect = Jsoup.connect(url);
            connect.timeout(10000);
            Document doc = connect.get();
            if(!isSimpleAnalysis){
                analysisUrl(noSpiderUrls,spiderUrls,doc,rootUrl);
            }
            Page page = new Page();
            page.setUrl(url);
            page.setSiteName(name);
            page.setTitle(doc.title());
            List<String> sources = new ArrayList<>();

            for(Analysis analysis : analysises){
                String tag = analysis.getTag();
                String attr = analysis.getAttr();
                Match match = analysis.getMatch();
                Elements elementsByTag = doc.getElementsByTag(tag);

                for(Element element : elementsByTag){
                    String value = element.attr(attr);
                    if(StringUtils.isEmpty(value)){
                        continue;
                    }
                    if(match==null){
                        if("save".equals(opertation)){
                            sources.add(value);
                        }else if("append".equals(opertation)){
                            sources.add(value+origin);
                        }else if("prepend".equals(opertation)){
                            sources.add(origin+value);
                        }

                    }else{
                        boolean b = buildMatch(value, match);
                        if(b){
                            if("save".equals(opertation)){
                                sources.add(value);
                            }else if("append".equals(opertation)){
                                sources.add(value+origin);
                            }else if("prepend".equals(opertation)){
                                sources.add(origin+value);
                            }
                        }
                    }
                }

                spiderUrls.add(url);
            }
            String s = analysisImg(doc);
            page.setPic(s);
            String source_str = JSONObject.toJSONString(sources);
            page.setSource(source_str);
            page.setCreateTime(new Date(System.currentTimeMillis()));
            pages.add(page);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(url);
            noSpiderUrls.add(url);
        }

    }

    /**
     * 根据根域名爬取本域名下的地址
     * @param doc
     * @param noSpiderUrls
     * @param rootUrl
     * @return
     */
    public static void analysisUrl(BlockingQueue<String> noSpiderUrls, BlockingQueue<String> spiderUrls,Element doc,String rootUrl){
        Elements origin = doc.getElementsByTag("a");
        for(Element img : origin){
            if(img.hasAttr("href")){
                String src = img.attr("href");
                if(noSpiderUrls.contains(src)||spiderUrls.contains(src)){
                    continue;
                }
                if(src.startsWith(rootUrl)){
                    noSpiderUrls.add(src);
                }
            }
        }
    }

    public static String analysisImg(Element doc){
        Elements imgs = doc.getElementsByTag("img");
        for(Element img : imgs){
            if(img.hasAttr("alt")){
                String src = img.attr("src");
                return src;
            }
        }
        return "";
    }

    public static boolean buildMatch(String value,Match match){
        String relative = match.getRelative();
        List<Condition> conditions = match.getConditions();
        boolean isMatch=true;
        switch (relative){
            case "and":
                for (Condition condition: conditions){
                    String opar = condition.getOpar();
                    String oparValue = condition.getOparValue();
                    if(opar.equals("startWith")){
                        if(!value.startsWith(oparValue)){
                            return false;
                        }else{
                            continue;
                        }
                    }else if(opar.equals("endWith")){
                        if(!value.endsWith(oparValue)){
                            return false;
                        }else{
                            continue;
                        }
                    }else if(opar.equals("contains")){
                        if(!value.contains(oparValue)){
                            return false;
                        }else{
                            continue;
                        }
                    }
                }
                return true;
            case "or":
                for (Condition condition: conditions){
                    String opar = condition.getOpar();
                    String oparValue = condition.getOparValue();
                    if(opar.equals("startWith")){
                        if(value.startsWith(oparValue)){
                            return true;
                        }
                    }else if(opar.equals("endWith")){
                        if(value.endsWith(oparValue)){
                            return true;
                        }
                    }else if(opar.equals("contains")){
                        if(value.contains(oparValue)){
                            return true;
                        }
                    }
                }
                return false;
            default:
                return false;
        }
    }

    private static void buildMatchEngine(BlockingQueue<String> noSpiderUrls, BlockingQueue<String> spiderUrls, BlockingQueue<Page> pages, String url, ResourceConfig resourceConfig,SpiderProjectManage spiderProjectManage,boolean isSimpleAnalysis) {

    }
}
