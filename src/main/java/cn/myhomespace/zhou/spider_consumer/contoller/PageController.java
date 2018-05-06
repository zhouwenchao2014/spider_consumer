package cn.myhomespace.zhou.spider_consumer.contoller;

import cn.myhomespace.zhou.spider_consumer.dao.PageDao;
import cn.myhomespace.zhou.spider_consumer.object.Page;
import cn.myhomespace.zhou.spider_consumer.object.TableResponse;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouw on 2018/5/5.
 */
@Controller
public class PageController {

    @Autowired
    private PageDao pageDao;

    @RequestMapping("/message")
    public String message(HttpServletRequest request, Model model){
        return "/message";
    }

    @RequestMapping("/pageList")
    @ResponseBody
    public TableResponse spiderProjectList(HttpServletRequest request){

        Map<String,Object> params = new HashMap<>();
        String siteName = request.getParameter("siteName");
        if(!StringUtils.isEmpty(siteName)){
            params.put("siteName",siteName);
        }
        String title = request.getParameter("title");
        if(!StringUtils.isEmpty(title)){
            params.put("title","%"+title+"%");
        }
        String url = request.getParameter("url");
        if(!StringUtils.isEmpty(url)){
            params.put("title","%"+url+"%");
        }
        String sources = request.getParameter("sources");
        if(!StringUtils.isEmpty(sources)){
            params.put("title","%"+sources+"%");
        }
        String pageSize = request.getParameter("pageSize");
        int size=0;
        if(!StringUtils.isEmpty(pageSize)){
            size= Integer.parseInt(pageSize);
            params.put("pageSize",size);
        }
        String pageIndex = request.getParameter("pageIndex");
        if(!StringUtils.isEmpty(pageIndex)){
            int i = Integer.parseInt(pageIndex);
            params.put("start",size*i);
        }

        List<Page> pages = pageDao.queryByParams(params);
        int count = pageDao.count(params);
        TableResponse<Page> tableResponse = new TableResponse();
        tableResponse.setRows(pages);
        tableResponse.setTotal(count);
        return tableResponse;
    }
}
