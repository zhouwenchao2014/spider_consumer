package cn.myhomespace.zhou.spider_consumer.dao;

import cn.myhomespace.zhou.spider_consumer.object.SpiderProjectManage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * describe:
 *
 * @author zhouwenchao
 * @date 2018/05/04
 */
@Mapper
public interface SpiderProjectManageDao {

    List<SpiderProjectManage> queryAll();
    int insert(SpiderProjectManage spiderProjectManage);

}
