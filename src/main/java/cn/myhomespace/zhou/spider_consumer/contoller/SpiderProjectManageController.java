package cn.myhomespace.zhou.spider_consumer.contoller;

import cn.myhomespace.zhou.spider_consumer.dao.PageDao;
import cn.myhomespace.zhou.spider_consumer.dao.SpiderProjectManageDao;
import cn.myhomespace.zhou.spider_consumer.object.Page;
import cn.myhomespace.zhou.spider_consumer.object.Response;
import cn.myhomespace.zhou.spider_consumer.object.SpiderProjectManage;
import cn.myhomespace.zhou.spider_consumer.object.TableResponse;
import cn.myhomespace.zhou.spider_consumer.utils.ThreadConcurrentHashMap;
import cn.myhomespace.zhou.spider_consumer.utils.WorkUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhouw on 2018/5/5.
 */
@Controller
public class SpiderProjectManageController {

    @Autowired
    private SpiderProjectManageDao spiderProjectManageDao;

    @Autowired
    private PageDao pageDao;

    @RequestMapping("/start")
    public String start(HttpServletRequest request,Model model){
        return "/start";
    }

    @RequestMapping("/spiderProjectList")
    @ResponseBody
    public TableResponse spiderProjectList(HttpServletRequest request){
        String name = request.getParameter("name");
        String displayName = request.getParameter("displayName");
        String url = request.getParameter("url");
        Map<String,Object> params = new HashMap<>();
        if(!StringUtils.isEmpty(name)){
            params.put("name",name);
        }
        if(!StringUtils.isEmpty(displayName)){
            params.put("displayName",displayName);
        }
        if(!StringUtils.isEmpty(url)){
            params.put("rootUrl",url);
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

        List<SpiderProjectManage> spiderProjectManages = spiderProjectManageDao.queryByParams(params);
        int count = spiderProjectManageDao.count(params);
        TableResponse<SpiderProjectManage> tableResponse = new TableResponse();
        tableResponse.setCode(200);
        tableResponse.setSuccess(true);
        tableResponse.setTotal(count);
        tableResponse.setRows(spiderProjectManages);
        return tableResponse;
    }
    @RequestMapping("/startOrStopWork")
    @ResponseBody
    public Response startOrStopWork(HttpServletRequest request,Model model){
        ConcurrentHashMap<String, List<Thread>> instance = ThreadConcurrentHashMap.instance();
        Response response = new Response();
        String used = request.getParameter("used");
        if(StringUtils.isEmpty(used)){
            response.setSuccess(false);
            response.setCode(100);
            response.setMessage("状态不存在");
            return response;
        }
        int status = Integer.parseInt(used);
        String id=request.getParameter("id");
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);
        List<SpiderProjectManage> spiderProjectManages = spiderProjectManageDao.queryByParams(params);

        if(CollectionUtils.isEmpty(spiderProjectManages)){
            response.setSuccess(false);
            response.setCode(100);
            response.setMessage("项目不存在");
            return response;
        }
        SpiderProjectManage spiderProjectManage = spiderProjectManages.get(0);
        if(spiderProjectManage.getUsed()==status){
            String state="开启状态";
            if(status==1){
                state="关闭状态";
            }
            response.setSuccess(false);
            response.setCode(100);
            response.setMessage("项目已经是"+state);
            return response;
        }
        if(status==0){
            spiderProjectManage.setUsed(status);
            Map<String,Object> param1 = new HashMap<>();
            param1.put("id",spiderProjectManage.getId());
            param1.put("used",spiderProjectManage.getUsed());
            int update = spiderProjectManageDao.update(param1);

            if(update>0){
                WorkUtils.startWork(spiderProjectManageDao,pageDao,spiderProjectManage);
            }
            response.setSuccess(true);
            response.setCode(200);
            response.setMessage("启动成功");
            return response;
        }else{
            String name = spiderProjectManage.getName();
            List<Thread> threads = instance.get(name);
            if(!CollectionUtils.isEmpty(threads)){
                for(Thread thread : threads){
                    thread.interrupt();
                }
            }

            Map<String,Object> param1 = new HashMap<>();
            param1.put("id",spiderProjectManage.getId());
            param1.put("used",1);
            int update = spiderProjectManageDao.update(param1);
            return response;
        }

    }

}
