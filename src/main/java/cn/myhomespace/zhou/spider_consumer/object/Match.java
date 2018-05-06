package cn.myhomespace.zhou.spider_consumer.object;

import java.util.List;

/**
 * Created by zhouw on 2018/5/5.
 */
public class Match {
    private String relative;
    private List<Condition> conditions;

    public String getRelative() {
        return relative;
    }

    public void setRelative(String relative) {
        this.relative = relative;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
}
