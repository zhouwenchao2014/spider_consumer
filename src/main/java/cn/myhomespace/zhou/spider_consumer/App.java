package cn.myhomespace.zhou.spider_consumer;

import cn.myhomespace.zhou.spider_consumer.consumer.Consumer;
import cn.myhomespace.zhou.spider_consumer.consumer.Monitor;
import cn.myhomespace.zhou.spider_consumer.consumer.Producer;
import cn.myhomespace.zhou.spider_consumer.dao.PageDao;
import cn.myhomespace.zhou.spider_consumer.dao.SpiderProjectManageDao;
import cn.myhomespace.zhou.spider_consumer.object.Page;
import cn.myhomespace.zhou.spider_consumer.object.SpiderProjectManage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Controller
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}

@Component
class ApplicationStartupListener implements ApplicationListener<ApplicationEvent> {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// 应用刷新
		if (event instanceof ContextRefreshedEvent) {
			doManyThing((ContextRefreshedEvent) event);
		}
	}

	public void doManyThing(ContextRefreshedEvent event){
//		PageDao pageDao = event.getApplicationContext().getBean(PageDao.class);
//		SpiderProjectManageDao spiderProjectManageDao = event.getApplicationContext().getBean(SpiderProjectManageDao.class);
//
//		Map<String,BlockingQueue<String>> noSpiderUrlsMap=new HashMap<>();
//		BlockingQueue<String> spiderUrls=new LinkedBlockingDeque<>();
//		BlockingQueue<Page> pages=new LinkedBlockingDeque<>();
//		List<SpiderProjectManage> spiderProjectManages = spiderProjectManageDao.queryAll();
//		for(SpiderProjectManage spiderProjectManage : spiderProjectManages){
////				List<String> urls = pageDao.queryBySiteName(spiderProjectManage.getName());
//			BlockingQueue<String> noSpiderUrls = new LinkedBlockingDeque<>();
//			noSpiderUrlsMap.put(spiderProjectManage.getName(),noSpiderUrls);
//			noSpiderUrls.add(spiderProjectManage.getRootUrl());
//
//			Producer producer = new Producer(noSpiderUrls,spiderUrls,pages,spiderProjectManage);
//			Thread thread = new Thread(producer);
//			thread.start();
//		}
//
//		for(int i=0;i<3;i++){
//			Consumer consumer = new Consumer(pages,pageDao);
//			Thread consumer_thread = new Thread(consumer);
//			consumer_thread.start();
//		}
//
//		Monitor monitor = new Monitor(noSpiderUrlsMap,spiderUrls,pages,"1");
//		Thread monitor_thread = new Thread(monitor);
//		monitor_thread.start();

	}
}
